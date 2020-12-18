package homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (FreezeMoney)实体类
 *
 * @author makejava
 * @since 2020-12-17 16:37:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "freeze_money")
public class FreezeMoney implements Serializable {
    private static final long serialVersionUID = -32981804637114427L;

    @Id
    @GeneratedValue(
            generator = "jdbc",
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    /**
     * 冻结金额
     */
    private BigDecimal money;
    /**
     * 金额类型
     * 0 - 美元
     * 1 - 人民币
     */
    private Integer moneySource;
    /**
     * 账户 id
     */
    private String accountId;

}