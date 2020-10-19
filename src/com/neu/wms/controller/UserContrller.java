package com.neu.wms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.wms.domain.Dept;
import com.neu.wms.domain.Role;
import com.neu.wms.domain.User;
import com.neu.wms.query.DeptQuery;
import com.neu.wms.query.UserQuery;
import com.neu.wms.service.DeptService;
import com.neu.wms.service.RoleService;
import com.neu.wms.service.UserService;
import com.neu.wms.utils.JsonModel;

@Controller
@RequestMapping("users")
public class UserContrller {
	
	@Autowired
	UserService userService;
	
	@Autowired
	DeptService deptService;
	
	@Autowired
	RoleService roleService;
	
	/**
	 * �б����
	 * @return
	 */
	@RequestMapping(value="listUI",method=RequestMethod.GET)
	public String listUI() {
		return "user/list";
	}
	
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel list(UserQuery userQuery) { //UserQuery:��װ�˲�ѯ����
		JsonModel jsonModel = new JsonModel();
		 
		List<User> users = userService.findByQuery(userQuery);
		
		//��ѯ��ǰ�����µ��ܵļ�¼��
		
		long count = userService.findCount(userQuery);
		jsonModel.setCount(count);
		jsonModel.setData(users);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("�����û��ɹ�");
		return jsonModel;
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Model model) {
		
		
		
		
		List<Dept> depts = new ArrayList<Dept>();
		
		deptTree(depts,null,"|--");
		
		model.addAttribute("depts",depts);
		
		return "user/add";
	}
	
	//�������
	private void deptTree(List<Dept> depts,Integer parentId,String namePrefix) {
		 
		DeptQuery deptQuery = new DeptQuery();
		deptQuery.setParentId(parentId);
		//���Ҷ�������
		List<Dept> parents = deptService.findByQuery(deptQuery);
		
		//�ܲð�
		   //�г��� ...
		      //...
		//���»�
		
		if(parents!=null && !parents.isEmpty()) {
			//���Ҷ�Ӧ���Ӳ���
			for(Dept parent:parents) { //�ܲð�
				//�����ܲð�������Ӳ���
				parent.setName(namePrefix+parent.getName());
				depts.add(parent);
				deptTree(depts,parent.getId(),"����"+namePrefix);//�г���
			}
		}
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(User user) {
		//����service
		userService.add(user);
		return "redirect:/users/listUI";
	}
	
	
	/**
	   *  ɾ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel delete(int [] id) {
		userService.delete(id);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		jsonModel.setMsg("ɾ���ɹ�");
		return jsonModel;
	}
	
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String edit(int id,Model model) {
		
		List<Dept> depts = new ArrayList<Dept>();
		
		deptTree(depts,null,"|--");
		model.addAttribute("depts",depts);
		
		
		
		User user = userService.findById(id);
		//������������������
		
		for(Dept temp : depts) {
			if(temp.getId().equals(user.getDept().getId())) {  //��ǰ�û��Ĳ����ҵ���
				user.setDept(temp);
			}
		}
		
		model.addAttribute("user",user);
		
		return "user/edit";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(User user) {
		userService.update(user);
		return "redirect:/users/listUI";
	}
	
	//"+userId+"/roles";
	@RequestMapping(value="{id}/roles",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel findRoleByUserId(@PathVariable("id")int userId) {
		
		List<Role> roles = roleService.findRoleByUserId(userId);
		 
		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		jsonModel.setData(roles);
		return jsonModel;
	}
	
	/**
	   * �û��ͽ�ɫ��
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="{id}/roles",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel bind(@PathVariable("id") int userId,int [] role) {
		//�û��ͽ�ɫ��
		userService.bind(userId,role);
		
		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	
}
