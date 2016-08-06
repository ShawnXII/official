package com.forerunner.admin.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.forerunner.core.service.system.MenuService;
import com.forerunner.core.tool.CommUtil;
import com.forerunner.core.tool.ParamsTool;
import com.forerunner.core.tool.SpringUtils;
import com.forerunner.core.web.resource.CommonParams;
import com.forerunner.foundation.domain.po.system.Menu;
import com.forerunner.web.controller.BaseController;
/**
 * 系统设置,用户,权限等系统管理
 * @author Administrator
 */
@Controller
@RequestMapping(value="/admin/system")
public class SystemController extends BaseController{
	
	@Autowired
	private MenuService menuService;	
	/**
	 * 系统设置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/setUp.htm"})
	public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view=new ModelAndView();
		view.setViewName("/admin/system/setUp");
		CommonParams.loadParams(view,"系统设置");
		
		return view;
	}
	/**
	 * 系统菜单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/menuList.htm")
	public ModelAndView toMenuView(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view=new ModelAndView();
		view.setViewName("/admin/system/menuList");
		CommonParams.loadParams(view,"系统菜单列表");
		List<Menu> resultList=menuService.gainMenu("show");
		view.addObject("menuShowList", resultList);
		return view; 
	}
	
	@RequestMapping(value="/addMenu.htm", method = RequestMethod.POST)
	public ModelAndView addMenu(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view=new ModelAndView();
		view.setViewName("/admin/system/menuAdd");
		Long parentId=CommUtil.null2Long(request.getParameter("id"),-1);
		String type=CommUtil.null2String(request.getParameter("type"));
		if(!"show".equals(type)){
			type="admin";
		}
		String typeName="前端菜单";
		if(type.equals("admin")){
			typeName="后端菜单";
		}
		Integer level=0;
		if(parentId>0){
			Menu menu=this.menuService.findOne(parentId);
			if(menu==null){
				view.addObject("success", "系统错误,找不到上级菜单,请重新选择添加");
				view.setViewName("redirect:/admin/system/menuList.htm");
				return view;
			}
			level=(int) (menu.getLevel()+1);
			if(level>5){
				view.addObject("success", "不能继续添加,系统最多支持5级菜单");
				view.setViewName("redirect:/admin/system/menuList.htm");
				return view;
			}
			view.addObject("parent", menu);	
		}
		view.addObject("typeName", typeName);
		view.addObject("type", type);
		view.addObject("level", level);
		CommonParams.loadParams(view,"系统菜单新增");
		//加载系统资源
		RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtils.getBean(RequestMappingHandlerMapping.class);
		List<Map<String,Object>> urlResource=CommUtil.getUrlResource(requestMappingHandlerMapping);
		view.addObject("urlResource", urlResource);
		return view; 
	}
	
	
	@RequestMapping(value = "/menu/add.htm", method = RequestMethod.POST)
	public String addMenu(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
		Menu menu = ParamsTool.WebRequestToBean(request, Menu.class);
		if (menu != null) {
			String str = "添加";
			if (menu.getId() != null && menu.getId() > 0) {
				str = "修改";
			}
			this.menuService.saveAndFlush(menu);
			attr.addFlashAttribute("success", str + "成功!");
			return "redirect:/admin/system/menuList.htm";
		}	
		return "redirect:/admin/system/addMenu.htm";
	}
	
	@RequestMapping(value="/editMenu.htm", method = RequestMethod.POST)
	public ModelAndView editMenu(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view=new ModelAndView();
		Long id=CommUtil.null2Long(request.getParameter("id"));
		Menu menu=this.menuService.findOne(id);	
		if(menu==null){
			view.setViewName("redirect:/admin/system/menuList.htm");
		}
		String type=menu.getType();
		if(!"show".equals(type)){
			type="admin";
		}
		String typeName="前端菜单";
		if(type.equals("admin")){
			typeName="后端菜单";
		}
		Long pid=menu.getParentId();
		if(pid!=null){
			Menu parent=this.menuService.findOne(pid);
			if(parent!=null){
				view.addObject("parent", parent);
			}
		}
		CommonParams.loadParams(view,menu.getTitle()+"修改");
		view.addObject("menu", menu);
		view.addObject("level", menu.getLevel());
		view.addObject("type", type);
		view.addObject("typeName", typeName);
		view.setViewName("/admin/system/menuAdd");
		RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtils.getBean(RequestMappingHandlerMapping.class);
		List<Map<String,Object>> urlResource=CommUtil.getUrlResource(requestMappingHandlerMapping);
		view.addObject("urlResource", urlResource);
		return view;
	}
	
	@RequestMapping(value="/deleteMenu.htm", method = RequestMethod.POST)
	public String deleteMenu(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr){
		Long id=CommUtil.null2Long(request.getParameter("id"));
		this.menuService.delete(id);
		attr.addFlashAttribute("success", "删除成功!");
		return "redirect:/admin/system/menuList.htm";
	}
	
}
