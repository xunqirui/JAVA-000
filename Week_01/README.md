# Course 01

## JVM 核心技术 -- 基础知识

### 语言种类的划分

#### 按语言类型划分：

​	机器语言 -> 汇编语言 -> 高级语言

#### 按有无虚拟机划分：

1. 有虚拟机：Java，Lua，Ruby，部分JavaScript的实现等等
2. 无虚拟机：：C，C++，C#，Golang，以及大部分常见的编程语言

#### 按照变量是不是有确定的类型划分：

1. 静态类型：Java，C，C++等等
2. 动态类型：所有脚本类型的语言

#### 按照是编译执行，还是解释执行划分：

1. 编译执行：C，C++，Golang，Rust，C#，Java，Scala，Clojure，Kotlin， Swift...等等
2. 解释执行：：JavaScript的部分实现和NodeJS，Python，Perl，Ruby...等等

#### 按照语言特点划分：

1. 面向过程：C，Basic，Pascal，Fortran等等
2. 面向对象：C++，Java，Ruby，Smalltalk等等
3. 函数式编程：LISP、Haskell、Erlang、OCaml、Clojure、F#等等

#### 编程语言跨平台

源代码跨平台：在各个平台上进行编译，会出现库安装麻烦等问题。

二进制跨平台：编译成字节码文件再去各平台上的虚拟机中执行。

### Java字节码

#### 字节码定义

​	Java bytecode 由单字节（byte）的指令组成，理论上最多支持 256 个操作码。

#### 字节码分类

1. 栈操作指令，包括与局部变量交互的指令
2. 程序流程控制指令
3. 对象操作指令，包括方法调用指令
4. 算数运算以及类型转换指令

#### java 查看字节码

​	获取编译后的 .class 文件，使用 javap -c 查看，如果要看详细的数据，那在编译的时候使用 javac -g 来获取详细信息，再通过 

javap -c -v 来查看更多的调试信息

#### 字节码运行时结构

JVM 是一台基于栈的虚拟机，每个线程都有一个独属于自己的线程栈（JVM Stack），用于存储栈帧（Frame），每一次方法调用都会自动创建一个栈帧，栈帧由操作数栈、局部变量数组以及一个 Class 引用组成。Class 引用指向当前方法在运行时常量池中对应的 Class

#### 栈操作指令

*load：例如 iload_、aload，将本地变量表的值压入栈顶

*store：例如 astore、istore，将栈顶的值弹出并放入本地变量表中

dup: 复制栈顶值

#### 方法调用指令

invokestatic：调用某个类的静态方法，是方法调用指令中最快的一个。

invokespecial：调用构造函数，也可以用于调用同一个类中的 private 方法，以及可见的超类方法。

inokevirtual：如果是具体某个类型的目标对象，invokevirtual 用于调用公共，受保护和 package 级的私有方法。

invokeinterface：当通过接口引用来调用方法时，将会编译为 invokeinterface 指令。

Invokedynamic：jdk7 新增指令，实现动态类型语言而进行的改进，也是 jdk8 lambda 表达式的实现基础。

### JVM 类加载器

#### 类的生命周期

1. 加载（Loading）：在 classpath 或者其他途径下找到 Class 文件，查找字节流，并根据此创建类的过程，基本数据类型是有 java 虚拟机预先定义好的，引用类型中类和接口需要获取字节流，数组类是 java 虚拟机直接生成的。
2. 验证（Verification）：验证格式，确保被加载类满足 java 虚拟机的约束条件，同时去检查依赖
3. 准备（Preparation）：创建静态字段，并将其初始化为默认值（例如 null ），并为静态字段分配内存空间。
4. 解析（Resolution）：将符号引用解析为实际引用。
5. 初始化（Initialization）：执行类构造器方法、静态变量赋值、执行 static 静态代码块。
6. 使用（Using）
7. 卸载（Unloading）

注：其中 2-4 步可以合并成为 链接

#### 类的初始化触发时机

1. 当虚拟机启动的时候，初始化用户指定的类，就是启动执行的 main 方法所在的类。
2. 当遇到用以新建实例的 new 指令时，初始化 new 指令的目标类，也就是 new 一个类的时候要初始化。
3. 当遇到静态方法的指令时，初始化该静态方法所在的类。
4. 当遇到静态字段的指令时，初始化静态字段所在的类。
5. 子类的初始化会触发父类的初始化。
6. 如果一个接口定义了 default 方法，那么直接实现或者间接实现接口的类的初始化，会触发接口的初始化。
7. 使用反射 API 对某个类进行反射调用时，初始化这个类。
8. 当初次调用 MethodHandle 实例时，初始化该 MethodHandle 指向的方法所在的类。

