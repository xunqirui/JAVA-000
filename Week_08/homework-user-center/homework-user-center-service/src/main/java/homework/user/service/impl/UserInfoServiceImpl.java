package homework.user.service.impl;

import homework.user.entity.UserInfo;
import homework.user.repository.UserInfoRepository;
import homework.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserInfoServiceImpl
 *
 * @author qrXun on 2020/12/9
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public Iterable<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }
}
