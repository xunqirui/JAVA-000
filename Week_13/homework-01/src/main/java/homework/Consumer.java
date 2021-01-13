package homework;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Consumer
 * 消费者
 *
 * @author qrXun on 2021/1/12
 */
public class Consumer {

    public static void main(String[] args) {
        try {
            // 创建连接和会话
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // topic
//            Destination topicDestination = new ActiveMQTopic(Const.TOPIC_NAME);
            // queue
            Destination queueDestination = new ActiveMQQueue(Const.QUEUE_NAME);
//            MessageConsumer consumer = session.createConsumer(topicDestination);
            MessageConsumer consumer = session.createConsumer(queueDestination);
            // 绑定消息监听器
            consumer.setMessageListener(message -> System.out.println("接收到消息：" + message));
            //8、程序等待接收用户消息
            System.in.read();
            consumer.close();
            session.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
