package homework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * DynamicDataSourceApplication
 *
 * @author qrXun on 2020/12/2
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("homework.dao")
public class DynamicDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceApplication.class, args);
    }

}