#### 类不会初始化情况（可能会被加载）

1. 通过子类引用父类的静态字段，只会触发父类的初始化，而不会触发子类的初始化。
2. 定义对象数组，不会触发类的初始化
3. 常量在编译期间会存入调用类的常量池中，本质上并没有直接引用定义常量的类，不会触发定义常量所在的类
4. 通过类型获取 Class 对象，不会触发类的初始化，Hello.class 不会让 Hello 类初始化。
5. 通过 Class.forName 加载指定类时，如果指定参数 initialize 为 false 时，也不会触发类初始化，这个参数是告诉虚拟机是否要对类进行初始化，默认 initialize 为 true。
6. 通过 ClassLoader 默认的 loadClass 方法，也不会触发初始化动作（加载了，但是不会初始化）。

#### 类加载器的种类

1. 启动类加载器（BootstrapClassLoader）：JVM 内部实现，用原生 C++ 代码来实现的，并不继承 java.lang.ClassLoader，负责加载JDK中 jre/lib/rt.jar 里的所有 class，和其他一些 jar 包（主要是 rt.jar）。
2. 扩展类加载器（ExtClassLoader）：负责加载 JRE 的扩展目录，lib/ext 或者由 java.ext.dirs 系统属性指定的目录中的 JAR 包的类。
3. 应用类加载器（AppClassLoader）：加载来自 Java 命令的 -classpath 或者 -cp 选项、java.class.path 系统属性指定的 jar 包和类路径、用户自定义的类。

#### 类加载器的特点

1. 双亲委派

   当一个自定义类加载器需要加载一个类的时候，会先去委托自己的父加载器加载，父加载器如果发现还有自己的父加载器，则会一直向上寻找，如果上级类加载器加载了这个类，则所有的子加载器不需要继续加载，如果几个类加载器都没有找到指定的类，则会抛出 ClassNotFoundException 异常。

2. 负责依赖

   如果一个加载器在加载某个类的时候，发现这个类依赖于另外几个类或接口时，也会尝试去加载这些依赖项。

3. 缓存加载

   已经被类加载器加载过的类，会缓存加载结果，不会重复加载。

#### 添加引用类的方式

1. 放到 JDK 的 lib/ext 下，或者 -Djava.ext.dirs。
2. java -cp/classpath 或者 class 文件放到当前路径进行执行。
3. 自定义 ClassLoader 加载。
4. 通过程序，获取当前执行类的 ClassLoader ，强转成 UrlClassLoader ，再通过反射调用 addUrl 方法添加 Jar 或路径（JDK9 无效），JDK 9 中可以通过 Class.forName 里传入类名、路径、对应的类加载器来加载指定的引用类。

### JVM 内存模型

#### 根据是否线程私有划分

1. 线程私有的

   程序计数器器

   虚拟机栈

   本地方法栈

2. 线程共享的

   堆

   方法区（jdk 1.8 后移除，改为 元数据区(Metaspace)）

   直接内存

#### 程序计数器

​		字节码解释器工作时，通过改变程序计数器的值来选取下一条要执行的指令，分支、循环、跳转、异常处理、线程恢复等功能都需要依赖这个计数器来完成。

​		每个线程都会有自己独立的程序计数器，各线程之间计数器互不影响。

作用：

1. 字节码解释器通过程序计数器来依次读取指令，从而实现代码的流程控制，如：顺序执行、选择、循环、异常处理。
2. 在多线程环境下，程序计数器用于记录当前线程的执行位置，所以当线程被切换回来的时候，可以知道上次运行到哪。

#### 虚拟机栈（java 栈）

​		每个线程都有自己的线程栈（也叫做 Java 方法栈），每执行一个方法都会创建一个栈帧，所以每个线程栈是由多个栈帧组成，而每个栈帧中又包含了：局部变量表、操作数栈、动态链接、方法出口信息。

​		虚拟机栈的局部变量表中主要存放：**原生数据类型的局部变量、对象引用**。

#### 本地方法栈

​		使用了 JNI 方法时，会分配本地方法栈，由于 JNI 方法会调用一些底层的例如 c++ 的代码，所以通过 c++ 运行占用的一些内存也不在 JVM 管理范围内。

