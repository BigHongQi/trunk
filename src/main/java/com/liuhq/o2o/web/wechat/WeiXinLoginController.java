package com.liuhq.o2o.web.wechat;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.liuhq.o2o.dto.UserAccessToken;
import com.liuhq.o2o.dto.WechatAuthExecution;
import com.liuhq.o2o.dto.WechatUser;
import com.liuhq.o2o.entity.PersonInfo;
import com.liuhq.o2o.entity.WechatAuth;
import com.liuhq.o2o.enums.WechatAuthStateEnum;
import com.liuhq.o2o.service.PersonInfoService;
import com.liuhq.o2o.service.WechatAuthService;
import com.liuhq.o2o.utils.WechatUtil;


@Controller
@RequestMapping("wechatlogin")
/**
 * 从微信菜单点击后调用的接口，可以在url里增加参数（role_type）来表明是从商家还是从玩家按钮进来的，依次区分登陆后跳转不同的页面
 * 玩家会跳转到index.html页面
 * 商家如果没有注册，会跳转到注册页面，否则跳转到任务管理页面
 * 如果是商家的授权用户登陆，会跳到授权店铺的任务管理页面
 * @author lixiang
 *
 */
public class WeiXinLoginController {

	private static Logger log = LoggerFactory.getLogger(WeiXinLoginController.class);
	
	private static final String FRONTEND = "1";
	private static final String SHOPEND = "2";
	
	@Autowired
	private PersonInfoService personInfoService;
	
	@Autowired
	private WechatAuthService wechatAuthService;

	 @RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	    public String doGet(HttpServletRequest request, HttpServletResponse response) {
	        log.debug("weixin login get...");
	        // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
	        String code = request.getParameter("code");
	        // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
	        String roleType = request.getParameter("state");
	        log.debug("weixin login code:" + code);
	        WechatUser user = null;
	        String openId = null;
	        WechatAuth auth = null;
	        if (null != code) {
	            UserAccessToken token;
	            try {
	                // 通过code获取access_token
	                token = WechatUtil.getUserAccessToken(code);
	                log.debug("weixin login token:" + token.toString());
	                // 通过token获取accessToken
	                String accessToken = token.getAccessToken();
	                // 通过token获取openId
	                openId = token.getOpenId();
	                // 通过access_token和openId获取用户昵称等信息
	                user = WechatUtil.getUserInfo(accessToken, openId);
	                log.debug("weixin login user:" + user.toString());
	                request.getSession().setAttribute("openId", openId);
	                auth = wechatAuthService.getWechatAuthByOpenId(openId);
	            } catch (IOException e) {
	                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
	                e.printStackTrace();
	            }
	        }
	        log.debug("auth :"+auth);
	        log.debug("weixin login success.");
			log.debug("login role_type:" + roleType);
			if(FRONTEND.equals(roleType)) {
				PersonInfo personInfo = null;
				WechatAuthExecution we = null;
				if (auth == null) {
					log.debug("测试进入:" + "11111111111");
					personInfo = WechatUtil.getPersonInfoFromRequest(user);
					personInfo.setCustomerFlag(1);
					personInfo.setShopOwnerFlag(0);
					personInfo.setAdminFlag(0);
					auth = new WechatAuth();
					auth.setOpenId(openId);
					auth.setPersonInfo(personInfo);
					we = wechatAuthService.register(auth);
					if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
						return null;
					}
				}
				personInfo = personInfoService.getPersonInfoById(auth.getUserId());
				request.getSession().setAttribute("user", personInfo);
				return "frontend/index";
			}
	        // ======todo begin======
	        // 前面咱们获取到openId后，可以通过它去数据库判断该微信帐号是否在我们网站里有对应的帐号了，
	        // 没有的话这里可以自动创建上，直接实现微信与咱们网站的无缝对接。
	        // ======todo end======
			if(SHOPEND.equals(roleType)) {
				PersonInfo personInfo = null;
				WechatAuthExecution we = null;
				if (auth == null) {
					log.debug("测试进入:" + "11111111111");
					personInfo = WechatUtil.getPersonInfoFromRequest(user);
					personInfo.setShopOwnerFlag(1);
					personInfo.setAdminFlag(-1);
					personInfo.setCustomerFlag(-1);
					auth = new WechatAuth();
					auth.setOpenId(openId);
					auth.setPersonInfo(personInfo);
					we = wechatAuthService.register(auth);
					if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
						return null;
					}
				}
				log.debug("authInfo:" + auth.getUserId());
				personInfo = personInfoService.getPersonInfoById(auth.getUserId());
				log.debug("personInfo:" + personInfo);
				request.getSession().setAttribute("user", personInfo);
			    return "shop/shoplist";
			}
//	        if (user != null) {
//	            // 获取到微信验证的信息后返回到指定的路由（需要自己设定）
//	            return "frontend/index";
//	        } else {
//	            return null;
//	        }
			return null;
			
	    }

	


}
