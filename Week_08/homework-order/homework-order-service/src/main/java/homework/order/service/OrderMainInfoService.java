package homework.order.service;

import homework.order.dto.OrderDTO;
import homework.order.entity.OrderMainInfo;

/**
 * OrderMainInfoService
 *
 * @author qrXun on 2020/12/10
 */
public interface OrderMainInfoService {

    void save(OrderDTO orderDTO);

    Iterable<OrderMainInfo> findAll();

    void delete(OrderMainInfo orderMainInfo);

    void update(OrderMainInfo oldMainInfo, OrderMainInfo newMainInfo);

}
