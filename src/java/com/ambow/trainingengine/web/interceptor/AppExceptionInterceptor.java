package com.ambow.trainingengine.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.util.ExceptionLogFileUtil;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

/*
 * CommonExceptionInterceptor.java
 * 
 * Created on Jul 3, 2008 11:29:41 AM
 * 
 * 对抛出的异常进行统一处理
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class AppExceptionInterceptor implements Interceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(this.getClass());

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		String next="error_500";
		try{
			next=invocation.invoke();
		}catch(Exception e){
			//System.out.println(e);
			String name=e.toString();
			String location=e.getLocalizedMessage();
			String cause="";
			if(e.getCause()!=null){
				cause=e.getCause().toString();
				if(e.getCause().getCause()!=null){
					cause=cause+" preCause:"+e.getCause().getCause().toString();
				}
			}
			
			String message=e.getMessage();
			StackTraceElement[] eles=e.getStackTrace();
			String stackTrace="";
			log.error(":::Exception Log Begin");
			log.error("name:"+name);
			//log.info(stackTrace);
			
			for(StackTraceElement element:eles){
				String lineStr=element.toString();
				if(lineStr.indexOf("com.ambow.trainingengine")>=0||lineStr.indexOf("com.ambow.studyflow")>=0){
					log.error(lineStr);
					stackTrace=stackTrace+lineStr+"  <br>  ";
				}
			}
			log.error(location);
			log.error(cause);
			log.error(message);
			log.error(":::Exception Log End");
			
			ActionContext actionContext=invocation.getInvocationContext();
			HttpServletRequest request = (HttpServletRequest) (Object) actionContext.get(WebWorkStatics.HTTP_REQUEST);
			
//			request.setAttribute("name",name);
//			request.setAttribute("location",location);
//			request.setAttribute("cause",cause);
//			request.setAttribute("message",message);
//			request.setAttribute("stackTrace",stackTrace);
			
			request.getSession().setAttribute("errorName",name);
			request.getSession().setAttribute("errorLocation",location);
			request.getSession().setAttribute("errorCause",cause);
			request.getSession().setAttribute("errorMessage",message);
			request.getSession().setAttribute("errorStackTrace",stackTrace);
			
			 //记录日志文件
			ExceptionLogFileUtil.printExceptionInFo(request, name, stackTrace);
			//发送email
			//this.sendMail(request,name, stackTrace);
		}
		return next;
	}
	
	public void sendMail(HttpServletRequest request,String name,String stackTrace){
		
		 //建立邮件消息
		  SimpleMailMessage mailMessage = new SimpleMailMessage();
		  //设置收件人，寄件人
		  String time=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		  mailMessage.setTo("ddwangw@163.com"); 		 
		  mailMessage.setFrom("ddwangw@163.com");
		  mailMessage.setSubject("etep系统Exception！");
		  StringBuffer strbuff=new StringBuffer();
		  strbuff.append("["+time+"] ["+request.getRemoteAddr()+"]\n");
		  Webuser webuser=(Webuser)request.getSession().getAttribute(SessionDict.WebUser);
		  if(webuser!=null)
				strbuff.append("[用户ID] "+webuser.getId()+"\n");
		  UserDataVO userData=(UserDataVO)request.getSession().getAttribute(SessionDict.UserData);
		  if(userData!=null)
				strbuff.append("[用户流程ID] "+userData.getRefID()+"\n");
		  strbuff.append(name+"\n"+stackTrace.replaceAll("<br>", "\n"));
		  mailMessage.setText(strbuff.toString());
		  //发送邮件		 
		  this.getMailSender().send(mailMessage);
		 
	}
	private JavaMailSenderImpl mailSender;
	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
}
