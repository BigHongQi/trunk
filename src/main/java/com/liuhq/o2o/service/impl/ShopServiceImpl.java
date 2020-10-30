package com.liuhq.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuhq.o2o.dao.ShopAuthMapDao;
import com.liuhq.o2o.dao.ShopDao;
import com.liuhq.o2o.dto.ImageHolder;
import com.liuhq.o2o.dto.ShopAuthMapExecution;
import com.liuhq.o2o.dto.ShopExecution;
import com.liuhq.o2o.entity.Shop;
import com.liuhq.o2o.entity.ShopAuthMap;
import com.liuhq.o2o.enums.ShopStateEnum;
import com.liuhq.o2o.exceptiopns.ShopOperationException;
import com.liuhq.o2o.service.ShopService;
import com.liuhq.o2o.utils.ImageUtil;
import com.liuhq.o2o.utils.PageCalculator;
import com.liuhq.o2o.utils.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop,  ImageHolder thumbnail) {
		// TODO Auto-generated method stub
		
		if(shop==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}
		try {
			  shop.setEnableStatus(0);
			  shop.setLastEditTime(new Date());
			  shop.setCreateTime(new Date());
			  int effectedNum =shopDao.insertShop(shop);
			  if(effectedNum <= 0) {
				  throw new ShopOperationException("店铺创建失败");
			  }else {
				  //存储图片
				  if(thumbnail.getImage() != null) {
					  try {
						addShopImg(shop,thumbnail);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw new ShopOperationException("addShopImg error" + e.getMessage());
					}
					  //更新店铺的图片地址
					  effectedNum=shopDao.updateShop(shop);
					  if(effectedNum <= 0) {
						  throw new ShopOperationException("更新店铺图片失败");
					  }
					ShopAuthMap shopAuthMap = new ShopAuthMap();
					shopAuthMap.setEmployeeId(shop.getOwnerId());
					shopAuthMap.setShopId(shop.getShopId());
					shopAuthMap.setName("");
					shopAuthMap.setTitle("店家");
					shopAuthMap.setTitleFlag(0);
					shopAuthMap.setCreateTime(new Date());
					shopAuthMap.setLastEditTime(new Date());
					shopAuthMap.setEnableStatus(0);
					try {
					    int effectedNum1 = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
						if(effectedNum1 <= 0) {
							 throw new ShopOperationException("授权创建失败");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						 throw new ShopOperationException("insertShopAuthMaperror" + e.getMessage());
					}
				  }
				  
			  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 throw new ShopOperationException("addShop error" + e.getMessage());
			
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		//获取shop图片目录的相对值路径
		String dest=PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr=ImageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr);
		
	}
	
	@Override
	public Shop getShopById(long shopId) {
		// TODO Auto-generated method stub
		return shopDao.queryByShopId(shopId);
	}
	
	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop,  ImageHolder thumbnail) {
		// TODO Auto-generated method stub
		if(shop == null || shop.getShopId() == null) {
			 return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}
		try {
			if(thumbnail != null && !("").equals(thumbnail.getImageName())) {
				Shop tempShop=shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg() != null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, thumbnail); 
			}
			shop.setLastEditTime(new Date());
			int effectNum = shopDao.updateShop(shop);
	        if (effectNum <= 0) {
	        	 return new ShopExecution(ShopStateEnum.INNER_ERROR);
	        } else {
	        	 shop = shopDao.queryByShopId(shop.getShopId());
	             return new ShopExecution(ShopStateEnum.SUCCESS, shop);
	        }   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 throw new ShopOperationException("modifyShop error" + e.getMessage());
		}
	
	}
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList= shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		 ShopExecution shopExecution = new ShopExecution();
	        if (shopList != null) {
	            shopExecution.setShopList(shopList);
	            shopExecution.setCount(count);
	        } else {
	            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
	        }
	        return shopExecution;
	}

}
