package com.liuhq.o2o.config.web;


import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.liuhq.o2o.interceptor.shop.ShopLoginInterceptor;
import com.liuhq.o2o.interceptor.shop.ShopPermissionInterceptor;


/**
 * 开启MVC自动注入Spring容器， WebMvcConfigurerAdapter：配置视图解析器
 * 当一个类实现了这个接口（ApplicationContextAware）之后，这个类就可以方便获得ApplicationContext
 * @author RD-10
 *
 */
@Configuration
//相当于<mvc:annotation-driven />
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter  implements ApplicationContextAware {
    //Spring容器  
	private ApplicationContext applicationContext;
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 静态资源配置
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
		//registry.addResourceHandler("/upload/**").addResourceLocations("file:/home/hongqipro/upload/");
		  registry.addResourceHandler("/upload/**").addResourceLocations("file:/D:/projectdev/image/upload/");
	}
	
	/**
	 * 定义默认的请求处理器
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/**
	 * 创建.定义视图解析器
	 * @return
	 */
	@Bean(name="viewResolver")
	public ViewResolver createViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		//设置Spring容器
		viewResolver.setApplicationContext(this.applicationContext);
		//取消缓存
		viewResolver.setCache(false);
		//设置解析的前缀
		viewResolver.setPrefix("/WEB-INF/html/");
		//设置视图解析的后缀
		viewResolver.setSuffix(".html");
		return viewResolver;
	}
	/**
	 * 文件上传解析器
	 * @return
	 */
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver createMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		//1024*1024*20=20M
		multipartResolver.setMaxUploadSize(20971520);
		multipartResolver.setMaxInMemorySize(20971520);
		return multipartResolver;
	}
	
	@Value("${kaptcha.border}")
	private String border;
	@Value("${kaptcha.textproducer.font.color}")
	private String fcolor;
	@Value("${kaptcha.image.width}")
	private String width;
	@Value("${kaptcha.textproducer.char.string}")
	private String cString;
	@Value("${kaptcha.image.height}")
	private String height;
	@Value("${kaptcha.textproducer.font.size}")
	private String fsize;
	@Value("${kaptcha.noise.color}")
	private String nColor;
	@Value("${kaptcha.textproducer.char.length}")
	private String clength;
	@Value("${kaptcha.textproducer.font.names}")
	private String fnames;
	/**
	 * 配置验证码
	 * @return
	 */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() throws ServletException {
    	ServletRegistrationBean  servlet = new ServletRegistrationBean(new KaptchaServlet(),"/Kaptcha");
    	servlet.addInitParameter("kaptcha.border", border);//无边框
    	servlet.addInitParameter("kaptcha.textproducer.font.color", fcolor);//字体颜色
    	servlet.addInitParameter("kaptcha.image.width", width);//图片宽度
    	servlet.addInitParameter("kaptcha.textproducer.char.string", cString);//使用的字体
    	servlet.addInitParameter("kaptcha.image.height", height);//图片高度
    	servlet.addInitParameter("kaptcha.textproducer.font.size", fsize);//字体颜色
    	servlet.addInitParameter("kaptcha.noise.color", nColor);//干扰线的颜色
    	servlet.addInitParameter("kaptcha.textproducer.char.length", clength);//字符个数
    	servlet.addInitParameter("kaptcha.textproducer.font.names", fnames);//字体
		return servlet;
    }
    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	String interceptPath = "/shopadmin/**";
    	//注册拦截器
    	InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
    	//配置拦截的路径
    	loginIR.addPathPatterns(interceptPath);
    	loginIR.excludePathPatterns("/shopadmin/addshopauthmap");
    	//注册其他的拦截器
    	InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
    	//配置拦截的路径
    	permissionIR.addPathPatterns(interceptPath);
    	//配置不拦截的路径
    	//shoplist page 
    	permissionIR.excludePathPatterns("/shopadmin/shoplist");
    	permissionIR.excludePathPatterns("/shopadmin/getshoplist");
    	// shopmanage page
    	permissionIR.excludePathPatterns("/shopadmin/shopmanagement");
    	permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
    	//shopedit page
    	permissionIR.excludePathPatterns("/shopadmin/shopedit");
    	permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
    	permissionIR.excludePathPatterns("/shopadmin/registershop");
    	
    	permissionIR.excludePathPatterns("/shopadmin/addshopauthmap");
    	
    }
}
