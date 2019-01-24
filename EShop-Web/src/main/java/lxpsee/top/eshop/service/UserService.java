package lxpsee.top.eshop.service;

import lxpsee.top.eshop.domain.User;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/20 17:27.
 */
public interface UserService extends BaseService<User> {
    boolean isEmailRegisted(String email);
}
