package homework.user.repository;

import homework.user.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * UserInfoRepository
 *
 * @author qrXun on 2020/12/9
 */
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
}
