package com.wme.base.manager;

import com.wme.base.persistance.BaseMapper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ming on 2016/3/28.
 */
public interface BaseManager<T extends BaseMapper<E>,E extends Serializable> {

    Date getCurrentTime();

    E get(Long id);

    int save(E e);

    int update(E e);

    int delete(Long id);

    /**
     * 按条件查询列表
     * @param params
     * @return
     */
    List<E> list(Map<String,Object> params);

    /**
     * 按条件查询记录数
     * @param params
     * @return
     */
    int count(Map<String,Object> params);

    /**
     * 按条件分布查询列表
     * @param params
     * @return
     */
    List<E> pageList(Map<String,Object> params);
}
