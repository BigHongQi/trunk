package com.liuhq.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liuhq.o2o.dao.ProductDao;
import com.liuhq.o2o.dao.ProductImgDao;
import com.liuhq.o2o.dto.ImageHolder;
import com.liuhq.o2o.dto.ProductExecution;
import com.liuhq.o2o.entity.Product;
import com.liuhq.o2o.entity.ProductImg;
import com.liuhq.o2o.enums.ProductStateEnum;
import com.liuhq.o2o.exceptiopns.ProductOperationException;
import com.liuhq.o2o.service.ProductService;
import com.liuhq.o2o.utils.ImageUtil;
import com.liuhq.o2o.utils.PageCalculator;
import com.liuhq.o2o.utils.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImgDao productImgDao;
	
	@Transactional
	@Override
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList)
			throws ProductOperationException {
		// TODO Auto-generated method stub
		// 空值判断
        if (product !=null && product.getShop().getShopId() != null) {
            // 给商品设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // 默认为上架状态
            product.setEnableStatus(1);
            // 若商品缩略图不为空则添加
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }

            try {
                // 创建商品信息
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败" + e.getMessage());
            }
            // 若商品详情图不为空则添加
            if (imageHolderList != null && imageHolderList.size() > 0) {
                addProductImgList(product, imageHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            // 传入的参数为空
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
	}
	/**
     * 批量添加图
     * @param product
     * @param imageHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> imageHolderList) {
        // 获取图片储存的路径，这里直接放在相应的店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        // 遍历图片一次去处理，并添加进productImg实体类
        for (ImageHolder imageHolder : imageHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }

        // 如果确实是有图片需要添加的 就执行批量添加
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败" + e.getMessage());

            }
        }
    }
	 /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        // 获取根路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
    
	@Override
	public Product getProductById(long productId) {
		// TODO Auto-generated method stub
		return productDao.queryProductById(productId);
	}
	
	@Transactional
	@Override
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList)
			throws ProductOperationException {
		// TODO Auto-generated method stub
		// 空值判断
        if (product !=null && product.getShop().getShopId() != null && product.getShop() != null) {
            // 给商品设置默认属性
            product.setLastEditTime(new Date());
            // 若商品缩略图不为空则添加
            if (thumbnail != null) {
            	Product tempProduct = productDao.queryProductById(product.getProductId());
            	if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
                addThumbnail(product, thumbnail);
            }
            if (imageHolderList != null && imageHolderList.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, imageHolderList);
			}
     
            try {
                // 创建商品信息
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("更新商品信息失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败" + e.getMessage());
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            // 传入的参数为空
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
	}
	/**
	 * 删除商品下详情图片
	 * @param productId
	 */
	private void deleteProductImgList(long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productId);
	}
	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize)
			throws ProductOperationException {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

}
