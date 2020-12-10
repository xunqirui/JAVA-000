package homework.goods.service;

import homework.goods.entity.GoodsType;

/**
 * GoosTypeService
 *
 * @author qrXun on 2020/12/10
 */
public interface GoodsTypeService {

    Iterable<GoodsType> findAll();
}
