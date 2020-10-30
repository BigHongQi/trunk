package com.liuhq.o2o.dto;

import java.util.List;

import com.liuhq.o2o.entity.Shop;
import com.liuhq.o2o.enums.ShopStateEnum;

public class ShopExecution {
	 // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;
    
    // 店铺数量
    private int count;

    // 返回的shop列表
    private List<Shop> shopList;

    // 操作的shop(增删改查的时候用到)
    private Shop shop;
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    // 无参构造器
    public ShopExecution() {

    }

    // 操作失败有参构造器
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的有参构造器
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    // 成功的有参构造器2
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }
}


