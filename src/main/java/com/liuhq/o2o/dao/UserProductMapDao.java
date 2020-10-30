package com.liuhq.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.liuhq.o2o.entity.UserProductMap;

public interface UserProductMapDao {
   
	/**
	 * 根据传入进来的查询条件分页返回用户购买商品的记录信息
	 * @param userProductCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserProductMap> queryUserProductMapList(@Param("userProductCondition")UserProductMap userProductCondition ,@Param("rowIndex")int rowIndex ,@Param("pageSize")int pageSize);
    
	/**
	 * 配置queryUserProductMapList返回相同查询条件返回用户购买商品的记录总数
	 * @param userAwardCondition
	 * @return
	 */
	int queryUserProductMapCount(@Param("userProductCondition")UserProductMap userProductCondition);
	
	/**
	 * 添加一条用户购买商品的记录
	 * @param userAwardMap
	 * @return
	 */
	int insertUserProductMap(UserProductMap userAwardMap);
}
