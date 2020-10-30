package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.liuhq.o2o.entity.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest{
	
	@Autowired
	ProductCategoryDao productCategoryDao;
	
	 @Test
	 public void testQueryByShopId() throws Exception{
	        Long shopId = 20L;
	        List<ProductCategory> productCategorieList = productCategoryDao.queryProductCategoryList(shopId);
	        System.out.println("该店铺自定义类别数为：" + productCategorieList.size());
	    }
	 
	    @Test
	    public void testBatchInsertProductCategory() {
	        ProductCategory productCategory = new ProductCategory();
	        productCategory.setProductCategoryName("商铺类别1");
	        productCategory.setPriority(20);
	        productCategory.setCreateTime(new Date());
	        productCategory.setShopId(16L);

	        ProductCategory productCategory2 = new ProductCategory();
	        productCategory2.setProductCategoryName("商铺类别2");
	        productCategory2.setPriority(10);
	        productCategory2.setCreateTime(new Date());
	        productCategory2.setShopId(16L);

	        List<ProductCategory> productCategoryList = new ArrayList<>();
	        productCategoryList.add(productCategory);
	        productCategoryList.add(productCategory2);

	        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
	        assertEquals(2, effectedNum);
	    }
	    
      @Test
      public void testDeleteProductCategory() {
    	  int effectedNum = productCategoryDao.deleteProductCategory(20, 20);
    	  assertEquals(1, effectedNum);
      }
}
