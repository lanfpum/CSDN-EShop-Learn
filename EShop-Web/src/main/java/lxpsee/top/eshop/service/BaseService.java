package lxpsee.top.eshop.service;

import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/19 19:05.
 * <p>
 * BaseService接口
 * <p>
 * crud 按照hql查找 执行hql
 */
public interface BaseService<T> {
    public void saveEntity(T t);

    public void updateEntity(T t);

    public void saveOrUpdateEntity(T t);

    public void deleteEntity(T t);

    public T getEntity(Integer id);

    public List<T> findByHQL(String hql, Object... objects);

    public void execHQL(String hql, Object... objects);

    public List<T> findAllEntities();
}
