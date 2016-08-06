package com.forerunner.core.service.system;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.forerunner.core.repository.system.MenuRepository;
import com.forerunner.core.search.SearchOperator;
import com.forerunner.core.search.Searchable;
import com.forerunner.core.service.BaseService;
import com.forerunner.foundation.domain.po.system.Menu;
import com.google.common.collect.Lists;

@Service
public class MenuService extends BaseService<Menu, Long>{
	@Autowired
	private MenuRepository menuRepository;
	
	public Integer getMaxMenuFloor(){
		return menuRepository.getMaxMenuFloor();
	}
	
	public List<Menu> gainMenu(String type) {
		if(!"show".equals(type)){
			type="admin";
		}
		Searchable searchable=Searchable.newSearchable();
		searchable.addSearchFilter("type", SearchOperator.eq, type);
		searchable.addSort(Direction.DESC, "sequence");
		List<Menu> list=this.findAllWithSort(searchable);
		addChilds(list);
		placeSurplus(list);
		return list;
	}
	/**
	 * 组装Menu
	 * @param menuList
	 */
	private void addChilds(List<Menu> menuList){
		for(Menu menu:menuList){
			List<Menu> childs=Lists.newArrayList();
			for(Menu menu1:menuList){
				if(menu1.getParentId()==menu.getId()){
					childs.add(menu1);
				}
			}
			menu.setChilds(childs);
		}
	}
	/**
	 * 去除多余的下级菜单
	 * 有上级的值删除掉
	 * @param set
	 */
	private void placeSurplus(List<Menu> list){
		Iterator<Menu> ite=list.iterator();
		while(ite.hasNext()){
			Menu menu=ite.next();
			if(menu.getParentId()!=null&&menu.getParentId()>0){
				ite.remove();
			}
		}
	}
}
