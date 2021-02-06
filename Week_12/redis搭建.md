1.（必做）配置 redis 的主从复制，sentinel 高可用，Cluster 集群。
在生产环境，需要引入集群方案，应对可用性，可靠性的要求

Redis 支持的三种集群方案：

主从复制模式
Sentinel(哨兵)模式
Cluster 模式
主从复制模式
主从复制模式中包含一个主数据库实例（master)与一个或多个数据库实例（slave),具体工作机制为：

slave启动后，向master 接收到SYNC 命令后通过bgsave 保存快照，并使用缓冲区记录保存快照这段时间内执行的写命令
mater 将保存的快照文件发送给 slave ，并继续记录执行的写命令
slave 接收到快照文件后，加载快照文件，载入数据
master 快照发送完后开始向slave发送缓冲区的写命令，slave 接收命令并执行，完成复制初始化
此后 master 每次执行一个写命令都会发给slave，保持master 与 slave 之间数据的一致性
在 redis 中添加配置

Redis-Master-01 节点配置

vim /etc/redis/master.conf

# 修改一下IP地址
bind 192.168.3.10
Redis-Slave-02 节点配置

vim /etc/redis/salver01.conf

# 1. 修改一下IP地址
bind 192.168.3.11

# 2. 定义从节点，追随主节点
# replicaof <masterip> <masterport>
replicaof 192.168.3.10 6379
Redis-Slave-03 节点配置

vim /etc/redis/salver02.conf

# 1. 修改一下IP地址
bind 192.168.3.12

# 2. 定义从节点，追随主节点
# replicaof <masterip> <masterport>
replicaof 192.168.3.10 6379
启动

[root@dev-server-1 master-slave]# redis-server master.conf
[root@dev-server-1 master-slave]# redis-server slave1.conf
[root@dev-server-1 master-slave]# redis-server slave2.conf
Redis Sentinel 哨兵
哨兵模式基于主从复制模式，只是引入了哨兵来监控与自动处理故障

哨兵顾名思义，就是来为Redis集群站哨的，一旦发现问题能做出相应的应对处理。其功能包括

监控master、slave是否正常运行
当master出现故障时，能自动将一个slave转换为master
多个哨兵可以监控同一个Redis，哨兵之间也会自动监控
哨兵模式的具体工作方式：

在配置文件中通过 sentinel monitor <master-name> <ip> <redis-port> <quorum> 来定位master的IP、端口，一个哨兵可以监控多个master数据库，只需要提供多个该配置项即可。哨兵启动后，会与要监控的master建立两条连接：

一条连接用来订阅master的_sentinel_:hello频道与获取其他监控该master的哨兵节点信息
另一条连接定期向master发送INFO等命令获取master本身的信息
与master建立连接后，哨兵会执行三个操作：

定期（一般10s一次，当master被标记为主观下线时，改为1s一次）向master和slave发送INFO命令
定期向master和slave的_sentinel_:hello频道发送自己的信息
定期（1s一次）向master、slave和其他哨兵发送PING命令
发送INFO命令可以获取当前数据库的相关信息从而实现新节点的自动发现。所以说哨兵只需要配置master数据库信息就可以自动发现其slave信息。获取到slave信息后，哨兵也会与slave建立两条连接执行监控。通过INFO命令，哨兵可以获取主从数据库的最新信息，并进行相应的操作，比如角色变更等。

接下来哨兵向主从数据库的_sentinel_:hello频道发送信息与同样监控这些数据库的哨兵共享自己的信息，发送内容为哨兵的ip端口、运行id、配置版本、master名字、master的ip端口还有master的配置版本。这些信息有以下用处：

其他哨兵可以通过该信息判断发送者是否是新发现的哨兵，如果是的话会创建一个到该哨兵的连接用于发送PING命令。
其他哨兵通过该信息可以判断master的版本，如果该版本高于直接记录的版本，将会更新
当实现了自动发现slave和其他哨兵节点后，哨兵就可以通过定期发送PING命令定时监控这些数据库和节点有没有停止服务。
如果被PING的数据库或者节点超时（通过 sentinel down-after-milliseconds master-name milliseconds 配置）未回复，哨兵认为其主观下线（sdown，s就是Subjectively —— 主观地）。如果下线的是master，哨兵会向其它哨兵发送命令询问它们是否也认为该master主观下线，如果达到一定数目（即配置文件中的quorum）投票，哨兵会认为该master已经客观下线（odown，o就是Objectively —— 客观地），并选举领头的哨兵节点对主从系统发起故障恢复。若没有足够的sentinel进程同意master下线，master的客观下线状态会被移除，若master重新向sentinel进程发送的PING命令返回有效回复，master的主观下线状态就会被移除

配置

mkdir /etc/redis
cd /etc/redis
vim sentinel-6379.conf
启动

sentinel monitor mymaster 192.168.3.13 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1
redis-sentinel /etc/redis/sentinel-6379.conf
启动后，可以kill 掉主节点Redis 来模拟Sentinel 的故障转移过程


Redis集群原理
Redis集群中数据是和槽（slot）挂钩的。

其总共定义了**「16384」个槽，所有的数据根据「一致性哈希算法」**会被映射到这16384个槽中的某个槽中；

另一方面，这16384个槽是按照设置被分配到不同的Redis节点上的，比如启动了三个Redis实例：Redis-A，Redis-B和Redis-C，这里将0-5460号槽分配给Redis-A，将5461-10922号槽分配给Redis-B，将10923-16383号槽分配给Redis-C（总共有16384个槽，但是其标号类似数组下标，是**「从0到16383」**）。

也就是说**「数据的存储只和槽有关，并且槽的数量是一定的」，由于一致性Hash算法是一定的，因而将这16384个槽分配给无论多少个Redis实例，「对于确认的数据其都将被分配到确定的槽位上」**。

Redis集群通过这种方式来达到Redis的高效和高可用性目的。

一致哈希算法根据数据的key值计算映射位置时和所使用的节点数量有非常大的关系。 一致哈希分区的实现思路是为系统中每个节点分配一个token，范围一般在0~2^32，这些token构成一个**「哈希环」，数据读写执行节点查找操作时，先根据key计算hash值，然后「顺时针找到第一个大于等于该hash值的token节点」**，需要操作的数据就保存在该节点上。

一致哈希分区存在如下问题：

加减节点会造成哈希环中部分数据无法命中，需要手动处理或忽略这部分数据；
当使用少量节点时，节点变化将大范围影响环中数据映射，因此这种方式不适合少量节点的分布式方案；
普通的一致性哈希分区在增减节点时需要增加一倍或减去一半节点才能保证数据和负载的平衡。
Redis的应对方案是，使用了**「虚拟槽」来处理分区时节点变化的问题，也即「将所有的数据映射到16384个虚拟槽位上，当Redis节点变化时数据映射的槽位将不会变化」**，并且这也是Redis进行节点扩张的基础

关键配置

bind 192.168.3.20
port 6380
cluster-enabled yes
cluster-node-timeout 15000
cluster-config-file "nodes-6380.conf"
daemonize yes
pidfile /var/run/redis_6380.pid
logfile "cluster-6380.log"
dbfilename dump-cluster-6380.rdb
appendfilename "appendonly-cluster-6380.aof"
启动集群

redis-server /etc/redis/6380.conf
redis-server /etc/redis/6381.conf
redis-cli --cluster create 192.168.3.20:6380 192.168.3.21:6380 192.168.3.22:6380 192.168.3.20:6381 192.168.3.21:6381 192.168.3.22:6381 --cluster-replicas 1