#### 堆

​		在 hotspot 虚拟机中，堆被分为年轻代（Young generation）和老年代（Old generation），其中年轻代还划分为3个内存池，新生代（Eden space）和存活区（Survivor space）。大部分 GC 算法中，有2个存活区（S0,S1），任何时刻，S0 和 S1 总有一个是空的。不同 GC 下，年轻代与老年代的占比是不同的，并行 GC 下是 1:2（年轻代:老年代），CMS 下年轻代的最大堆内存不一样，如果是64位的机器就是 64MB * 并行GC的线程数 * 一个系数 / 10 这样计算出来的一个值。

​		年轻代主要**存放新创建的对象**，如果新创建的对象在新生代中满了，就会放到存活区，如果一个存活区满了，就会在触发 gc 之后，将该存活区剩余的对象全部挪到另外一个存活区中。

​		老年代主要**存放创建的对象占用空间大的对象和存活时间长的对象**。

​		堆中主要存放 **Java 代码中创建的所有对象，也包括包装类型（Integer、Boolean 等），同时包含在 1.7 版本后从运行时常量池中分出来的字符串常量池**。

#### 非堆

##### 元数据区（Metaspace）

​		jdk 1.8 以前是方法区，jdk 1.8 之后主要存放**已被虚拟机加载的类信息、运行时常量池（不包含字符串常量池）中的常量、静态变量、即时编译器编译后的代码等数据**，在 jdk 1.8 以前，方法区的实现是永久代，并放在堆中。

##### CCS （Compressed Class Space）

​		存放 class 信息的，与 Metaspace 有交叉。

##### Code Cache

​		存放 JIT 编译器编译后的本地机器码

#### CPU 与内存行为

1. CPU 乱序执行
2. volatile 关键字
3. 原子性操作
4. 内存屏障

### JVM 启动参数

#### 从形式上划分

1. 以 - 开头为标准参数，表示以什么模式启动，例如 -server、-client。
2. -D 开头，设置系统属性。
3. -X 开头为非标准参数，例如：-Xmx。
4. -XX:  开头为非稳定参数，随时可能在下个版本取消。-XX: 后面可以跟 +-Flags 形式，+- 是的对布尔值进行开关，例如：-XX+UseG1GC；还可以跟 -XX:key=value 形式，指定某个选项的值，例如：-XX:MaxPermSize=256m。

#### 按作用划分

1. 系统属性参数，例如：-D 开头的
2. 运行模式参数，例如：-server、-client
3. 堆内存设置参数，例如：-Xmx8g
4. GC 设置参数，例如：-XX:+UseG1GC
5. 分析诊断参数
6. JavaAgent 参数

# Course 02

## JVM 核心技术 -- 工具与 GC 策略

### JDK 内置命令行工具

#### JVM 命令行工具

| 工具          | 简介                                                         |
| ------------- | ------------------------------------------------------------ |
| java          | java 应用的启动程序                                          |
| javac         | JDK 内置的编译工具                                           |
| javap         | 反编译 class 文件的工具                                      |
| javadoc       | 根据 java 代码和标准标注，自动生成相关的 API 说明文档        |
| javah         | JNI 开发时，根据 java 代码生成需要的 .h 文件                 |
| extcheck      | 检查某个 jar 文件和运行时扩展 jar 有没有版本冲突，很少使用   |
| jdb           | java debugger；可以调试本地和远端程序，属于 JPDA 中的一个 demo 实现，供其他调试器参考。开发时很少使用 |
| jdeps         | 探测 class 或 jar 包需要的依赖                               |
| jar           | 打包工具，可以将文件和目录打包成为 .jar 文件；.jar 文件本质上就是 zip 文件, 只是后缀不同。使用时按顺序对应好选项和参数即可。 |
| keytool       | 安全证书和密钥的管理工具; （支持生成、导入、导出等操作）     |
| jarsigner     | JAR 文件签名和验证工具                                       |
| policytool    | 实际上这是一款图形界面工具, 管理本机的 Java 安全策略         |
| **jps/jinfo** | 查看 Java 进程                                               |
| **jstat**     | 查看 jvm 内部 gc 相关信息                                    |
| **jmap**      | 查看 heap 或类占用空间统计                                   |
| **jstack**    | 查看线程信息                                                 |
| **jcmd**      | 执行 jvm 相关分析命令（整合命令）                            |
| runscript/jjs | 执行 js 命令                                                 |

#### jps/jinfo 查看 Java 进程

