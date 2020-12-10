package homework.user.repository;

import homework.user.entity.ReceiverInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ReceiverInfoRepository
 *
 * @author qrXun on 2020/12/9
 */
@Repository
public interface ReceiverInfoRepository extends CrudRepository<ReceiverInfo, Long> {
}
