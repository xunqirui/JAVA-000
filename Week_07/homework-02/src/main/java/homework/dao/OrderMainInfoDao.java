package homework.dao;

import homework.entity.OrderMainInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * OrderMainInfoDao
 *
 * @author qrXun on 2020/12/2
 */
@Mapper
@Repository
public interface OrderMainInfoDao{

    OrderMainInfo findById(Long id);

}
