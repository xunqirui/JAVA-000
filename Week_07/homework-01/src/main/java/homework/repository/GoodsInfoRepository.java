package homework.repository;

import com.google.common.base.CaseFormat;
import homework.entity.GoodsInfo;
import homework.jdbc.AbstractJdbcCrud;
import homework.jdbc.util.ConnectionMethod;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static homework.util.ReflectUtil.getGenericTypeClass;

/**
 * GoodInfoRepository
 * 商品存储
 *
 * @author qrXun on 2020/12/2
 */
public class GoodsInfoRepository extends AbstractJdbcCrud<GoodsInfo, ConnectionMethod> {

    public GoodsInfoRepository(ConnectionMethod connectionMethod) {
        super(connectionMethod);
    }

    @Override
    protected Class<?> getCurrentClass() {
        return GoodsInfoRepository.class;
    }

    /**
     * 递减
     *
     * @param step 步长
     */
    public void decrease(int step) {
        try (Connection connection = connectionMethod.getConnection()){
            connection.setAutoCommit(false);
            // 获取当前泛型类型
            Class<?> clazz = getGenericTypeClass(getCurrentClass());
            // 获取 table 名称
            Table table = clazz.getAnnotation(Table.class);
            String tableName;
            if (!StringUtils.hasText(table.name())) {
                tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
            } else {
                tableName = table.name();
            }
            String sql = "update " + tableName + " set stock = stock - " + step;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
