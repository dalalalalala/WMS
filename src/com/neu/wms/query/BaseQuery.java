package com.neu.wms.query;

public class BaseQuery {
	 
	private int offset;         //����ǰ������� (offset=(page-1)*pageSize)
	private int pageSize=3;     //ÿҳ��ʾ������
	  
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
