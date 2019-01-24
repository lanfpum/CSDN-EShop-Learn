package lxpsee.top.eshop.service.impl;

import lxpsee.top.eshop.dao.BaseDao;
import lxpsee.top.eshop.domain.User;
import lxpsee.top.eshop.service.UserService;
import lxpsee.top.eshop.utils.ValidateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/20 17:27.
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    /**
     * 重新该方法，需要注入指定的UserDao对象
     */
    @Resource(name = "userDao")
    @Override
    public void setDao(BaseDao<User> dao) {
        super.setDao(dao);
    }

    public boolean isEmailRegisted(String email) {
        String hql = "from User u where u.email = ?";
        List<User> userList = this.findByHQL(hql, email);
        return ValidateUtil.isValid(userList);
    }
}
