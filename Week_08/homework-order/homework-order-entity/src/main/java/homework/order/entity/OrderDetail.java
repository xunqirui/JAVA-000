package homework.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * (OrderDetail)实体类
 *
 * @author makejava
 * @since 2020-12-10 00:31:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 822896265083216292L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "jdbc"
    )
    private Long id;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 商品数量
     */
    private Integer goodsNum;
    /**
     * 购买时价格
     */
    private Double price;
    /**
     * 总价
     */
    private Double totalPrice;
    /**
     * 用户ID
     */
    private Long buyerId;

}