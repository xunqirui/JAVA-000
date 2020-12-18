package homework.repository;

import homework.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * AccountMapper
 *
 * @author qrXun on 2020/12/17
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query(
            "update Account a set a.money = a.money - :#{#account.money} " +
                    " where a.accountId = :#{#account.accountId} and (a.money - :#{#account.money}) > 0 and a.moneySource = :#{#account.moneySource}"
    )
    int decreaseMoney(@Param("account") Account account);


    @Query(
            "update Account a set a.money = a.money + :#{#account.money} " +
                    " where a.accountId = :#{#account.accountId} and a.moneySource = :#{#account.moneySource}"
    )
    int insertMoney(@Param("account") Account account);

}
