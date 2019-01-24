package lxpsee.top.eshop.service.impl;

import lxpsee.top.eshop.domain.User;
import lxpsee.top.eshop.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/23 20:39.
 */
public class UserServiceImplTest {

    @Test
    public void testService() throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        UserService service = (UserService) applicationContext.getBean("userService");
        User user = new User();
        user.setName("tom");
        user.setPassword("123");
        service.saveEntity(user);

    }
}