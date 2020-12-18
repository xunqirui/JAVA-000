package homework.repository;

import homework.entity.FreezeMoney;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * FreezeMoneyMapper
 *
 * @author qrXun on 2020/12/18
 */
@Repository
public interface FreezeMoneyRepository extends CrudRepository<FreezeMoney, Long> {

    @Query(
            "update FreezeMoney a set a.money = a.money + :#{#freezeMoney.money} " +
                    "where a.accountId = :#{#freezeMoney.accountId} and a.moneySource = :#{#freezeMoney.moneySource}"
    )
    int insertFreezeMoney(@Param("freezeMoney") FreezeMoney freezeMoney);

    @Query(
            "update FreezeMoney a set a.money = a.money - :#{#freezeMoney.money} " +
                    "where a.accountId = :#{#freezeMoney.accountId} and a.moneySource = :#{#freezeMoney.moneySource} and (a.money - :#{freezeMoney.money} > 0)"
    )
    int decreaseFreezeMoney(@Param("freezeMoney") FreezeMoney freezeMoney);

}
