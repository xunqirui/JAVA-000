package homework.goods.service.impl;

import homework.goods.entity.GoodsType;
import homework.goods.repository.GoodsTypeRepository;
import homework.goods.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GoodsTypeServiceImpl
 *
 * @author qrXun on 2020/12/10
 */
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Autowired
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public Iterable<GoodsType> findAll() {
        return goodsTypeRepository.findAll();
    }
}
