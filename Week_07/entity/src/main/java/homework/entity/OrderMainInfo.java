package homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单主信息
 *
 * @author qrXun
 * @since 2020-12-01 21:53:45
 */
@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMainInfo implements Serializable {

    @Transient
    private static final long serialVersionUID = 786318680779430733L;

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
    private BigDecimal totalPrice;
    /**
     * 买家留言
     */
    private String leaveMessage;
    /**
     * 运费
     */
    private BigDecimal transportPrice;
    /**
     * 收件人详情ID
     */
    private Long receiverInfoId;
    /**
     * 用户ID
     */
    private Long buyerId;

}