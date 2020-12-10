package homework.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * (ReceiverInfo)实体类
 *
 * @author makejava
 * @since 2020-12-09 19:33:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "receiver_info")
public class ReceiverInfo implements Serializable {
    private static final long serialVersionUID = -99065734225122565L;

    @Id
    @GeneratedValue(
            strategy = IDENTITY,
            generator = "jdbc"
    )
    private Long id;
    /**
     * 收件人
     */
    private String name;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 收件地址
     */
    private String address;
    /**
     * 邮政编码
     */
    private Long postalcode;

}