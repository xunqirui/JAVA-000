package homework.order.client;

import homework.goods.entity.GoodsInfo;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * GoodsInfoClient
 *
 * @author qrXun on 2020/12/10
 */
@FeignClient(value = "goods")
public interface GoodsInfoClient {

    @PostMapping("/goodsInfo/save")
    @Hmily
    GoodsInfo save(GoodsInfo goodsInfo);

    @PostMapping("/goodsInfo/decreaseStock")
    @Hmily
    int decreaseStock(@RequestParam("count") Integer count, @RequestParam("goodsId") Long goodsId);

}