jps 查看进程号

jps -mlvV 查看详细 Java 进程

jpsinfo <hostid> 查看指定 java 进程的详细信息

#### jstat* 查看 gc 信息

jstat -gcutil pid 1000 1000 （后面 1000 1000 的意思是1秒执行一次，一共执行1000次）：查看 gc 相关的堆内存的使用率、gc 执行次数、gc 执行时间等。

jstat -gc pid 1000 1000 ：查看 gc 相关的堆内存情况、gc 执行次数、gc 执行时间等。

#### jmap 查看 heap 或类占用空间统计

jmap -heap pid：打印堆内存的配置和使用信息。

jmap -histo pid：类的占用空间统计。

#### jstack 查看线程信息

jstack pid [commond]

##### 命令行参数

-F 强制指定 thread dump ，可在 java 进程卡住时使用

-m 混合模式，将 java 帧和 native 帧一起输出

-l 长列表模式，将线程相关的 locks 信息一起输出，比如持有的锁，等待的锁。

#### jcmd*

jcmd 综合了上面的几个命令，可以通过 jcmd pid -help 查看当前进程可以使用哪些 jcmd 命令。

#### JVM 相关问题

1. 直接 java -jar 启动，默认初始堆和最大堆大小为多少?

   java 8 中，默认初始堆大小为物理内存的 1/64，最大堆内存不超过物理内存的 1/4 或 1G。

### JDK 内置图形化工具

#### 工具列表

1. jconsole
2. jvisualvm
3. idea 中 VisualGC
4. jmc

### GC

#### 判断对象是否存活算法

1. 引用计数法

   ​		给对象添加一个引用计数器，每当有一个地方引用它的时候 +1，引用失效的时候减1，如果当计数器值为 0 之后，就无法再引用该对象，因为已经获取不到这个对象了。但是该方法无法解决两个对象之间相互循环引用造成的问题。

2. 可达性分析算法

   ​		从 GC Roots 作为根节点出发，向下搜索，节点所走过的链接为引用链，如果一个对象到 GC Roots 没有任何引用链相连的话，这个对象就是不可用的。

   ​	可作为 GC Roots 的对象：

   ​		1) 当前正在执行的方法里的局部变量和输入参数

   ​		2) 活动线程（Active threads）

   ​		3) 已加载类的静态字段

   ​		4) JNI 引用

#### 解决垃圾回收的误报

​		使用 stop-the-world，停止其他非垃圾回收线程的工作，知道垃圾回收完成为止。stop-the-world 是通过安全点实现的。

#### 垃圾回收的三种方式

1. 清除算法

   ​	标记完存活对象之后，将不可用的对象所占据的内存标记为空闲内存，并记录在一个空闲列表（free list）之中，当有新的对象创建的时候，内存管理模块便会从该空闲列表中寻找空闲内存，并划分给新建的对象。

   ​	缺点：1. 会造成内存碎片。因为堆中的对象是连续分布的，可能会出现当前的空闲列表中没有连续的空间分配给新的对象的情况，也就是明明有足够的空间，但是无法分配。2. 分配效率低。Java 需要逐个访问空闲列表中的项，来查找能够放入新建对象的空闲内存。

2. 压缩算法

   ​	将存活对象聚集到内存区域的起始位置，从而留下一块连续的内存空间，通过该方法解决了内存碎片问题，但是代价是压缩算法带来的性能开销。

3. 复制算法

   ​	即把内存区域分为两等分，分别用两个指针 from 和 to 来维护，并且只是用 from 指针指向的内存区域来分配内存。当发生垃圾回收时，便把存活的对象复制到 to 指针指向的内存区域中，并且交换 from 指针和 to 指针的内容。复制这种回收方式同样能够解决内存碎片化的问题，但是它的缺点也极其明显，即堆空间的使用效率极其低下。

#### 垃圾收集器

##### Serial GC 和 Serial Old GC

​	新生代使用 Serial 收集器，老年代使用 Serial Old 收集器，新生代中采用的是标记-复制算法，老年代中采用的是标记-整理算法。都是只用一个线程进行垃圾回收，在垃圾回收时会触发 STW 事件。

##### Parallel GC（并行 GC）

```java
-XX:UseParallelGC
// 年轻代使用 Parallel Scavenge，老年代使用串行 GC
-XX:UseParallelOldGC
// 年轻代用 Parallel Scavenge，老年代使用并行 GC
```

