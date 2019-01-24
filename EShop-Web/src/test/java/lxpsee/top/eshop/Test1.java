package lxpsee.top.eshop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/20 09:07.
 */
public class Test1 {
    @Test
    public void testConn() throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

    }
}
