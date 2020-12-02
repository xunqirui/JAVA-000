package homework.repository;

import homework.entity.ReceiverInfo;
import homework.jdbc.AbstractJdbcCrud;
import homework.jdbc.util.ConnectionMethod;

/**
 * ReceiverInfoRepository
 *
 * @author qrXun on 2020/12/2
 */
public class ReceiverInfoRepository extends AbstractJdbcCrud<ReceiverInfo, ConnectionMethod> {

    public ReceiverInfoRepository(ConnectionMethod connectionMethod) {
        super(connectionMethod);
    }

    @Override
    protected Class<?> getCurrentClass() {
        return ReceiverInfoRepository.class;
    }
}
