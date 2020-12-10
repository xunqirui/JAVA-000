package homework.order.dto;

import homework.order.entity.OrderDetail;
import homework.order.entity.OrderMainInfo;
import homework.order.entity.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * OrderDTO
 *
 * @author qrXun on 2020/12/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    /**
     * 订单ID
     */
    private Long uniqueId;
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

    /**
     * 订单详情
     */
    private List<OrderDetail> detailList;

    /**
     * 获取主订单信息
     * @return
     */
    public OrderMainInfo getOrderMainInfo() {
        SnowFlake snowFlake = new SnowFlake(1, 1);
        OrderMainInfo orderMainInfo = OrderMainInfo.builder()
                .uniqueId(Optional.of(uniqueId).orElse(snowFlake.nextId()))
                .orderStatus(orderStatus)
                .totalPrice(totalPrice)
                .leaveMessage(leaveMessage)
                .transportPrice(transportPrice)
                .receiverInfoId(receiverInfoId)
                .buyerId(buyerId)
                .insertTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return orderMainInfo;
    }

}
