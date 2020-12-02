package homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品类别
 *
 * @author qrXun
 * @since 2020-12-01 21:53:44
 */
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsType implements Serializable {

    @Transient
    private static final long serialVersionUID = -69250608251853421L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "jdbc"
    )
    private Long id;
    /**
     * 类别ID
     */
    private Integer categoryId;
    /**
     * 类别名称
     */
    private String name;

}