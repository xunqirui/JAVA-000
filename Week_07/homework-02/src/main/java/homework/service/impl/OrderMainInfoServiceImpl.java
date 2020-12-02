package homework.service.impl;

import homework.annotation.ReadOnly;
import homework.dao.OrderMainInfoDao;
import homework.entity.OrderMainInfo;
import homework.service.OrderMainInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderMainInfoServiceImpl
 *
 * @author qrXun on 2020/12/2
 */
@Service
public class OrderMainInfoServiceImpl implements OrderMainInfoService {

    @Autowired
    private OrderMainInfoDao orderMainInfoDao;

    @Override
    @ReadOnly
    public OrderMainInfo findById(Long id) {
        return orderMainInfoDao.findById(id);
    }
}
