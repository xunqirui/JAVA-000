package homework.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * UserCenterServiceApplication
 *
 * @author qrXun on 2020/12/9
 */
@SpringBootApplication
@EnableEurekaClient
public class UserCenterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterServiceApplication.class, args);
    }

}
