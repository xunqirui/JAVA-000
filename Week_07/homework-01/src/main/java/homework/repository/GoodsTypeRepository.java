package homework.repository;

import homework.entity.GoodsType;
import homework.jdbc.AbstractJdbcCrud;
import homework.jdbc.util.ConnectionMethod;

/**
 * GoodsTypeRepository
 *
 * @author qrXun on 2020/12/2
 */
public class GoodsTypeRepository extends AbstractJdbcCrud<GoodsType, ConnectionMethod> {

    public GoodsTypeRepository(ConnectionMethod connectionMethod) {
        super(connectionMethod);
    }

    @Override
    protected Class<?> getCurrentClass() {
        return GoodsTypeRepository.class;
    }
}
