package homework.service;

import homework.entity.Account;
import org.dromara.hmily.annotation.Hmily;

/**
 * ProviderService
 *
 * @author qrXun on 2020/12/18
 */
public interface ProviderService {

    @Hmily
    void changeDollarToRmb(Account account);

}
