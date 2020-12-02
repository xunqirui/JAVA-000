package homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情
 *
 * @author qrXun
 * @since 2020-12-01 21:53:43
 */
@Table
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo implements Serializable {

    @Transient
    private static final long serialVersionUID = 872635962903457327L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "jdbc"
    )
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品类别id
     */
    private Integer typeId;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 折后价
     */
    private BigDecimal discountPrice;
    /**
     * 原始价
     */
    private BigDecimal normalPrice;
    /**
     * 简介
     */
    private String description;
    /**
     * 重量
     */
    private Double weight;
    /**
     * 尺寸描述
     */
    private String sizeDescription;
    /**
     * 当前库存数量
     */
    private Integer stock;

}