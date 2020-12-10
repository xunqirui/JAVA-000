package homework.goods.repository;

import homework.goods.entity.GoodsType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * GoodsTypeRepository
 *
 * @author qrXun on 2020/12/10
 */
@Repository
public interface GoodsTypeRepository extends CrudRepository<GoodsType, Long> {
}