​	新生代中有 Parallel New 和 Parallel Scavenge ，老年代中是 Parallel Old。相当于 Serial GC 的多线程版，新生代中采用的是标记-复制算法，老年代中采用的是标记-整理算法，在垃圾回收时也会触发 STW 事件。Parallel Scavenge GC 比 Parallel New 更加注重吞吐量（ 应用运行时间 /(应用运行时间 + 垃圾回收时间) ，高吞吐量就是说运行应用的时间占比大），它提供了很多参数来帮用户找到最合适的停顿时间或最大吞吐量。由于 Parallel Scavenge 的侧重点与 CMS 不同，同时该垃圾收集器的底层设计没有采用 hotspot 中原本的分代框架，所以无法与 CMS 配合工作。并行 GC 在两次 GC 周期的间隔期，没有 GC 线程在运行，不会消耗任何系统资源。

​	并行 GC 默认的线程数为当前 CPU 的核数，可以通过 -XX:ParallelGCThreads=N 来指定 GC 线程数。在使用 -XX:+useParalelGC 的时候新生代使用的是 Parallel Scavenge GC。

##### CMS GC

```java
注：并发与并行在 jvm 环境下的意思
并发：用户线程与垃圾回收线程同时在运行
并行：触发 STW 事件，用户线程暂停执行，只有垃圾回收线程在执行
```

```java
// 使用方法
-XX:+UseConcMarkSweepGC
```

​	CMS 是一种已获取最短回收停顿时间为目标的收集器，是 HotSpor 虚拟机第一款真正意义上的**并发收集器**，它是属于老年代的垃圾收集器，一般与年轻代的 Parallel New GC 配合使用，采用的是标记-清除算法，通过空闲列表来管理内存空间的回收。当 CMS 失败的时候，会退化为 serial old gc。

​	默认情况下，CMS 使用的并发线程数等于 CPU 核心数的 1/4。

​	如果服务器是多核 CPU，并且主要调优目标是降低 GC 停顿导致的系统延迟，那么可以考虑使用 CMS ，不过 CMS 在 jdk9 以后已经被废弃。

###### 优点

并发收集、低停顿

###### 缺点

1. 对 CPU 资源敏感
2. 无法处理浮动垃圾
3. 使用标记-清除算法，会导致空间碎片

###### CMS GC 的六个阶段

1. Initial Mark（初始标记）

   ​	**这个阶段将会触发 STW 事件**，初始标记的目标是标记所有的根对象（GC Roots），包括根对象直接引用的对象，以及被年轻代中所有存活对象引用的对象。

2. Concurrent Mark（并发标记）

   ​	CMS GC 遍历老年代，标记所有存活对象，从前一个阶段找到的跟对象开始算起，该阶段与应用程序同时运行，不用暂停的阶段。

3. Concurrent Prelean（并发预清理）

   ​	该阶段也是与应用程序并发执行，不用停止应用线程。因为上个阶段是并发执行，可能有些引用已经发生了改变，如果在并发标记过程中引用关系发生了变化，JVM 会通过"Card（卡片）"的方式将发生了改变的区域标记为"脏"区，这就是所谓的卡片标记。

4. Final Remark （最终标记）

   ​	**最终标记阶段会触发 STW 事件**，本阶段的目标是完成老年代中所有存活的对象的标记，因为之前预清理阶段是并发执行的，有可能 GC 线程跟不上应用程序修改的速度，所以要通过 STW 来处理各种复杂的情况。

   ​	通常 CMS 会尝试在年轻代尽可能空的情况下执行该阶段，以免连续触发多次 STW 事件。

5. Concurrent Sweep（并发清除）

   ​	此阶段需要触发 STW 事件，JVM 在此阶段删除不再使用的对象，并回收他们所占用的内存空间。

6. Concurrent Reset （并发重置）

   ​	此阶段与应用程序并发执行，重置 CMS 算法相关的内部数据，为下一次 GC 循环做准备。

###### 缺点

​	老年代的内存碎片问题。

##### G1 GC

​	G1 GC 也是一个并发的 GC，全称为 Garbage-First，意为垃圾优先，哪一块的垃圾最多就优先清理它。

​	G1 GC 的设计目标是将 STW 停顿的时间和分布变成可预期可配置的。

​	G1 是一个横跨新生代和老年代的垃圾回收期。它将堆划分为多个（通常是 2048 个）可以存放对象的小块堆区域（smaller heap regions）。每个区域都可以充当 Eden 区、Survivor 区或者 Old 区中的一个。来逻辑上，所有的 Eden 区和 Surivivor 区合起来是年轻代，所有的 Old 区拼在一起就是老年代。

