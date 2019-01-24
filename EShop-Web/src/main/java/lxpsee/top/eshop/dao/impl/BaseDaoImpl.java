package lxpsee.top.eshop.dao.impl;

import lxpsee.top.eshop.dao.BaseDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/19 18:55.
 * <p>
 * BaseDao接口的实现类(抽象)
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    private SessionFactory sessionFactory;      //hibernate会话工厂,相当于连接池，数据源。 set方法设置
    private Class<T>       clazz;   //接受T的具体类型

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 1.取得子类的泛型化超类，因为子类的dao都是继承该类，她的构造方法会先调用父类的空参构造
     * 2.得到第一个实际参数
     */
    public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    public void saveEntity(T t) {
        sessionFactory.getCurrentSession().save(t);
    }

    public void updateEntity(T t) {
        sessionFactory.getCurrentSession().update(t);
    }

    public void saveOrUpdateEntity(T t) {
        sessionFactory.getCurrentSession().saveOrUpdate(t);
    }

    public void deleteEntity(T t) {
        sessionFactory.getCurrentSession().delete(t);
    }

    /**
     * 按照id查询对象实体
     */
    public T getEntity(Integer id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    /**
     * 按照hql查询数据,绑定多个参数
     */
    public List<T> findByHQL(String hql, Object... objects) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        for (int i = 0; i < objects.length; i++) {
            query.setParameter(i, objects[i]);
        }
        return query.list();
    }

    /**
     * 按照hql进行批量写操作,绑定多个参数
     */
    public void execHQL(String hql, Object... objects) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        for (int i = 0; i < objects.length; i++) {
            query.setParameter(i, objects[i]);
        }
        query.executeUpdate();
    }

}
