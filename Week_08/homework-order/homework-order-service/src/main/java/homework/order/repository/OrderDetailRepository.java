package homework.order.repository;

import homework.order.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderDetailRepository
 *
 * @author qrXun on 2020/12/10
 */
@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {

    OrderDetail findByIdAndBuyerIdAndOrderId(Long id, Long buyerId, Long orderId);

}