​	G1 不必每次都去收集整个堆空间，而是以增量的方式来进行处理，每次只处理一部分内存块，这些被处理的内存块称为回收集（Collection set），构建回收集的原则是垃圾最多的小块会被优先收集。每次 GC 暂停都会收集所有的年轻代的内存块，但一般只包含部分老年代的内存块。G1 会在并发阶段估算每一个小堆存活对象的总数，垃圾最多的小块会被优先收集。

###### G1 -- 配置参数

| 参数                                            | 含义                                                         |
| ----------------------------------------------- | ------------------------------------------------------------ |
| -XX:+UseG1GC                                    | 启用G1 GC                                                    |
| -XX:G1NewSizePercent（主要参数）                | 初始年轻代占整个 Java Heap 的大小，默认值为 5%               |
| -XX:G1MaxNewSizePercent (主要参数)              | 最大年轻代占整个 Java Heap 的大小，默认值为 60%              |
| -XX:G1HeapRegionSize（主要参数）                | 设置每个 Region 的大小，单位 MB,需要为 1,2,4,8,16,32 中的某个值，默认是堆内存的 1/20000。如果这个值设置比较大，那么大对象就可以进入 Region |
| -XX:ConGCThreads（主要参数）                    | 与 Java 应用一起执行的 GC 线程数量，默认是 1/4               |
| -XX:+InitiatingHeapOccupancyPercent（主要参数） | G1内部并行回收循环启动的阈值，也叫触发标记周期的 Java 堆占用率阈值，默认值为 45%，这里的java堆占比指的是non_young_capacity_bytes，包括old（老年代）+humongous（巨大对象） |
| -XX:G1HeapWastePercent（主要参数）              | G1 停止回收的最小内存大小，默认是堆大小的 5%，也就是说如果 Region 中的对象下降到 5%，就停止收集。 |
| -XX:G1MixedGCCountTarget                        | 设置并行循环之后需要有多少个混合GC启动，默认值是8个。老年代Regions的回 收时间通常比年轻代的收集时间要长一些。所以如果混合收集器比较多，可以允许G1延长老年代的收集时间。 |
| -XX:+G1PrintRegionLivenessInfo                  | 这个参数需要和 -XX:+UnlockDiagnosticVMOptions 配合启动，打印JVM的调试信息， 每个Region里的对象存活信息。 |
| -XX:G1ReservePercent                            | G1为了保留一些空间用于年轻代之间的提升，默认值是堆空间的10%。因为大量执行回收的地方在年 轻代（存活时间较短），所以如果你的应用里面有比较大的堆内存空间、比较多的大对象存活，这里需要保留一些内存。 |
| -XX:+G1SummarizeRSetStats                       | 这也是一个VM的调试信息。如果启用，会在VM退出的时候打印出RSets的详细总结信息。 如果启用-XX:G1SummaryRSetStatsPeriod参数，就会阶段性地打印RSets信息。 |
| -XX:+G1TraceConcRefinement                      | 这个也是一个VM的调试信息，如果启用，并行回收阶段的日志就会被详细打印出来。 |
| -XX:+GCTimeRatio                                | 这个参数就是计算花在Java应用线程上和花在GC线程上的时间比率，默认是9，跟新生代内存的分配比 例一致。这个参数主要的目的是让用户可以控制花在应用上的时间，G1的计算公式是100/（1+GCTimeRatio）。这样如果参 数设置为9，则最多10%的时间会花在GC工作上面。Parallel GC的默认值是99，表示1%的时间被用在GC上面，这是因为 Parallel GC贯穿整个GC，而G1则根据Region来进行划分，不需要全局性扫描整个内存堆。 |
| -XX:+UseStringDeduplication                     | 手动开启Java String对象的去重工作，这个是JDK8u20版本之后新增的参数，主要用于相同 String避免重复申请内存，节约Region的使用。 |
| -XX:MaxGCPauseMills                             | 预期 G1 每次执行 GC 操作的暂停时间，单位是毫秒，默认值是200毫秒，G1 会尽量保证控制在这个范围内。 |

###### 处理步骤

