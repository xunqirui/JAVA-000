package homework.repoistory;

import homework.entity.OrderMainInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * OrderMainRepository
 *
 * @author qrXun on 2020/12/3
 */
public interface OrderMainRepository extends CrudRepository<OrderMainInfo, Long> {
}
