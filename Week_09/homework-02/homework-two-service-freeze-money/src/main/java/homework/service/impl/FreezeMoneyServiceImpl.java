package homework.service.impl;

import homework.api.FreezeMoneyService;
import homework.entity.FreezeMoney;
import homework.repository.FreezeMoneyRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FreezeMoneyServiceImpl
 *
 * @author qrXun on 2020/12/18
 */
@Service
@DubboService(version = "1.0.0")
public class FreezeMoneyServiceImpl implements FreezeMoneyService {

    @Autowired
    private FreezeMoneyRepository freezeMoneyRepository;

    @Override
    @HmilyTCC(cancelMethod = "cancelInsert")
    public int insertFreezeMoney(FreezeMoney freezeMoney) {
        return freezeMoneyRepository.insertFreezeMoney(freezeMoney);
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelDecrease")
    public int decreaseFreezeMoney(FreezeMoney freezeMoney) {
        return freezeMoneyRepository.decreaseFreezeMoney(freezeMoney);
    }

    public void cancelInsert(FreezeMoney freezeMoney){
        decreaseFreezeMoney(freezeMoney);
    }

    public void cancelDecrease(FreezeMoney freezeMoney){
        insertFreezeMoney(freezeMoney);
    }
}
