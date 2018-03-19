package com.wme.base.service;

import com.wme.base.persistance.BaseMapper;
import com.wme.base.utils.PageQuery;
import com.wme.base.utils.PaginationSupport;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingne on 2015/8/18.
 */
public interface BaseService<T extends BaseMapper<E>,E extends Serializable> {
    
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
    List<E> list(Map params);

    /**
     * 按条件查询记录数
     * @param params
     * @return
     */
    int count(Map params);

    /**
     * 按条件分布查询列表
     * @param params
     * @return
     */
    PaginationSupport<E> pageList(Map params);

    /**
     * 按条件分布查询列表
     * @param params
     * @return
     */
    PaginationSupport<E> pageList(E params);

    int count(PageQuery params);

    PaginationSupport<E> pageList(PageQuery params);

    /**
     * 按条件查询列表
     * @param params
     * @return
     */
    List<E> list(PageQuery params);

    PaginationSupport<E> pageList(Object params);
}
