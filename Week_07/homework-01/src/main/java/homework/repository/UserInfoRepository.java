package homework.repository;

import homework.entity.UserInfo;
import homework.jdbc.AbstractJdbcCrud;
import homework.jdbc.util.ConnectionMethod;

/**
 * UserInfoRepository
 *
 * @author qrXun on 2020/12/2
 */
public class UserInfoRepository extends AbstractJdbcCrud<UserInfo, ConnectionMethod> {

    public UserInfoRepository(ConnectionMethod connectionMethod) {
        super(connectionMethod);
    }

    @Override
    protected Class<?> getCurrentClass() {
        return UserInfoRepository.class;
    }
}
