package homework.repository;

import homework.entity.OrderDetail;
import homework.jdbc.AbstractJdbcCrud;
import homework.jdbc.util.ConnectionMethod;

/**
 * OrderDetailRepository
 *
 * @author qrXun on 2020/12/2
 */
public class OrderDetailRepository extends AbstractJdbcCrud<OrderDetail, ConnectionMethod> {

    public OrderDetailRepository(ConnectionMethod connectionMethod) {
        super(connectionMethod);
    }

    @Override
    protected Class<?> getCurrentClass() {
        return OrderDetailRepository.class;
    }
}
