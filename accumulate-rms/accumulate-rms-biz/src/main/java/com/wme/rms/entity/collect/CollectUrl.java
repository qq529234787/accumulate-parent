package com.wme.rms.entity.collect;

import java.util.Date;

/**
 * Created by ming on 2016/3/27.
 */
public class CollectUrl implements java.io.Serializable {

    private Long collectId;
    private String loginName;
    private String url;
    private String param;
    private String paramJson;
    private String localAddr;
    private String remoteAddr;
    private Date created;

    /**------------------------------- 分页 -------------------------------*/
    private int pageNo;

    private int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /**------------------------------- 分页 -------------------------------*/

    public Long getCollectId() {
        return collectId;
    }

    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public String getLocalAddr() {
        return localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "CollectUrl{" +
                "collectId=" + collectId +
                ", loginName='" + loginName + '\'' +
                ", url='" + url + '\'' +
                ", param='" + param + '\'' +
                ", paramJson='" + paramJson + '\'' +
                ", localAddr='" + localAddr + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", created=" + created +
                '}';
    }
}
