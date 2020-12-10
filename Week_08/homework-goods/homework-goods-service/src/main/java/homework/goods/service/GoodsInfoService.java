package homework.goods.service;

import homework.goods.entity.GoodsInfo;

/**
 * GoodsInfoService
 *
 * @author qrXun on 2020/12/10
 */
public interface GoodsInfoService {

    GoodsInfo save(GoodsInfo goodsInfo);

    int decreaseStock(Integer count, Long goodsId);

}
