package com.neu.wms.domain;

public class Dept {
    private Integer id;

    private String name;

    private Integer parentId;
    
    //��ϵ�����ݿ�����ͨ�����������ʾ��
    //��ϵ�������������ͨ����������ʾ��
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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