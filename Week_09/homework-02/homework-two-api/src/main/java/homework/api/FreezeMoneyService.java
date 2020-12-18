package homework.api;

import homework.entity.FreezeMoney;

import java.math.BigDecimal;

/**
 * FreezeMoneyService
 *
 * @author qrXun on 2020/12/17
 */
public interface FreezeMoneyService {

    /**
     * 新增冻结资产
     * @param freezeMoney
     * @return
     */
    int insertFreezeMoney(FreezeMoney freezeMoney);

    /**
     * 减少冻结资产
     * @param freezeMoney
     * @return
     */
    int decreaseFreezeMoney(FreezeMoney freezeMoney);

}
