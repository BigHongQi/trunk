package com.liuhq.o2o.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathUtil {
     private static String winPath;
     private static String linuxPath;
     private static String shopPath;
     

     @Value("${win.base.path}")
	 public void setWinPath(String winPath) {
		PathUtil.winPath = winPath;
	}
     @Value("${linux.base.path}")
	public void setLinuxPath(String linuxPath) {
		PathUtil.linuxPath = linuxPath;
	}
     @Value("${shop.relevant.path}")
	public void setShopPath(String shopPath) {
		PathUtil.shopPath = shopPath;
	}

	// 获取系统分隔符
    private static String separator = System.getProperty("file.separator");
    
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			//basePath = "D:/projectdev/image";
			basePath = winPath;
		} else {
		//	basePath = "/home/hongqipro";
			basePath = linuxPath;
		}
		basePath = basePath.replace("/", separator);
		return basePath;
	}
	
	 public static String getShopImagePath(long shopId) {
	      //  String imagePath = "/upload/item/shop" + shopId + "/";
	        String imagePath = shopPath + shopId + "/";
	        return imagePath.replace("/", separator);
	    }
}
