package homework.api;

import homework.entity.Account;

/**
 * AccountService
 *
 * @author qrXun on 2020/12/18
 */
public interface AccountService {

    int decreaseMoney(Account account);

    int insertMoney(Account account);

}
