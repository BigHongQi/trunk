package com.liuhq.o2o.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.liuhq.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtil {
	
	    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	    private static final SimpleDateFormat sDateFormate = new SimpleDateFormat("yyyyMMddHHmmss");
	    private static final Random random = new Random();
	    private static Logger logger=LoggerFactory.getLogger(ImageUtil.class);
	    
	    
	    /**
	     * 将CommonsMultipartFile转换为File
	     * @param cFile
	     * @return
	     */
	    public File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
	    	File newFile=new File(cFile.getOriginalFilename());
	    	try {
				cFile.transferTo(newFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				logger.debug(e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.toString());
				e.printStackTrace();
			}
	    	
			return newFile;
	    	
	    }
	 
	    
	    /**
	     * 创建缩略图
	     */
	    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
	        // 获取图片的随机图片名
	        String realFileName = getRandomFileName();
	        // 获取图片的拓展名
	        String extension = getFileExtension(thumbnail.getImageName());
	        //创建路径
	        makeDirPath(targetAddr);
	        String relativeAddr = targetAddr + realFileName + extension;
	        logger.debug("current relativeAddr is :"+relativeAddr);
	        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
	        logger.debug("current Complete Addr is :"+PathUtil.getImgBasePath() + relativeAddr);
	        try {
	            Thumbnails.of(thumbnail.getImage()).size(200, 200).outputQuality(0.8f).toFile(dest);
	        } catch (IOException e) {
	        	logger.error(e.toString());
	            throw new RuntimeException("创建缩略图失败：" + e.toString());
	        }
	        return relativeAddr;
	    }
	    /**
	     * 处理详情图
	     * @param thumbnail
	     * @param targetAddr
	     * @return
	     */
	    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
			//获取不重复的随机名
	    	String realFileName=getRandomFileName();
	    	 // 获取图片的拓展名
	        String extension = getFileExtension(thumbnail.getImageName());
			makeDirPath(targetAddr);
			String relativeAddr = targetAddr + realFileName + extension;
			logger.debug("current relativeAddr is :"+relativeAddr);
			File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
			logger.debug("current Complete Addr is :"+PathUtil.getImgBasePath() + relativeAddr);
			try {
				Thumbnails.of(thumbnail.getImage()).size(337, 640).outputQuality(0.5f).toFile(dest);
			} catch (IOException e) {
				throw new RuntimeException("创建图片失败：" + e.toString());
			}	
			return relativeAddr;
			}
		
          
	    /**
	     * 创建路径  /home/work/cuzz/xx.jpg
	     * 那么 home work xiangze 这三个文件都自动创建
	     * @param targetAddr
	     */
	    private static void makeDirPath(String targetAddr) {
	        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
	        File dirPath = new File(realFileParentPath);
	        // 如果路径不存在就递归的创建
	        if (!dirPath.exists()) {
	            dirPath.mkdirs();
	        }
	    }

	    /**
	     * 获取输入文件流的拓展名
	     * @param thumbnail
	     * @return
	     */
	    private static String getFileExtension(String fileName) {
	        // 获取原来的文件名
	        String originalFileName = fileName;
	        return originalFileName.substring(originalFileName.lastIndexOf("."));
	    }

	    /**
	     * 生成随机文件名，当前年月日时分秒 + 5位随机数
	     * @return
	     */
	    public static String getRandomFileName() {
	        // 获取随机的5位数
	        int rannum = random.nextInt(89999) + 10000;
	        String nowTimestr = sDateFormate.format(new Date());
	        return nowTimestr + rannum;
	    }
	    
	    /**
	     * storePath 是文件的路径还是目录的路径
	     * 如果storePath是文件的路径则删除该文件
	     * 如果storePath是目录的路径则删除工目录下的所有文件
	     * @param storePath
	     */
	    public static void deleteFileOrPath(String storePath) {
	       File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
	       if (fileOrPath.exists()) {
	           // 如果是目录
	           if (fileOrPath.isDirectory()) {
	               File file[] = fileOrPath.listFiles();
	               for (int i = 0; i < file.length; i++) {
	                   file[i].delete();
	               }
	           }
	           // 如果是文件
	           fileOrPath.delete();
	       }
	    }
}
