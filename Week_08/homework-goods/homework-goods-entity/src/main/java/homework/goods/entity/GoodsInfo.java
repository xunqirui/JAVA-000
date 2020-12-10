package homework.goods.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * (GoodsInfo)实体类
 *
 * @author makejava
 * @since 2020-12-09 22:47:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "goods_info")
public class GoodsInfo implements Serializable {
    private static final long serialVersionUID = -93745195927479240L;

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
    private Double discountPrice;
    /**
     * 原始价
     */
    private Double normalPrice;
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