package com.wme.base.service.impl;

import com.wme.base.persistance.BaseMapper;
import com.wme.base.service.BaseService;
import com.wme.base.utils.BeanUtil;
import com.wme.base.utils.PageQuery;
import com.wme.base.utils.PaginationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingne on 2015/8/18.
 */
public class BaseServiceImpl<T extends BaseMapper<E>, E extends Serializable> implements BaseService<T, E> {

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
    public int insert(E e) {
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
    public PaginationSupport<E> pageList(Map params) {
        if (params.get("pageSize") == null || params.get("pageNo") == null) {
            throw new RuntimeException("You must specify pageSize and pageNo in the params when executing pageList method");
        }
        int pageSize = (Integer) params.get("pageSize");
        int pageNo = (Integer) params.get("pageNo");
        if (pageNo < 1) pageNo = 1;
        //for oracle
        int startRow = (pageNo - 1) * pageSize;
        int endRow = startRow + pageSize;
        params.put("startRow", startRow);
        params.put("endRow", endRow);
        //for mysql
        params.put("startIndex", startRow);

        List<E> list = mapper.pageList(params);
        int total = count(params);
        if (logger.isInfoEnabled()) {
            logger.info("Get totalCount is {} when executing pageList,pageSize is {},pageNo is {}", total, pageSize, pageNo);
        }
        return new PaginationSupport<>(list, total, pageSize, startRow);
    }

    @Override
    public PaginationSupport<E> pageList(E params) {
        return pageList(BeanUtil.bean2map(params));
    }


    @Override
    public int count(PageQuery params) {
        return mapper.count(params);
    }

    @Override
    public PaginationSupport<E> pageList(PageQuery params) {
        if (params.getPageSize() <= 0 || params.getPageNo() <= 0) {
            throw new RuntimeException("You must specify pageSize and pageNo in the params when executing pageList method");
        }

        int total = count(params);
        List<E> list = null;
        if(total>0){
            list = mapper.pageList(params);

        }
        return new PaginationSupport<>(list, total, params.getPageSize(), params.getStartRow());
    }

    @Override
    public List<E> list(PageQuery params) {
        return mapper.list(params);
    }

    @Override
    public PaginationSupport<E> pageList(Object params) {
        return pageList(BeanUtil.bean2map(params));
    }

}
