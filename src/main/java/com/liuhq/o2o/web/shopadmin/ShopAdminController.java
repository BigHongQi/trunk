package com.liuhq.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin",method=(RequestMethod.GET))
public class ShopAdminController {
 
	@RequestMapping(value="/shopedit")
	public String ShopEdit() {
		return "shop/shopedit";
		
	}
	
	@RequestMapping(value="/shoplist")
    public String shoplist() {
       return "shop/shoplist";
    }
	
    @RequestMapping(value="/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }
    
    @RequestMapping(value="/shopauthmanagement")
    public String shopAuthManagement() {
        return "shop/shopauthmanagement";
    }
    @RequestMapping(value="/shopauthedit")
    public String shopAuthEdit() {
        return "shop/shopauthedit";
    }
    @RequestMapping(value="/shopauthadd")
    public String shopAuthAdd() {
        return "shop/shopauthadd";
    }
    @RequestMapping(value="/operationfail",method = RequestMethod.GET)
    public String operationFail() {
        return "shop/operationfail";
    }
    @RequestMapping(value="/operationsucess",method = RequestMethod.GET)
    public String operationSucess() {
        return "shop/operationsucess";
    }
    @RequestMapping(value="/productbuycheck",method = RequestMethod.GET)
    public String productBuyCheck() {
        return "shop/productbuycheck";
    }
    @RequestMapping(value = "/productcategorymanagement")
    public String ProductCategoryManagement() {
        return "shop/productcategorymanagement";
    }
    
    @RequestMapping(value = "/productedit")
    public String ProductEdit() {
        return "shop/productedit";
    }
    
    @RequestMapping(value = "/productmanagement")
    public String Productmanagement() {
        return "shop/productmanagement";
    }
}
