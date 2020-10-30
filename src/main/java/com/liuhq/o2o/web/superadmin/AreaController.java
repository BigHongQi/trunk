package com.liuhq.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liuhq.o2o.entity.Area;
import com.liuhq.o2o.service.AreaService;

@Controller
@RequestMapping("superadmin")
public class AreaController {
    
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/listareas", method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listAreas(){
		 Map<String,Object> modelMap=new HashMap<String,Object>();
		 List<Area> list=new ArrayList<>();
		 try {
			list=areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelMap.put("sucess", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
		
	}
}
