package com.liuhq.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import com.liuhq.o2o.dto.ImageHolder;
import com.liuhq.o2o.dto.ShopExecution;
import com.liuhq.o2o.entity.Area;
import com.liuhq.o2o.entity.PersonInfo;
import com.liuhq.o2o.entity.Shop;
import com.liuhq.o2o.entity.ShopCategory;
import com.liuhq.o2o.exceptiopns.ShopOperationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest{
	
	@Autowired
	ShopService shopService;
	 @Test
	 public void testGetShopList(){
		    Shop shopCondition = new Shop();
		    shopCondition.setShopCategoryId(14L);
	        ShopExecution shopExecution = shopService.getShopList(shopCondition, 1, 5);
	        System.out.println("店铺列表数" + shopExecution.getShopList().size());
	        System.out.println("店铺总数" + shopExecution.getCount());    
	 }
	@Test
	public void testAddShop() {
		Shop shop = new Shop();
		shop.setOwnerId(8L);
		Area area = new Area();
		area.setAreaId(3L);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(10L);
		shop.setShopName("mytest3");
		shop.setShopDesc("mytest3");
		shop.setShopAddr("testaddr1");
		shop.setPhone("18137309743");
		shop.setLongitude(1D);
		shop.setLatitude(1D);
		shop.setAdvice("审核中");
		shop.setArea(area);
		shop.setShopCategory(sc);
		File shopImg=new File("/78.jpg");
		InputStream is;
		try {
			is = new FileInputStream(shopImg);
			ImageHolder thumbnail = new ImageHolder(shopImg.getName(), is);
			ShopExecution effectedNum = shopService.addShop(shop,thumbnail);
			assertEquals(0, effectedNum.getState());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	  @Test
	    public void testModifyShop() throws ShopOperationException, FileNotFoundException {
	        Shop shop  = new Shop();
	        shop.setShopId(41L);
	        shop.setShopName("修改后的店铺名称");
	        File shopImg = new File("D:\\56.jpg");
	        InputStream inputStream = new FileInputStream(shopImg);
	        ImageHolder thumbnail = new ImageHolder("\56.jpg", inputStream);
	        ShopExecution shopExecution = shopService.modifyShop(shop, thumbnail);
	        System.out.println("新的图片地址：" + shopExecution.getShop().getShopImg());

	    }

}
