package com.liuhq.o2o.entity;

import java.util.Date;

//顾客在该店铺的积分映射
public class UserShopMap {
	private Long userShopId;
	private Long userId;
	private Long shopId;
	private String userName;
	private String shopName;
	private Date createTime;
	private Integer point;
	private PersonInfo user;
	private Shop shop;

	public Long getUserShopId() {
		return userShopId;
	}

	public void setUserShopId(Long userShopId) {
		this.userShopId = userShopId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public PersonInfo getUser() {
		return user;
	}

	public void setUser(PersonInfo user) {
		this.user = user;
	}


	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public String toString() {
		return "UserShopMap [userShopId=" + userShopId + ", userId=" + userId + ", shopId=" + shopId + ", userName="
				+ userName + ", shopName=" + shopName + ", createTime=" + createTime + ", point=" + point + ", user="
				+ user + ", shop=" + shop + "]";
	}
    
}
