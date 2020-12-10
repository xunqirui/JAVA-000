package homework.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * (UserInfo)实体类
 *
 * @author makejava
 * @since 2020-12-09 19:33:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -31099423367921733L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "jdbc"
    )
    private Long id;
    /**
     * 用户唯一ID
     */
    private Long uniqueId;
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
    private String birthday;

}