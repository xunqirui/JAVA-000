package homework.consumer;

import homework.api.OrderService;
import homework.api.UserService;
import homework.client.Rpcfx;
import homework.entity.Order;
import homework.entity.User;

/**
 * ServerConsumer
 *
 * @author qrXun on 2020/12/17
 */
public class ServerConsumer {
    public static void main(String[] args) {
        OrderService orderService = Rpcfx.create(OrderService.class);
        Order order = orderService.findOrderById(1);
        System.out.println(order);

        UserService userService = Rpcfx.create(UserService.class);
        User user = userService.findById(1);
        System.out.println(user);
    }
}
