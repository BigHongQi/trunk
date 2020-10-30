package com.liuhq.o2o.dto;

public class WechatInfo {
 private long customerId;
 private long productId;
 private long userAwardId;
 private long createTime;
 private long shopId;
public long getCustomerId() {
	return customerId;
}
public void setCustomerId(long customerId) {
	this.customerId = customerId;
}
public long getProductId() {
	return productId;
}
public void setProductId(long productId) {
	this.productId = productId;
}
public long getUserAwardId() {
	return userAwardId;
}
public void setUserAwardId(long userAwardId) {
	this.userAwardId = userAwardId;
}
public long getCreateTime() {
	return createTime;
}
public void setCreateTime(long createTime) {
	this.createTime = createTime;
}
public long getShopId() {
	return shopId;
}
public void setShopId(long shopId) {
	this.shopId = shopId;
}
@Override
public String toString() {
	return "WechatInfo [customerId=" + customerId + ", productId=" + productId + ", userAwardId=" + userAwardId
			+ ", createTime=" + createTime + ", shopId=" + shopId + "]";
}
 
 
}
