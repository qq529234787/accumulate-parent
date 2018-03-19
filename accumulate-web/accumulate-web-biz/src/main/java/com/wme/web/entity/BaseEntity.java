package com.wme.web.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ming on 2017/3/12.
 * 基础实体类
 */
public abstract class BaseEntity implements Serializable {

    /**
     * 自增id(数据库自增)
     */
    private Long id;

    /**
     * 状态(正常,不可用,删除)
     */
    private Integer status;

    /**
     * 创建时间(数据库设置默认值自动生成)
     */
    private Date created;

    /**
     * 修改时间(数据库设置默认值并设置自动变更)
     */
    private Date modified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
