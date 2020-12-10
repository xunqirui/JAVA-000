package homework.user.service;

import homework.user.entity.UserInfo;

/**
 * UserInfoService
 *
 * @author qrXun on 2020/12/9
 */
public interface UserInfoService {

    Iterable<UserInfo> findAll();

}
