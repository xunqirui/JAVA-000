package homework.order.service;

import homework.order.entity.OrderDetail;

/**
 * OrderDetailService
 *
 * @author qrXun on 2020/12/10
 */
public interface OrderDetailService {

    void save(OrderDetail orderDetail);

    Iterable<OrderDetail> findAll();

    void delete(OrderDetail orderDetail);

    void update(OrderDetail oldDetail, OrderDetail newDetail);

}
