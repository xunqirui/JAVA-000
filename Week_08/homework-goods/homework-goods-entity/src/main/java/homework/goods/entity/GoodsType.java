package homework.goods.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * (GoodsType)实体类
 *
 * @author makejava
 * @since 2020-12-09 22:47:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goods_type")
public class GoodsType implements Serializable {
    private static final long serialVersionUID = -48505388225443277L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "jdbc"
    )
    private Integer id;
    /**
     * 类别ID
     */
    private Integer categoryId;
    /**
     * 类别名称
     */
    private String name;

}