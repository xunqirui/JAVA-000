package homework.order.repository;

import homework.order.entity.OrderMainInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderMainInfoRepository
 *
 * @author qrXun on 2020/12/10
 */
@Repository
public interface OrderMainInfoRepository extends CrudRepository<OrderMainInfo, Long> {
}
