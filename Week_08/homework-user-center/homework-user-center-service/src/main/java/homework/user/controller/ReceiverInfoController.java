package homework.user.controller;

import homework.user.entity.ReceiverInfo;
import homework.user.service.ReceiverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ReceiverInfoController
 *
 * @author qrXun on 2020/12/9
 */
@Controller
@RequestMapping("receiverInfo")
public class ReceiverInfoController {

    @Autowired
    private ReceiverInfoService receiverInfoService;

    @GetMapping("findAll")
    public Iterable<ReceiverInfo> findAll(){
        return receiverInfoService.findAll();
    }

}
