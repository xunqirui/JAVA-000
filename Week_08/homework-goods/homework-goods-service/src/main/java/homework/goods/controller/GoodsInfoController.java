package homework.goods.controller;

import homework.goods.entity.GoodsInfo;
import homework.goods.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * GoodsInfoController
 *
 * @author qrXun on 2020/12/10
 */
@RequestMapping("goodsInfo")
public class GoodsInfoController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @PostMapping("save")
    public GoodsInfo save(@RequestBody GoodsInfo goodsInfo){
        return goodsInfoService.save(goodsInfo);
    }

    @PostMapping("decreaseStock")
    public int decreaseStock(Integer count, Long goodsId){
        return goodsInfoService.decreaseStock(count, goodsId);
    }



}
