package homework.service.impl;

import homework.entity.Account;
import homework.repository.AccountRepository;
import homework.api.AccountService;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AccountServiceImpl
 *
 * @author qrXun on 2020/12/18
 */
@DubboService(version = "1.0.0")
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountMapper;

    @Override
    @HmilyTCC(cancelMethod = "cancelDecrease")
    public int decreaseMoney(Account account) {
        return accountMapper.decreaseMoney(account);
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelInsert")
    public int insertMoney(Account account) {
        return accountMapper.insertMoney(account);
    }

    public void cancelDecrease(Account account){
        insertMoney(account);
    }

    public void cancelInsert(Account account){
        decreaseMoney(account);
    }
}
