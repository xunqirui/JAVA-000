package homework.controller;

import homework.entity.Account;
import homework.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProviderController
 *
 * @author qrXun on 2020/12/18
 */
@RestController("provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @GetMapping("changeMoney")
    public String changeDollarToRmb(Account account){
        providerService.changeDollarToRmb(account);
        return "success";
    }

}
