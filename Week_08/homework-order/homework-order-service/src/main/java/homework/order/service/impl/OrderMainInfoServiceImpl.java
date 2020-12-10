package homework.order.service.impl;

import homework.order.client.GoodsInfoClient;
import homework.order.dto.OrderDTO;
import homework.order.entity.OrderMainInfo;
import homework.order.repository.OrderMainInfoRepository;
import homework.order.service.OrderDetailService;
import homework.order.service.OrderMainInfoService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderMainInfoServiceImpl
 *
 * @author qrXun on 2020/12/10
 */
@Service
public class OrderMainInfoServiceImpl implements OrderMainInfoService {

    @Autowired
    private OrderMainInfoRepository orderMainInfoRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private GoodsInfoClient goodsInfoClient;

    @Override
    @HmilyTCC(cancelMethod = "cancelSave")
    public void save(OrderDTO orderDTO) {
        OrderMainInfo mainInfo = orderDTO.getOrderMainInfo();
        orderMainInfoRepository.save(mainInfo);
        orderDTO.getDetailList().forEach(orderDetail -> {
            orderDetailService.save(orderDetail);
            goodsInfoClient.decreaseStock(orderDetail.getGoodsNum(), orderDetail.getGoodsId());
        });

    }

    @Override
    public Iterable<OrderMainInfo> findAll() {
        return orderMainInfoRepository.findAll();
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelDelete")
    public void delete(OrderMainInfo orderMainInfo) {
        orderMainInfoRepository.delete(orderMainInfo);
    }

    @Override
    public void update(OrderMainInfo orderMainInfo, OrderMainInfo newOrderMainInfo) {
        newOrderMainInfo.setId(orderMainInfo.getId());
        orderMainInfoRepository.save(newOrderMainInfo);
    }

    public boolean cancelSave(OrderDTO orderDTO){
        OrderMainInfo mainInfo = orderDTO.getOrderMainInfo();
        orderMainInfoRepository.delete(mainInfo);
        orderDTO.getDetailList().forEach(orderDetail -> {
            orderDetailService.delete(orderDetail);
        });
        return true;
    }

    public boolean cancelDelete(OrderMainInfo orderMainInfo){
        orderMainInfoRepository.save(orderMainInfo);
        return true;
    }
}
