package com.neu.wms.query;

public class UserQuery extends BaseQuery{
	
	//ǰ̨���ύname loginName
	private String name;
	private String loginName;
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}