1. 年轻代模式转移暂停（Eva）（Young GC）

   ​	G1 GC会通过前面一段时间的运行情况来不断的调整自己的回收策略和行为，以此来比较稳定地控制 暂停时间。在应用程序刚启动时，G1还没有采集到什么足够的信息，这时候就处于初始的 fully-young 模式。当年轻代空间用满后，**应用线程会被暂停**，年轻代内存块中的存活对象被拷贝到存活 区。如果还没有存活区，则任意选择一部分空闲的内存块作为存活区。

   ​	拷贝的过程称为转移(Evacuation)，这和前面介绍的其他年轻代收集器是一样的工作原理。

2. 并发标记（Gloabal Concurrent Marking）

   ​	当堆内存的使用比例达到一定数值的时候才会触发并发标记，该默认比例是 45%。

   ​	G1并发标记的过程与CMS基本上是一样的。G1的并发标记通过 Snapshot-At-The-Beginning(起始快照 ) 的方式，在标记阶段开始时记下所有的存活对象。即使在标记的同时又有一些变成了垃圾。通过对象的存活信息，可以构建出每个小堆块的存活状态，以便回收集能高效地进行选择。这些信息在接下来的阶段会用来执行老年代区域的垃圾收集。

   ​	有两种情况并发标记是可以完全并发执行的： 一、如果在标记阶段确定某个小堆块中没有存活对象，只包含垃圾； 二、在STW转移暂停期间，同时包含垃圾和存活对象的老年代小堆块。

   ​	并发标记也是由多个阶段组成的：

   ​		阶段一：Initial Mark（初始标记）

   ​			此阶段标记所有从 GC ROOTS 对象直接可达的对象，**该阶段会触发 STW 事件**。

   ​		阶段二：Root Regison Scan（Root 区扫描）

   ​			此阶段标记所有从 "根区域"可达的存活对象。根区域包括：非空的区域，以及在标记过程中不得不手机的区域。**可以并发执行**。

   ​		阶段三：Concurrent Mark（并发标记）

   ​			只遍历对象图，并在一个特殊的位图中标记能访问到的对象。**可以并发执行**。

   ​		阶段四：Remark（再次标记）

   ​			G1收集器会短暂地停止应用线程，停 止并发更新信息的写入，处理其中的少量信息，并标记所有在并发标记开始时未被标记的存活对象。**该阶段会触发 STW 事件**。

   ​		阶段五：Cleanup（清理）

   ​			最后这个清理阶段为即将到来的转移阶段做准备，统计小堆块中所有存活的对象，并将小堆块进行排序，以提升 GC的效率，维护并发标记的内部状态。 所有不包含存活对象的小堆块在此阶段都被回收了。**有一部分任务是并发**的：例如空堆区的回收，还有大部分的存活率计算。**此阶段也需要一个短暂的STW暂停**。

3. 转移暂停：混合模式（Evacuation Pause (mixed)）（Mixed GC）

   ​	并发标记完成之后，G1将执行一次混合收集（mixed collection），就是不只清理年轻代，还将一部 分老年代区域也加入到 回收集 中。混合模式的转移暂停不一定紧跟并发标记阶段。有很多规则和历 史数据会影响混合模式的启动时机。比如，假若在老年代中可以并发地腾出很多的小堆块，就没有必 要启动混合模式。

   ​	因此，在并发标记与混合转移暂停之间，很可能会存在多次 young 模式的转移暂停（young gc）。

   ​	具体添加到回收集的老年代小堆块的大小及顺序，也是基于许多规则来判定的。其中包括指定的软实 时性能指标，存活性，以及在并发标记期间收集的GC效率等数据，外加一些可配置的JVM选项。混合 收集的过程，很大程度上和前面的fully-young gc是一样的。

   ​	**该阶段也会触发 STW 事件**

###### G1 GC 的注意事项

​		某些情况下 G1 触发了 full GC，这时 G1 会退化使用 Serial 收集器来完成垃圾的清理工作，因为是单线程，所以此时的暂停时间将达到秒级别。

有哪几种情况会触发 full GC：

  1. 并发模式失败

     ​	G1 启动标记周期，但在 Mix GC 调用之前，老年代被填满了，这时候 G1 会放弃标记周期。

     ​	解决办法：增加堆大小，或者调整周期（例如增加线程数 -XX:ConcGCThreads 来提升 GC 的速度）

     	2. 晋升失败

     ​	没有足够的空间供存活对象或晋升对象使用，由此触发了 Full GC（to-space exhausted/to-space overflow）。

     ​	解决办法：

     ​	a)增加 -XX:G1ReservePercent 选项的值（并相应增加总的堆大小）增加预留内存量。 

     ​	b)通过减少 -XX:InitiatingHeapOccupancyPercent 提前启动标记周期。 

     ​	c)也可以通过增加 -XX:ConcGCThreads 选项的值来增加并行标记线程的数目。

     	3. 巨型对象分配失败

     ​	当巨型对象找不到合适的空间进行分配的时候，就会启动 full GC。

     ​	解决办法：增加内存或者增大-XX:G1HeapRegionSize

