package homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账户(Account)实体类
 *
 * @author makejava
 * @since 2020-12-17 16:37:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account implements Serializable {
    private static final long serialVersionUID = -33019673305633405L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "jdbc"
    )
    private Long id;
    /**
     * 账户id
     */
    private String accountId;
    /**
     * 存款金额
     */
    private BigDecimal money;
    /**
     * 金额类型
     * 0 - 美元
     * 1 - 人命币
     */
    private Integer moneySource;

}