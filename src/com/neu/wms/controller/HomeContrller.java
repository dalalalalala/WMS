package com.neu.wms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.wms.domain.User;
import com.neu.wms.service.UserService;
import com.neu.wms.utils.JsonModel;

@Controller
public class HomeContrller {
	
	
	@Autowired
	UserService userSerivce;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String main() {
		return "main";
	}
 
	
	/**
	 * �˳�
	 * @return
	 */
	@RequestMapping(value="logout",method=RequestMethod.GET)
	public String logout(HttpSession session) {
		//���session(ʹsession����)
		session.invalidate();
		return "redirect:/login";
	}
	
	/**
	 * ת������¼����
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	 
	/**
	 *    ��¼
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel login(String loginName,String password,HttpSession session) {
		JsonModel jsonModel = new JsonModel();
		 
		User user = userSerivce.login(loginName, password);
		
		if(user==null) {
			jsonModel.setSuccess(false);
			jsonModel.setMsg("�˺Ż��������");
		}else {
			session.setAttribute("user",user);  
			//��¼�������Ϳ��Ը���session����û��user���Զ�Ӧ��ֵ���ж��Ƿ��¼�ɹ�
			jsonModel.setSuccess(true);
			jsonModel.setMsg("��¼�ɹ�");
		}
		return jsonModel;
	}
}
