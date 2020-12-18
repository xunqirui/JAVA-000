package homework.service;

import homework.api.UserService;
import homework.entity.User;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author qrXun on 2020/12/17
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
