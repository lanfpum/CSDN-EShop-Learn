package lxpsee.top.eshop.service.impl;

import lxpsee.top.eshop.dao.BaseDao;
import lxpsee.top.eshop.service.BaseService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/19 19:06.
 * <p>
 * BaseService实现类.(抽象)
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    private BaseDao<T> dao;
    private Class<T>   clazz;

    public BaseServiceImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    public BaseDao<T> getDao() {
        return dao;
    }

    public void setDao(BaseDao<T> dao) {
        this.dao = dao;
    }

    public void saveEntity(T t) {
        dao.saveEntity(t);
    }

    public void updateEntity(T t) {
        dao.updateEntity(t);
    }

    public void saveOrUpdateEntity(T t) {
        dao.saveOrUpdateEntity(t);
    }

    public void deleteEntity(T t) {
        dao.deleteEntity(t);
    }

    public T getEntity(Integer id) {
        return dao.getEntity(id);
    }

    public List<T> findByHQL(String hql, Object... objects) {
        return dao.findByHQL(hql, objects);
    }

    public void execHQL(String hql, Object... objects) {
        dao.execHQL(hql, objects);
    }

    /**
     * 查询所有实体
     */
    public List<T> findAllEntities() {
        String hql = "from " + clazz.getSimpleName();
        return dao.findByHQL(hql);
    }
}
