package homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户信息
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
public class UserInfo implements Serializable {

    @Transient
    private static final long serialVersionUID = 439147411174865821L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "jdbc"
    )
    private Long id;
    /**
     * 用户唯一ID
     */
    private String uniqueId;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 登录账号
     */
    private String account;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 创建时间
     */
    private String insertTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 出生年月
     */
    private Object birthday;


}