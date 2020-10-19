package com.neu.wms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.wms.domain.Dept;
import com.neu.wms.query.DeptQuery;
import com.neu.wms.service.DeptService;
import com.neu.wms.utils.JsonModel;

@Controller
@RequestMapping("depts")
public class DeptContrller {
	
	@Autowired
	DeptService deptService;
	
	/**
	 * �б����
	 * @return
	 */
	@RequestMapping(value="listUI",method=RequestMethod.GET)
	public String listUI() {
		return "dept/list";
	}
	
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel list(DeptQuery deptQuery) {
		JsonModel jsonModel = new JsonModel();
		 
		List<Dept> depts = deptService.findByQuery(deptQuery);
		 
		long count = deptService.findCount(deptQuery);
		jsonModel.setCount(count);
		jsonModel.setData(depts);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("���Ҳ��ųɹ�");
		return jsonModel;
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add() {
		return "dept/add";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Dept dept) {
		//����service
		deptService.add(dept);
		return "redirect:/depts/listUI";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String edit(int id,Model model) {//Model ModelMap ���ǵ����ݻ���뵽request��������
		
		Dept dept = deptService.findById(id);
		//������������������
		
		model.addAttribute("dept",dept);
		
		return "dept/edit";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(Dept dept) {
		deptService.update(dept);
		return "redirect:/depts/listUI";
	}
	
	
	/**
	   *  ɾ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel delete(int [] id) {
		deptService.delete(id);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		jsonModel.setMsg("ɾ���ɹ�");
		return jsonModel;
	}
	
	
}
