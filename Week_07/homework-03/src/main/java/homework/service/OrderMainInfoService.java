package homework.service;

import homework.entity.OrderMainInfo;

/**
 * OrderMainInfoService
 *
 * @author qrXun on 2020/12/3
 */
public interface OrderMainInfoService {

    OrderMainInfo findById(Long id);

    OrderMainInfo save(OrderMainInfo orderMainInfo);

}