##### 各个 GC 对比

| 收集器            | 串行、并行 or 并发 | 新生代/老年代 | 算法               | 目标         | 适用场景                                      |
| ----------------- | ------------------ | ------------- | ------------------ | ------------ | --------------------------------------------- |
| Serial            | 串行               | 新生代        | 复制算法           | 响应速度优先 | 单 CPU 环境下的 client 模式                   |
| Serial Old        | 串行               | 老年代        | 标记-整理          | 响应速度优先 | 单 CPU 环境下的 Client 模式、CMS 的后备预案   |
| ParNew            | 并行               | 新生代        | 复制算法           | 响应速度优先 | 多 CPU 环境时在 Server 模式下与 CMS 配合      |
| Parallel Scavenge | 并行               | 新生代        | 复制算法           | 吞吐量优先   | 在后台运算而不需要太多交互的任务              |
| Parallel Old      | 并行               | 老年代        | 标记-整理          | 吞吐量优先   | 在后台运算而不需要太多交互的任务              |
| CMS               | 并发               | 老年代        | 标记-清除          | 响应速度优先 | 集中在互联网战或 B/S 系统服务端上的 Java 应用 |
| G1                | 并发               | both          | 标记-整理+复制算法 | 响应速度优先 | 面向服务端应用，将来替换 CMS（jdk9 以后）     |

##### 常用的 GC 组合

1. Serial + Serial Old 实现单线程的低延迟垃圾回收机制
2. ParNew + CMS，实现多线程的低延迟垃圾回收机制
3. Parallel Scavenge + Parallel Old ，实现高多线程的高吞吐量垃圾回收机制

##### GC 如何选择

指导原则:

1. 如果系统考虑吞吐优先（商城、流量不大的系统），CPU 资源都用来最大程度处理业务，用 Parallel GC
2. 如果系统考虑低延迟优先（高频交易），每次 GC 时间尽量短，用 CMS GC
3. 如果系统内存堆较大，同时希望整体来看平均 GC 时间可控，使用 G1 GC，因为 G1 将堆内存分为很多的小块，这样在进行垃圾回收的时候可控，如果是分代的 GC，可能会出现老年代一次收集花费很长的时间

对于堆内存大小的考量：

1. 一般 4G 以上，算是比较大，用 G1 的性价比高。
2. 一般超过 8G ，比如 16G-64G内存，非常推荐使用 G1 GC。

##### ZGC 介绍

目前只能在 linux 上使用该 GC

通过着色指针和读屏障，实现几乎全部的并发执行，几毫秒级别的延迟，线性可扩展

```java
// 需要开启实验性开关才能使用
-XX:+UnlockExpermentalVMOptions
// 使用 ZGC
-XX:+UseZGC
```

###### 主要特点

1. GC 最大停顿时间不超过 10ms
2. 堆内存支持范围广，小至几百 MB 的堆空间，大至 4TB 的超大堆内存（JDK 13 升至 16TB）
3. 与 G1 相比，应用吞吐量下降不超过 15%

##### ShennandoahGC 介绍

```java
// 需要开启实验性开关才能使用
-XX:+UnlockExpermentalVMOptions
// 使用 ShennandoahGC
-XX:+UseShennandoahGC
```

​	设计为 GC线程与应用线程并发执行的方式，通过实 现垃圾回收过程的并发处理，改善停顿时间， 使得GC执行线程能够在业务处理线程运行过 程中进行堆压缩、标记和整理，从而消除了 绝大部分的暂停时间。

​	Shenandoah 团队对外宣称Shenandoah GC的暂停时间与堆大小无关，无论是200 MB 还是 200 GB的堆内存，都可以保障具有 很低的暂停时间（注意:并不像ZGC那样保证 暂停时间在10ms以内）。

##### 垃圾器相关问题

1. 说说 java 默认情况下是使用什么 GC 策略

   Jdk 8 和 8 之前默认使用的是并行 GC ，8以后（不包括8）使用的是 G1 GC。