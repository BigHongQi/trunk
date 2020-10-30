package com.liuhq.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuhq.o2o.dao.ProductCategoryDao;
import com.liuhq.o2o.dao.ProductDao;
import com.liuhq.o2o.dto.ProductCategoryExecution;
import com.liuhq.o2o.entity.ProductCategory;
import com.liuhq.o2o.enums.ProductCategoryStateEnum;
import com.liuhq.o2o.exceptiopns.ProductCategoryOperationException;
import com.liuhq.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	 @Autowired
	 private ProductCategoryDao productCategoryDao;
	 
	 @Autowired
	 private ProductDao productDao;
	 
	@Override
	public List<ProductCategory> getProductCategroyList(Long shopId) {
		// TODO Auto-generated method stub
		 List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
	     return productCategoryList;
	}
	
	
    @Transactional
	@Override
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		// TODO Auto-generated method stub
    	   if (productCategoryList != null && productCategoryList.size() > 0) {
               try {
                   int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                   if (effectedNum <= 0) {
                       throw new ProductCategoryOperationException("店铺创建失败");
                   } else {
                       return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList);
                   }
               } catch (Exception e) {
                   throw new ProductCategoryOperationException("batchAddProductCategory error" + e.getMessage());
               }
           } else {
               return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
           }
	}

    @Transactional
	@Override
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
        
    	try {
            int effectNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectNum < 0) {
                throw new ProductCategoryOperationException("店铺类别删除失败");
            } 
        } catch (Exception e) {
            throw new ProductCategoryOperationException("店铺类别删除失败" + e.getMessage());
        }
    	// TODO 将此商品类别下的商品的类别Id置为空
    	try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("店铺类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
        }
		
	}

}
