package homework.api;

import homework.annotation.Post;
import homework.entity.User;

/**
 * UserService
 *
 * @author qrXun on 2020/12/16
 */
public interface UserService {

    @Post(url = "http://localhost:8080/")
    User findById(int id);

}
