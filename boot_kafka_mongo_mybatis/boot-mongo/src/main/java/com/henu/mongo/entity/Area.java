package com.henu.mongo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *@author CoderYe
 *@version 2019年12月7日 下午9:06:59
 */
public class Area implements Serializable {

    private Integer areaId;

    private  String areaName;

    private Integer priority;

    private Date createTime;

    private  Date lastEditTime;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "Area [areaId=" + areaId + ", areaName=" + areaName + ", priority=" + priority + ", createTime="
                + createTime + ", lastEditTime=" + lastEditTime + "]";
    }

}

