package com.ect.domain;

import java.sql.Date;

/**
 * 订单
 */
public class DealDomain {

    private int id;

    private String userId;

    private String productId;

    private int buyNum;

    private Date createTime;

    private Date updateTIme;

    public DealDomain(){
    }

    public DealDomain(String userId, String productId) {
        this.userId = userId;
        this.productId = productId;
        buyNum = 1;
    }

    public DealDomain(String userId, String productId, int buyNum) {
        this.userId = userId;
        this.productId = productId;
        this.buyNum = buyNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTIme() {
        return updateTIme;
    }

    public void setUpdateTIme(Date updateTIme) {
        this.updateTIme = updateTIme;
    }
}
