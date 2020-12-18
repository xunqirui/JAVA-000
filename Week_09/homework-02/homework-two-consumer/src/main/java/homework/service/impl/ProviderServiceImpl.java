package homework.service.impl;

import homework.api.AccountService;
import homework.api.FreezeMoneyService;
import homework.entity.Account;
import homework.entity.FreezeMoney;
import homework.service.ProviderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * ProviderServiceImpl
 *
 * @author qrXun on 2020/12/18
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
    private AccountService accountService;

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12346")
    private FreezeMoneyService freezeMoneyService;

    @Override
    @Hmily
    public void changeDollarToRmb(Account account) {
        accountService.decreaseMoney(account);
        FreezeMoney freezeMoney = FreezeMoney.builder()
                .money(account.getMoney())
                .moneySource(account.getMoneySource())
                .accountId(account.getAccountId())
                .build();
        freezeMoneyService.insertFreezeMoney(freezeMoney);
        Account rmbAccount = Account.builder()
                .accountId(account.getAccountId())
                .money(account.getMoney().multiply(new BigDecimal("7")))
                .moneySource(1)
                .build();
        accountService.insertMoney(rmbAccount);
        freezeMoneyService.decreaseFreezeMoney(freezeMoney);
    }
}
