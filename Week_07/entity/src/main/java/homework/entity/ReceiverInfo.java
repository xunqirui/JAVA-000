package homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 收件人信息
 *
 * @author qrXun
 * @since 2020-12-01 21:53:46
 */
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverInfo implements Serializable {

    @Transient
    private static final long serialVersionUID = 370709274836983856L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
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
    private Integer postalcode;


}