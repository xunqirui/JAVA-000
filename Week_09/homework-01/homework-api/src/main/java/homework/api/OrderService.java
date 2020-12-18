package homework.api;

import homework.annotation.Post;
import homework.entity.Order;

/**
 * OrderService
 *
 * @author qrXun on 2020/12/16
 */
public interface OrderService {

    @Post(url = "http://localhost:8080/")
    Order findOrderById(int id);

}
