package homework.service.impl;

import homework.entity.OrderMainInfo;
import homework.repoistory.OrderMainRepository;
import homework.service.OrderMainInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderMainInfoServiceImpl
 *
 * @author qrXun on 2020/12/3
 */
@Service
public class OrderMainInfoServiceImpl implements OrderMainInfoService {

    @Autowired
    private OrderMainRepository orderMainRepository;

    @Override
    public OrderMainInfo findById(Long id) {
        return orderMainRepository.findById(id).orElse(null);
    }

    @Override
    public OrderMainInfo save(OrderMainInfo orderMainInfo) {
        return orderMainRepository.save(orderMainInfo);
    }
}
