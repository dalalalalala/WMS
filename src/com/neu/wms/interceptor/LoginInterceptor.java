package com.neu.wms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//���ɵ�¼�˲��ܷ���������
		Object data = request.getSession().getAttribute("user");
		if(data==null) {
			//û�е�¼
			    //�������������¼,Ӧ�÷���
			String requestURI = request.getRequestURI(); // /WMS/login
			
			if(requestURI.equals(request.getContextPath()+"/login")) {
				return true;
			}
			
			//ȥ����¼����
			//request.getContextPath() ��ȡ������(/WMS)
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}else {
			//�ж��Ƿ�������¼,�����ȥ��������
            String requestURI = request.getRequestURI(); // /WMS/login
			
			if(requestURI.equals(request.getContextPath()+"/login")) {
				//ȥ�������� 
				response.sendRedirect(request.getContextPath());
				return false;
			}
			//�е�¼
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
