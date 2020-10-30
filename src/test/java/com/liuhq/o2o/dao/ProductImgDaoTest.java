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

import com.liuhq.o2o.entity.ProductImg;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductImgDaoTest{
	
	@Autowired
	ProductImgDao productImgDao;
	
    @Test
    public void testABatchInsertProductImg() throws Exception {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(28L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片1");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(28L);

        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
    }
    
    @Test
    public void testDeleteProductImg()throws Exception  {
    	long productId=27L;
    	int effectedNum = productImgDao.deleteProductImgByProductId(productId);
    	assertEquals(1, effectedNum);
    }

}
