package homework;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumer
 *
 * @author qrXun on 2021/1/13
 */
@Component
public class Consumer {

    @KafkaListener(topics = "test")
    public void processMessage(String content){
        System.out.println("收到消息: " + content);
    }

}
