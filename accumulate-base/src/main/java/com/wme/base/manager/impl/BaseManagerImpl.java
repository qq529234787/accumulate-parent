package com.wme.base.manager.impl;

import com.wme.base.manager.BaseManager;
import com.wme.base.persistance.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ming on 2016/3/28.
 */
public class BaseManagerImpl <T extends BaseMapper<E>, E extends Serializable> implements BaseManager<T, E> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected T mapper;

    @Override
    public Date getCurrentTime() {
        return mapper.getCurrentTime();
    }

    @Override
    public E get(Long id) {
        return mapper.get(id);
    }

    @Override
    public int save(E e) {
        return mapper.insert(e);
    }

    @Override
    public int update(E e) {
        return mapper.update(e);
    }

    @Override
    public int delete(Long id) {
        return mapper.delete(id);
    }

    @Override
    public List<E> list(Map params) {
        return mapper.list(params);
    }

    @Override
    public int count(Map params) {
        return mapper.count(params);
    }

    @Override
    public List<E> pageList(Map<String, Object> params) {
        return mapper.pageList(params);
    }

}
