package homework.order.service.impl;

import homework.goods.entity.GoodsInfo;
import homework.order.entity.OrderDetail;
import homework.order.repository.OrderDetailRepository;
import homework.order.service.OrderDetailService;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * OrderDetailServiceImpl
 *
 * @author qrXun on 2020/12/10
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @HmilyTCC(cancelMethod = "cancelSave")
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public Iterable<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelDelete")
    public void delete(OrderDetail orderDetail) {
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    public void update(OrderDetail oldDetail, OrderDetail newDetail) {
            newDetail.setId(oldDetail.getId());
            orderDetailRepository.save(newDetail);
    }

    public boolean cancelSave(final OrderDetail orderDetail){
        orderDetailRepository.delete(orderDetail);
        return true;
    }

    public boolean cancelDelete(final OrderDetail orderDetail){
        orderDetailRepository.save(orderDetail);
        return true;
    }

    public boolean cancelUpdate(final OrderDetail orderDetail, final OrderDetail newDetail){
        orderDetailRepository.save(orderDetail);
        return true;
    }
}
