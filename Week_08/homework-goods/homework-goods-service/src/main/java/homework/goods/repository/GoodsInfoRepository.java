package homework.goods.repository;

import homework.goods.entity.GoodsInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * GoodsInfoRepository
 *
 * @author qrXun on 2020/12/10
 */
@Repository
public interface GoodsInfoRepository extends CrudRepository<GoodsInfo, Long> {

    @Query(
        "update GoodsInfo a set a.stock = a.stock - ?1 where a.id = ?2 and a.stock > 0"
    )
    int decreaseStock(@Param("count") Integer count, @Param("goodsId")Long goodsId);

    @Query(
            "update GoodsInfo a set a.stock = a.stock + ?1 where a.id = ?2 and a.stock >= 0"
    )
    int increaseStock(@Param("count") Integer count, @Param("goodsId")Long goodsId);

}
