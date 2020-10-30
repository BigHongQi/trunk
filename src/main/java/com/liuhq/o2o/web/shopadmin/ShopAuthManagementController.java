package com.liuhq.o2o.web.shopadmin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.liuhq.o2o.dto.ShopAuthMapExecution;
import com.liuhq.o2o.dto.UserAccessToken;
import com.liuhq.o2o.dto.WechatInfo;
import com.liuhq.o2o.entity.PersonInfo;
import com.liuhq.o2o.entity.Shop;
import com.liuhq.o2o.entity.ShopAuthMap;
import com.liuhq.o2o.entity.WechatAuth;
import com.liuhq.o2o.enums.ShopAuthMapStateEnum;
import com.liuhq.o2o.service.PersonInfoService;
import com.liuhq.o2o.service.ShopAuthMapService;
import com.liuhq.o2o.service.WechatAuthService;
import com.liuhq.o2o.utils.BaiduDwz;
import com.liuhq.o2o.utils.CodeUtil;
import com.liuhq.o2o.utils.HttpServletRequestUtil;
import com.liuhq.o2o.utils.WechatUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {
	
	@Autowired
	private ShopAuthMapService shopAuthMapService;
	
	@Autowired
	private WechatAuthService wechatAuthService;
	
	@Autowired
	private PersonInfoService personInfoService;
    
	private static String urlPrefix;
	private static String urlMiddle;
	private static String urlSuffix;
	private static String authUrl;
	
	@Value("${wechat.prefix}")
	public void setUrlPrefix(String urlPrefix) {
		ShopAuthManagementController.urlPrefix = urlPrefix;
	}
	@Value("${wechat.middle}")
	public void setUrlMiddle(String urlMiddle) {
		ShopAuthManagementController.urlMiddle = urlMiddle;
	}
	@Value("${wechat.suffix}")
	public void setUrlSuffix(String urlSuffix) {
		ShopAuthManagementController.urlSuffix = urlSuffix;
	}
	@Value("${wechat.auth.url}")
	public void setAuthUrl(String authUrl) {
		ShopAuthManagementController.authUrl = authUrl;
	}
	@RequestMapping(value = "/generateqrcode4shopauth", method = RequestMethod.GET)
	@ResponseBody
    private  void generateQRCode4ShopAuth(HttpServletRequest request,HttpServletResponse response) {
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if(currentShop != null && currentShop.getShopId() != null) {
			//保证二维码的时间的有效性
			long timeStamp = System.currentTimeMillis();
			String content ="{aaashopIdaaa:"+currentShop.getShopId()+",aaacreateTimeaaa"+timeStamp+"}";
			try {
				String longUrl = urlPrefix+authUrl+urlMiddle+URLEncoder.encode(content,"UTF-8")+urlSuffix;
				String shortUrl = BaiduDwz.createShortUrl(longUrl, "1-year");
				//生成二维码
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStrream(shortUrl, response);
				//将二维码以图片流的方式输出到前端
				MatrixToImageWriter.writeToStream(qRcodeImg, "png",response.getOutputStream());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	@RequestMapping(value = "/addshopauthmap", method = RequestMethod.GET)
	@ResponseBody
	private String addShopAuthMAp(HttpServletRequest request,HttpServletResponse response) {
		WechatAuth auth = getEmployeeInfo(request);
		if(auth != null) {
			PersonInfo user = personInfoService.getPersonInfoById(auth.getUserId());
			request.getSession().setAttribute("user", user);
			WechatInfo wechatInfo = null;
			try {
				String qrCodeInfo = new String(URLEncoder.encode(HttpServletRequestUtil.getString(request, "state"),"UTF-8"));
				ObjectMapper mapper = new ObjectMapper();
				  wechatInfo = mapper.readValue(qrCodeInfo.replace("aaa", "\""), WechatInfo.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "shop/operationfail";
			}
			if(!checkQRCodeInfo(wechatInfo)) {
				return "shop/operationfail";
			}
			ShopAuthMapExecution allMapList = shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(), 1, 999);
			List<ShopAuthMap> shopAuthMaps = allMapList.getShopAuthMapList();
			for (ShopAuthMap shopAuthMap : shopAuthMaps) {
				if(shopAuthMap.getEmployeeId() == user.getUserId()) {
					return "shopadmin/operationfail";
				}
			}
			try {
				ShopAuthMap shopAuthMap = new ShopAuthMap();
				shopAuthMap.setShopId(wechatInfo.getShopId());
				shopAuthMap.setEmployeeId(user.getUserId());
				shopAuthMap.setEmployee(user);
				shopAuthMap.setTitle("员工");
				shopAuthMap.setTitleFlag(1);
				ShopAuthMapExecution se = shopAuthMapService.addShopAuthMap(shopAuthMap);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					return  "shop/operationsucess";
				} else {
					return "shop/operationfail";
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "shop/operationfail";
			}
		}
		
		return "shop/operationfail";
		
	}
	@RequestMapping(value = "/listshopauthmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopAuthMapsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)&& (currentShop.getShopId() != null)) {
			ShopAuthMapExecution se = shopAuthMapService.listShopAuthMapByShopId(currentShop.getShopId(),pageIndex, pageSize);
			modelMap.put("shopAuthMapList", se.getShopAuthMapList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopauthmapbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopAuthMapById(@RequestParam Long shopAuthId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (shopAuthId != null && shopAuthId > -1) {
			ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
			modelMap.put("shopAuthMap", shopAuthMap);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopAuthId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/addshopauthmaphand", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addShopAuthMap(String shopAuthMapStr,HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		ShopAuthMap shopAuthMap = null;
		try {
			shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (shopAuthMap != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				if (currentShop.getOwnerId() != user.getUserId()) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "无操作权限");
					return modelMap;
				}
				shopAuthMap.setShopId(currentShop.getShopId());
				shopAuthMap.setEmployeeId(user.getUserId());
				shopAuthMap.setTitleFlag(1);
				ShopAuthMapExecution se = shopAuthMapService.addShopAuthMap(shopAuthMap);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入授权信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyshopauthmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShopAuthMap(String shopAuthMapStr,HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//是授权编辑的时候调用  还是删除/恢复授权操作的时候调用
		//若为前者则进行验证码判断，后者则跳过验证码判断
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		ShopAuthMap shopAuthMap = null;
		try {
			shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
			try {
				if(!checkPermission(shopAuthMap.getShopAuthId())) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "无法对店家本身权限做操作(已是店铺的最高权限)");
					return modelMap;
				}
				ShopAuthMapExecution se = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入要修改的授权信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/removeshopauthmap", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> removeShopAuthMap(Long shopAuthId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (shopAuthId != null && shopAuthId > 0) {
			try {
				ShopAuthMapExecution se = shopAuthMapService
						.removeShopAuthMap(shopAuthId);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一个授权进行删除");
		}
		return modelMap;
	}
	/**
	 * 检查被操作的对象是否可修改
	 * @param shopAuthId
	 * @return
	 */
	private boolean checkPermission(long shopAuthId) {
		ShopAuthMap authMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
		if(authMap.getTitleFlag() == 0) {
			//若是店家本身。不能操作
			return false;
		}else {
			return true;
		}
		
	}
	/**
	 * 检查二维码带的时间是否超过了十分钟。超过则过期
	 * @param shopAuthId
	 * @return
	 */
	private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
	    if(wechatInfo !=null && wechatInfo.getShopId() != 0 && wechatInfo.getCreateTime() != 0) {
	    	long nowTime = System.currentTimeMillis();
	    	if(nowTime - wechatInfo.getCreateTime() <= 600000) {
	    		return true;
	    	}else {
				return false;
			}
	    	
	    }else {
	    	return false;
		}
		
	}
	/**
	 * 获取信息
	 * @param request
	 * @return
	 */
	private WechatAuth getEmployeeInfo(HttpServletRequest request) {
		String code = request.getParameter("code");
		WechatAuth auth =null;
		if(null != code) {
			UserAccessToken token;
			try {
				token = WechatUtil.getUserAccessToken(code);
				String openId = token.getOpenId();
				request.getSession().setAttribute("openId", openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return auth;
		
	}
	

    public static void main(String[] args) {
    	String code = "011yWrW92ryCSN07chU92aM4W92yWrW0";
		WechatAuth auth =null;
		if(null != code) {
			UserAccessToken token;
			try {
				token = WechatUtil.getUserAccessToken(code);
				String openId = token.getOpenId();
				System.out.println(openId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
    }
}
