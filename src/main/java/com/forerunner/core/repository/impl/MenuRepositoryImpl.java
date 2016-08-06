package com.forerunner.core.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.forerunner.core.tool.CommUtil;

public class MenuRepositoryImpl {
	@PersistenceContext
	private EntityManager em;
	
	public Integer getMaxMenuFloor(){
		String sql="select max(level) from sys_menu where delete_status=?";
		Query query=em.createNativeQuery(sql);
		query.setParameter(1,0);
		Object obj=query.getSingleResult();
		Integer maxLevel=CommUtil.null2Int(CommUtil.null2Float(obj));	
		return maxLevel;
	}
	
}
