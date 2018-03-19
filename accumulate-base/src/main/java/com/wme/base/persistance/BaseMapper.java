package com.wme.base.persistance;

import com.wme.base.utils.PageQuery;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingne on 2015/8/18.
 */
public interface BaseMapper<E extends Serializable> {

    Date getCurrentTime();

    E get(Long id);

    int insert(E e);

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



    // -------------test--------------------
    /**
     * 按条件查询列表
     * @param params
     * @return
     */
    List<E> list(PageQuery params);
    /**
     * 按条件查询记录数
     * @param params
     * @return
     */
    int count(PageQuery params);

    /**
     * 按条件分布查询列表
     * @param params
     * @return
     */
    List<E> pageList(PageQuery params);

}
