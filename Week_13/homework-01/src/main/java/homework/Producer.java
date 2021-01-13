package homework;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Product
 * 生产者
 *
 * @author qrXun on 2021/1/12
 */
public class Producer {

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
            // 创建生产者
//            MessageProducer producer = session.createProducer(topicDestination);
            MessageProducer producer = session.createProducer(queueDestination);
            int index = 0;
            while (index < 100){
                TextMessage textMessage = session.createTextMessage(index + "_active_mq_test_message");
                producer.send(textMessage);
                index++;
            }
            session.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
