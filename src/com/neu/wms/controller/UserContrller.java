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
	 * 列表界面
	 * @return
	 */
	@RequestMapping(value="listUI",method=RequestMethod.GET)
	public String listUI() {
		return "user/list";
	}
	
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel list(UserQuery userQuery) { //UserQuery:封装了查询参数
		JsonModel jsonModel = new JsonModel();
		 
		List<User> users = userService.findByQuery(userQuery);
		
		//查询当前条件下的总的记录数
		
		long count = userService.findCount(userQuery);
		jsonModel.setCount(count);
		jsonModel.setData(users);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("查找用户成功");
		return jsonModel;
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Model model) {
		
		
		
		
		List<Dept> depts = new ArrayList<Dept>();
		
		deptTree(depts,null,"|--");
		
		model.addAttribute("depts",depts);
		
		return "user/add";
	}
	
	//重在理解
	private void deptTree(List<Dept> depts,Integer parentId,String namePrefix) {
		 
		DeptQuery deptQuery = new DeptQuery();
		deptQuery.setParentId(parentId);
		//查找顶级部门
		List<Dept> parents = deptService.findByQuery(deptQuery);
		
		//总裁办
		   //市场部 ...
		      //...
		//董事会
		
		if(parents!=null && !parents.isEmpty()) {
			//查找对应的子部门
			for(Dept parent:parents) { //总裁办
				//查找总裁办下面的子部门
				parent.setName(namePrefix+parent.getName());
				depts.add(parent);
				deptTree(depts,parent.getId(),"　　"+namePrefix);//市场部
			}
		}
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(User user) {
		//调用service
		userService.add(user);
		return "redirect:/users/listUI";
	}
	
	
	/**
	   *  删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel delete(int [] id) {
		userService.delete(id);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		jsonModel.setMsg("删除成功");
		return jsonModel;
	}
	
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String edit(int id,Model model) {
		
		List<Dept> depts = new ArrayList<Dept>();
		
		deptTree(depts,null,"|--");
		model.addAttribute("depts",depts);
		
		
		
		User user = userService.findById(id);
		//作用域中用来做回显
		
		for(Dept temp : depts) {
			if(temp.getId().equals(user.getDept().getId())) {  //当前用户的部门找到了
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
	   * 用户和角色绑定
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="{id}/roles",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel bind(@PathVariable("id") int userId,int [] role) {
		//用户和角色绑定
		userService.bind(userId,role);
		
		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	
}
