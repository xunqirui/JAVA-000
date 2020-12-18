package homework.service;

import homework.api.OrderService;
import homework.entity.Order;
import org.springframework.stereotype.Service;

/**
 * OrderServiceImpl
 *
 * @author qrXun on 2020/12/17
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
