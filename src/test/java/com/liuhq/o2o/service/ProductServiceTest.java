package com.liuhq.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.liuhq.o2o.dto.ImageHolder;
import com.liuhq.o2o.dto.ProductExecution;
import com.liuhq.o2o.entity.Product;
import com.liuhq.o2o.entity.ProductCategory;
import com.liuhq.o2o.entity.Shop;
import com.liuhq.o2o.enums.ProductStateEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest{

	@Autowired
	ProductService productService;
	
    @Test
    public void testaddProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(16L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(10L);

        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试");
        product.setPriority(0);
        product.setEnableStatus(0);
        // 创建缩略图文件流
        File thumbnailFile = new File("D:\\1234.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);

        // 创建详情图片列表
        File thumbnailFile2 = new File("D:\\56.jpg");
        InputStream thumbnail2 = new FileInputStream(thumbnailFile2);
        ImageHolder imageHolder2 = new ImageHolder(thumbnailFile2.getName(), thumbnail2);

        List<ImageHolder> list = new ArrayList<>();
       
        list.add(imageHolder2);

        // 添加商品并验证
        ProductExecution productExecution = productService.addProduct(product, thumbnail, list);
        assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
    }
    
    @Test
    public void testUpdateProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(16L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(10L);

        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("正式商品1");
        product.setProductDesc("正式");
        product.setPriority(0);
        product.setEnableStatus(0);
        product.setProductId(29L);
        // 创建缩略图文件流
        File thumbnailFile = new File("D:\\1234.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);

        // 创建详情图片列表
        File thumbnailFile2 = new File("D:\\56.jpg");
        InputStream thumbnail2 = new FileInputStream(thumbnailFile2);
        ImageHolder imageHolder2 = new ImageHolder(thumbnailFile2.getName(), thumbnail2);

        List<ImageHolder> list = new ArrayList<>();
       
        list.add(imageHolder2);

        // 添加商品并验证
        ProductExecution productExecution = productService.modifyProduct(product, thumbnail, list);
        assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
    }
}
