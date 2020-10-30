package com.liuhq.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuhq.o2o.dto.ImageHolder;
import com.liuhq.o2o.dto.ShopExecution;
import com.liuhq.o2o.entity.Area;
import com.liuhq.o2o.entity.PersonInfo;
import com.liuhq.o2o.entity.Shop;
import com.liuhq.o2o.entity.ShopCategory;
import com.liuhq.o2o.enums.ShopStateEnum;
import com.liuhq.o2o.service.AreaService;
import com.liuhq.o2o.service.ShopCategoryService;
import com.liuhq.o2o.service.ShopService;
import com.liuhq.o2o.utils.CodeUtil;
import com.liuhq.o2o.utils.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Autowired
	private AreaService areaService;
	
    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        // 如果前端没有传shopId来
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            // 如果还是没有获取shop
            if (currentShopObj == null) {
                // 重定向
                modelMap.put("redirect", true);
                modelMap.put("url","/o2o/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            Shop shop = shopService.getShopById(shopId);
            currentShop.setOwnerId(shop.getOwnerId());
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
    
    
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
//        user.setUserId(8L);
//        user.setName("liuhq");
//        request.getSession().setAttribute("user", user);
        user = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwnerId(user.getUserId());
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", se.getShopList());
            request.getSession().setAttribute("shopList", se.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
	
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getShopById(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");

        }
        return modelMap;
    }
	
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
	
		return modelMap;
	}
	
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接收并转化为相应的参数，包括店铺信息和图片信息
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
		} 
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//修改店铺
		if (shop != null && shop.getShopId() != null) {
			try {
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
//				PersonInfo user=new PersonInfo();
//				user.setUserId(8L);
				shop.setOwnerId(user.getUserId());
				ShopExecution se = null;
				try {
					if(shopImg == null) {
						se = shopService.modifyShop(shop, null);
					}else {
						ImageHolder thumbnail = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
						se = shopService.modifyShop(shop, thumbnail);
					}
					if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
						modelMap.put("success", true);
						// 若shop创建成功，则加入session中，作为权限使用
//						@SuppressWarnings("unchecked")
//						List<Shop> shopList = (List<Shop>) request.getSession()
//								.getAttribute("shopList");
//						if (shopList != null && shopList.size() > 0) {
//							shopList.add(se.getShop());
//							request.getSession().setAttribute("shopList", shopList);
//						} else {
//							shopList = new ArrayList<Shop>();
//							shopList.add(se.getShop());
//							request.getSession().setAttribute("shopList", shopList);
//						}
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", se.getStateInfo());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接收并转化为相应的参数，包括店铺信息和图片信息
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//注册店铺
		if (shop != null && shopImg != null) {
			try {
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
//				PersonInfo user=new PersonInfo();
//				user.setUserId(8L);
				shop.setOwnerId(user.getUserId());
				ShopExecution se = null;
				try {
					ImageHolder thumbnail = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.addShop(shop, thumbnail);
					if (se.getState() == ShopStateEnum.CHECK.getState()) {
						modelMap.put("success", true);
						// 若shop创建成功，则加入session中，作为权限使用
						@SuppressWarnings("unchecked")
						List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
						if (shopList != null && shopList.size() > 0) {
							shopList.add(se.getShop());
							request.getSession().setAttribute("shopList", shopList);
						} else {
							shopList = new ArrayList<Shop>();
							shopList.add(se.getShop());
							request.getSession().setAttribute("shopList", shopList);
						}
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", se.getStateInfo());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}
//	private static void inputStreamToFile(InputStream ins,File file) {
//		OutputStream os=null;
//		try {
//			os=new FileOutputStream(file);
//			int bytesRead = 0;
//			byte[] buffer = new byte[1024];
//			while((bytesRead = ins.read(buffer))!=-1) {
//				os.write(buffer, 0, bytesRead);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			throw new RuntimeException("调用inputStreamToFile异常"+e.getMessage());
//		}finally {
//				try {
//					if(os != null) {
//					    os.close();
//					}
//					if(ins != null) {
//						ins.close();
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
	
}
