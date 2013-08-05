package com.ambow.trainingengine.systemsecurity.web.action;
/**
 * SecurityFilter.java
 * 
 * Created on 2008-8-1 上午11:15:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: WangLiBin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
/**
 * 系统安全权限过滤器
 */
import static com.ambow.trainingengine.util.SessionDict.AdminUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ambow.studyflow.common.ServiceLocator;
import com.ambow.trainingengine.systemsecurity.domain.SysFunction;
import com.ambow.trainingengine.systemsecurity.domain.SysRole;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.service.AdminManageService;
import com.ambow.trainingengine.systemsecurity.service.FunctionManageService;
import com.ambow.trainingengine.systemsecurity.service.RoleManageService;
@SuppressWarnings("unused")
public class SecurityFilter implements Filter {
	/**
	 * 系统功能集合
	 */
	public static List<SysFunction> checkURLList = new ArrayList<SysFunction>();
	/**
	 * 访问功能的服务对象名
	 */
	private String service = "functionManageService";
	/**
	 * 是否开启权限过滤ON/OFF
	 */
	private String ignore;
	/**
	 * 过滤控制对象
	 */
	private FilterConfig filterConfig = null;
	/**
	 * 权限访问失败的时候跳转的地址
	 */
	private static String errorURL = null;
	/**
	 * 监听的用户session数据存储名称
	 */
	private String sessionKey = null;
	/**
	 * 初始化权限过滤器信息
	 */
	@SuppressWarnings("static-access")
	public void init(FilterConfig filterConfig) throws ServletException {
		sessionKey = AdminUser;
		this.filterConfig = filterConfig;
		errorURL = filterConfig.getInitParameter("errorURL");//过去错误跳转路径
		ignore = filterConfig.getInitParameter("ignore");//获取开关信息
		FunctionManageService functionManage = (FunctionManageService) ServiceLocator.getService(service);//得到功能服务类对象
		checkURLList = functionManage.getSysFunctionList();//获取全部的权限功能
	}

	/**
	 * 权限过滤
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		Object sysUser = session.getAttribute(sessionKey);//得到登陆用户对象
		String path = request.getServletPath();//得到访问的地址路径
		if (ignore.equals("ON")) {//当权限过滤功能开启时
			if(sysUser==null&&path.startsWith("/admin/")&&!path.startsWith("/admin/login.jsp")&&!path.startsWith("/admin/adminLogin.jhtml")&&!path.startsWith("/admin/image.jsp")){
				response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
				return;
			}
			boolean flag = this.checkRequestURIIntNotFilterList(path);//判断访问的路径是否在属于控制权限的地址范围内，不是就放过，是就继续处理
			if (flag) {//当在范围内的时候
				flag = this.checkrequestURIIntNotUserFilterList(sysUser,path);//判断此权限是否属于用户的使用权限
				if (flag) {//当用户没有访问权限是，跳转到指定路径
					response.sendRedirect(request.getContextPath() + errorURL);
					return;
				}
				//后台登陆的当前session会话设置为2小时
				session.setMaxInactiveInterval(60*60*2);
			}		
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 判断访问的路径是否属于用户的访问范围
	 * @param sysUser
	 * @param path
	 * @return
	 */
	public boolean checkrequestURIIntNotUserFilterList(Object sysUser,String path) {
		boolean flag=true;
		if (sysUser!= null){//判断用户是否登陆
			SysUser sysu = (SysUser) sysUser;
			List<SysRole> rolelist=((RoleManageService)ServiceLocator.getService("roleManageService")).getSysRoleList(sysu.getId());
			SysFunction sysFun = null;
			for (Iterator<SysRole> it = rolelist.iterator(); it
					.hasNext();) {
				SysRole sysr=it.next();
				List<SysFunction> funlist=((FunctionManageService)ServiceLocator.getService("functionManageService")).getSysFunctionList(sysr.getId());
				for (Iterator<SysFunction> sit = funlist.iterator(); sit.hasNext();) {
					sysFun = sit.next();
					if (path.startsWith((sysFun.getPath() + sysFun.getAction()))&&sysFun.getStatus()==0) {//当属于用户访问的范围,并且功能是启用的。
						return false;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 判断访问路径是否属于权限控制的范围内
	 * @param path
	 * @return
	 */
	public boolean checkRequestURIIntNotFilterList(String path) {
		SysFunction sysFun = null;
		for (Iterator<SysFunction> it = checkURLList.iterator(); it.hasNext();) {
			sysFun = it.next();
			if (path.startsWith((sysFun.getPath() + sysFun.getAction()))) {//当属于用户访问的范围,并且功能是启用的。
				return true;
			}
		}
		return false;
	}

	/**
	 * 销毁对象数据
	 */
	public void destroy() {
		checkURLList.clear();
	}
}
