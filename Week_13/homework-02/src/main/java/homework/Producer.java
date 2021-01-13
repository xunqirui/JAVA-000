package homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Producer
 *
 * @author qrXun on 2021/1/13
 */
@RestController
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("sendMessage")
    public String sendMessage(){
        kafkaTemplate.send("test", "name", "hello world");
        return "OK";
    }

}
