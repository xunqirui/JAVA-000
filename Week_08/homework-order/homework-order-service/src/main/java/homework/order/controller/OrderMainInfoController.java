package homework.order.controller;

import homework.order.dto.OrderDTO;
import homework.order.service.OrderMainInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * OrderMainInfoController
 *
 * @author qrXun on 2020/12/10
 */
@Controller
@RequestMapping("orderMainInfo")
public class OrderMainInfoController {

    @Autowired
    private OrderMainInfoService orderMainInfoService;

    @PostMapping("/save")
    public void save(@RequestBody OrderDTO orderDTO){
        orderMainInfoService.save(orderDTO);
    }

}
