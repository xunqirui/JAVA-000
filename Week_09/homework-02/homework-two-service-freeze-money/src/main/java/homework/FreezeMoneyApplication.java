package homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * FreezeMoneyApplication
 *
 * @author qrXun on 2020/12/18
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class FreezeMoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreezeMoneyApplication.class, args);
    }

}
