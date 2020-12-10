package homework.user.controller;

import homework.user.entity.UserInfo;
import homework.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UserInfoController
 *
 * @author qrXun on 2020/12/9
 */
@Controller
@RequestMapping("userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("findAll")
    public Iterable<UserInfo> findAll(){
        return userInfoService.findAll();
    }


}
