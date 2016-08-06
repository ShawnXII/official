package com.forerunner.foundation.domain.po.system;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.forerunner.foundation.domain.po.BaseEntity;
import com.google.common.collect.Lists;

@Entity
@Table(name="sys_menu")
public class Menu extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8898681653797580674L;
	@Id
	@Column(name="id")
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="url",length=64)
	private String url;
	
	@Column(name="target")
	private String target;
	/**
	 * 级别 最多支持到99级
	 */
	@Column(name="level",length=2)
	private Float level;
	
	@Column(name="sequence")
	private Integer sequence;
	
	@Column(name="title")
	private String title;
	
	@Column(name="info")
	private String info;
	
	@Column(name="type")
	private String type;
	
	@Column(name="icon")
	private String icon;
	
	@Column(name="parent_id")
	private Long parentId;
	
	@Transient
	private Menu parent;
	
	@Transient
	private List<Menu> childs=Lists.newArrayList();

	public List<Menu> getChilds() {
		return childs;
	}
	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Float getLevel() {
		return level;
	}
	public void setLevel(Float level) {
		this.level = level;
	}
	
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", url=" + url + ", target=" + target + ", level=" + level + ", sequence=" + sequence
				+ ", title=" + title + ", info=" + info + ", type=" + type + ", icon=" + icon + ", parentId=" + parentId
				+ ", parent=" + parent + ", childs=" + childs + "]";
	}
	/**
	 * 使用ID做唯一标识 很重要
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
