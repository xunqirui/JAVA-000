package homework.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * (OrderMainInfo)实体类
 *
 * @author makejava
 * @since 2020-12-10 00:33:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_main_info")
public class OrderMainInfo implements Serializable {
    private static final long serialVersionUID = -99468048068789129L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "jdbc"
    )
    private Long id;
    /**
     * 订单唯一ID
     */
    private Long uniqueId;
    /**
     * 创建时间
     */
    private String insertTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 订单状态：0-提交未付钱、1-已付钱未发货、2-已发货、3-买家已拿货、4-买家已最终确认收货
     */
    private Integer orderStatus;
    /**
     * 商品总价（元）
     */
    private Double totalPrice;
    /**
     * 买家留言
     */
    private String leaveMessage;
    /**
     * 运费
     */
    private Double transportPrice;
    /**
     * 收件人详情ID
     */
    private Integer receiverInfoId;
    /**
     * 用户ID
     */
    private Long buyerId;

}