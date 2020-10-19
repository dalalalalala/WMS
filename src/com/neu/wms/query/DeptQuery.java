package com.neu.wms.query;

public class DeptQuery extends BaseQuery{
	 
	private String name;
	
	private Integer parentId;   //¸¸²¿ÃÅµÄid
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
