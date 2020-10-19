package com.neu.wms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.wms.domain.Role;
import com.neu.wms.query.RoleQuery;
import com.neu.wms.service.RoleService;
import com.neu.wms.utils.JsonModel;

@Controller
@RequestMapping("roles")
public class RoleContrller {
	
	@Autowired
	RoleService roleService;
	 
	
	/**
	 * �б����
	 * @return
	 */
	@RequestMapping(value="listUI",method=RequestMethod.GET)
	public String listUI() {
		return "role/list";
	}
	
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel list(RoleQuery roleQuery) { //RoleQuery:��װ�˲�ѯ����
		JsonModel jsonModel = new JsonModel();
		 
		List<Role> roles = roleService.findByQuery(roleQuery);
		
		//��ѯ��ǰ�����µ��ܵļ�¼��
		
		long count = roleService.findCount(roleQuery);
		jsonModel.setCount(count);
		jsonModel.setData(roles);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("���ҽ�ɫ�ɹ�");
		return jsonModel;
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Model model) {
		return "role/add";
	}
	 
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Role role) {
		//����service
		roleService.add(role);
		return "redirect:/roles/listUI";
	}
	
	
	/**
	   *  ɾ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel delete(int [] id) {
		roleService.delete(id);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		jsonModel.setMsg("ɾ���ɹ�");
		return jsonModel;
	}
	
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String edit(int id,Model model) {
		Role role = roleService.findById(id);
		model.addAttribute("role",role);
		return "role/edit";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(Role role) {
		roleService.update(role);
		return "redirect:/roles/listUI";
	}
}
