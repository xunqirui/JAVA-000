package homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;

/**
 * ShardingSphereApplication
 *
 * @author qrXun on 2020/12/3
 */
@SpringBootApplication(exclude = JtaAutoConfiguration.class)
public class ShardingSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereApplication.class, args);
    }

}
