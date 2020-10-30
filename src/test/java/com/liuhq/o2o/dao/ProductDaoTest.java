package com.liuhq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.entity.Product;
import com.liuhq.o2o.entity.ProductCategory;
import com.liuhq.o2o.entity.Shop;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoTest{
	
	@Autowired
	ProductDao productDao;
	
	  @Test
	  public void testAInsertProduct() throws Exception {
	        Shop shop1 = new Shop();
	        shop1.setShopId(16L);
	        ProductCategory pc1 = new ProductCategory();
	        pc1.setProductCategoryId(10L);
	        Product product1 = new Product();
	        product1.setProductName("测试1");
	        product1.setProductDesc("测试Desc1");
	        product1.setImgAddr("test1");
	        product1.setPriority(0);
	        product1.setEnableStatus(1);
	        product1.setCreateTime(new Date());
	        product1.setLastEditTime(new Date());
	        product1.setShop(shop1);
	        product1.setProductCategory(pc1);
	        int effectedNum = productDao.insertProduct(product1);
	        assertEquals(1, effectedNum);
	    }
	  
	  @Test
	  public void testGetProductById() throws Exception{
		  long productId=28L;
		  Product effectedNum = productDao.queryProductById(productId);
		  assertEquals(3, effectedNum.getProductImgList().size());
	  }
	  
	  @Test 
	  public void testUpdateProduct()throws Exception{
		  Product product = new Product();
		  ProductCategory productCategory = new ProductCategory();
		  Shop shop =  new Shop();
		  shop.setShopId(16L);
		  productCategory.setProductCategoryId(10L);
		  product.setProductId(28L);
		  product.setShop(shop);
		  product.setProductCategory(productCategory);
		  product.setProductName("小红旗");
		  int effectedNum = productDao.updateProduct(product);
		  assertEquals(1, effectedNum);
	  }
	  
		@Test
		public void testBQueryProductList() throws Exception {
			Product product = new Product();
			List<Product> productList = productDao.queryProductList(product, 0, 20);
			assertEquals(15, productList.size());
			int count = productDao.queryProductCount(product);
			assertEquals(15, count);
			product.setProductName("测试");
			productList = productDao.queryProductList(product, 0, 3);
			assertEquals(1, productList.size());
			count = productDao.queryProductCount(product);
			assertEquals(1, count);
			Shop shop = new Shop();
			shop.setShopId(16L);
			product.setShop(shop);
			productList = productDao.queryProductList(product, 0, 3);
			assertEquals(1, productList.size());
			count = productDao.queryProductCount(product);
			assertEquals(1, count);
		}
        @Test
        public void testupdateProductCategoryToNull() {
        	int count = productDao.updateProductCategoryToNull(10L);
			assertEquals(1, count);
        }
        
    	@Test
		public void testQueryProductList() throws Exception {
			Product product = new Product();
			List<Product> productList = productDao.queryProductList(product, 0, 20);
			assertEquals(15, productList.size());
			ProductCategory pro=new ProductCategory();
			pro.setProductCategoryId(11L);
			Shop shop = new Shop();
			shop.setShopId(20L);
			product.setShop(shop);
			product.setProductCategory(pro);
			productList = productDao.queryProductList(product, 0, 10);
			System.out.println(productList);
		}
	   

}
