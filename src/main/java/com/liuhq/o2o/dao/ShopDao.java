package com.liuhq.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liuhq.o2o.entity.Shop;

public interface ShopDao {
	
	 /**
       * 查询总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
	 /**
     * 分页查询 可输入的条件有：店铺名(模糊). 店铺状态 区域id owner
     * @param shopCondition 查询的条件
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * @param
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
	/**
	 *   1：成功     -1：失败
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	/**
	 * 
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);

}
