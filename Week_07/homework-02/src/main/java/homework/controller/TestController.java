package homework.controller;

import homework.entity.OrderMainInfo;
import homework.service.OrderMainInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author qrXun on 2020/12/2
 */
@RestController
public class TestController {

    @Autowired
    private OrderMainInfoService orderMainInfoService;

    @RequestMapping("orderMain")
    public OrderMainInfo orderMain(){
        return orderMainInfoService.findById(1L);
    }

}
