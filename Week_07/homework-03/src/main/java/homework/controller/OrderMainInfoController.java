package homework.controller;

import homework.entity.OrderMainInfo;
import homework.service.OrderMainInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * OrderMainInfoController
 *
 * @author qrXun on 2020/12/3
 */
@RestController
public class OrderMainInfoController {

    @Autowired
    private OrderMainInfoService orderMainInfoService;

    @GetMapping("findById")
    public OrderMainInfo findById(){
        return orderMainInfoService.findById(1L);
    }

    @GetMapping("save")
    public OrderMainInfo save(){
        OrderMainInfo orderMainInfo = new OrderMainInfo();
        orderMainInfo.setInsertTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return orderMainInfoService.save(orderMainInfo);
    }

}
