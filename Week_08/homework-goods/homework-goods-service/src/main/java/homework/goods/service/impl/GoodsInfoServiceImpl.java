package homework.goods.service.impl;

import homework.goods.entity.GoodsInfo;
import homework.goods.repository.GoodsInfoRepository;
import homework.goods.service.GoodsInfoService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GoodsInfoServiceImpl
 *
 * @author qrXun on 2020/12/10
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsInfoServiceImpl.class);

    @Override
    @HmilyTCC(cancelMethod = "cancel")
    public GoodsInfo save(GoodsInfo goodsInfo) {
        return goodsInfoRepository.save(goodsInfo);
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelDecrease")
    public int decreaseStock(Integer count, Long goodsId) {
        return goodsInfoRepository.decreaseStock(count, goodsId);
    }

    public boolean cancel(final GoodsInfo accountDTO) {
        LOGGER.info("============执行cancel 删除接口===============");
        goodsInfoRepository.delete(accountDTO);
        return true;
    }

    public boolean cancelDecrease(Integer count, Long goodsId){
        LOGGER.info("============执行取消减库存接口=============");
        goodsInfoRepository.increaseStock(count, goodsId);
        return true;
    }
}
