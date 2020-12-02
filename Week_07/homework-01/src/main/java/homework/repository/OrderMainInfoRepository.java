package homework.repository;

import homework.entity.OrderMainInfo;
import homework.jdbc.AbstractJdbcCrud;
import homework.jdbc.util.ConnectionMethod;

/**
 * OrderMainInfoRepository
 *
 * @author qrXun on 2020/12/2
 */
public class OrderMainInfoRepository extends AbstractJdbcCrud<OrderMainInfo, ConnectionMethod> {

    public OrderMainInfoRepository(ConnectionMethod connectionMethod) {
        super(connectionMethod);
    }

    @Override
    protected Class<?> getCurrentClass() {
        return OrderMainInfoRepository.class;
    }
}
