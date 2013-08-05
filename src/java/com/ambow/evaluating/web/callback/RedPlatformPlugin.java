package com.ambow.evaluating.web.callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class RedPlatformPlugin
{
	static private String fixStart = "";
	static private String fixEnd = "";
	static final String LOG_ID = "_bp_log_id";
	static final String MODULE_ID = "_bp_module_id";
	static final String USER_ID = "_bp_user_id_in";

	private static Properties props = null;
	
	static
	{
		//String webServer = "10.10.10.115";
		//String webServer = "124.42.8.115";
		//String webServer = "www.ebopo.com";
		initConfig();
		String webServer = props.getProperty("webServer");
		if (webServer.trim().equals("")) {
			InetAddress address;
			try {
				address = InetAddress.getLocalHost();
				String IP = address.getHostAddress();
				webServer = IP;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		String startServletUrl = props.getProperty("startServletUrl");
		String endServletUrl = props.getProperty("endServletUrl");
		fixStart = "http://"+webServer+startServletUrl;
		fixEnd = "http://"+webServer+endServletUrl;
	}

	/**
	 * @param request
	 *            JSP中的request
	 * @param modelID
	 *            平台调用子系统的URL时传入的参数，应该已经记入Session中
	 * @param userIDPlatForm
	 *            当前用户在平台中的ID，平台调用子系统URL时传入的参数，应该已经记入了Session中，
	 * @param refID
	 *            modelID在子系统中对应的ID
	 * @param refDesc
	 *            由子系统提供的学习内容描述信息
	 * @param procRate
	 *            开始时，该模块的进度
	 * @param refItemID
	 *            子系统refID中，如果还有更细一级的ID，可以记入此处；否则，置为空串“”
	 * @param starQue 星级题数
	 * @param errQue  做错题数
	 * @param askQue  疑问题数
	 * @param totalRightRate 总正确率
	 * @param allKnows 积分 
	 * @param moduleType 只有7和8----7为开始学习，8为结束学习
	 *            
	 * @return
	 * @throws IOException
	 */
	public static boolean Start(HttpServletRequest request, String refID, String refItemDesc,
			int procRate, String refItemID,
			int starQue, int errQue, int askQue,
			int totalRightRate, int allKnows)
			throws IOException

	{
		StringBuffer startURL = new StringBuffer(fixStart);

		startURL.append("?refID=").append(refID)
				.append("&refItemDesc=").append(refItemDesc)
				.append("&procRate=").append(procRate)
				.append("&refItemID=").append(refItemID)
				.append("&starQue=").append(starQue)
				.append("&errQue=").append(errQue)
				.append("&askQue=").append(askQue)
				.append("&totalRightRate=").append(totalRightRate)
				.append("&allKnows=").append(allKnows)
				.append("&moduleType=").append(7);
		
		Object obj = request.getSession().getAttribute(MODULE_ID);
		if (obj == null) { return false; }
		startURL.append("&moduleID=" + obj.toString());
		obj = request.getSession().getAttribute(USER_ID);
		if (obj == null) { return false; }
		startURL.append("&userID=" + obj.toString());

		System.out.println("[RedPlatformPlugin][Start]=="+startURL.toString());
		// 结束TODO
		URL url = new URL(startURL.toString().replaceAll(" ","%20"));
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = br.readLine();
		if (request != null)
			request.getSession().setAttribute(LOG_ID, line);
		//CommonLog.log("[RedPlatformPlugin][Start]2=="+line);		
		br.close();
		is.close();
		return true;
	}

	/**
	 * @param request
	 * @param logID
	 *            执行Start()的URL时返回的数值，表示平台中的日志ID，应该已经记入Session中
	 * @param procRate
	 *            结束时，该模块的进度
	 * @param validSeconds
	 *            本模块的具体子模块有效学习时间，如果没有，写入-1
	 * @param testResult 本模块中如果有相关成绩，则记入成绩，否则，写入-1
	 * @return
	 * @throws IOException
	 */
	public static boolean End(HttpServletRequest request, int procRate, int validSeconds, int testResult,
			int starQue, int errQue, int askQue,
			int totalRightRate, int allKnows)
			throws IOException
	{
		if (request == null) { return false; }
		
		//补全URL
		StringBuffer endURL = new StringBuffer(fixEnd);
		
		
		Object obj = request.getSession().getAttribute(LOG_ID);
		if (obj == null) { return false; }
		endURL.append("?logID=" + obj.toString());
		// System.out.println(obj);

		endURL.append("&validSeconds=").append(validSeconds)
			  .append("&testResult=").append(testResult)
			  .append("&procRate=").append(procRate)
			  .append("&starQue=").append(starQue)
			  .append("&errQue=").append(errQue)
			  .append("&askQue=").append(askQue)
			  .append("&totalRightRate=").append(totalRightRate)
			  .append("&allKnows=").append(allKnows)
			  .append("&moduleType=").append(8);
		
		obj = request.getSession().getAttribute(MODULE_ID);
		if (obj == null) { return false; }
		endURL.append("&moduleID=" + obj.toString());
		obj = request.getSession().getAttribute(USER_ID);
		if (obj == null) { return false; }
		endURL.append("&userID=" + obj.toString());

		System.out.println("[RedPlatformPlugin][End]"+endURL.toString());		
		// 结束TODO
		URL url = new URL(endURL.toString());
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		br.close();
		is.close();
		
		//清除已用的日志ID
		request.getSession().setAttribute(LOG_ID,null);

		return true;
	}
	
	public static boolean callBack(HttpServletRequest request)
	{
		if(request==null)
			return false;
		
		Object obj = request.getParameter("moduleID");
		if (obj == null) { return false; }
		request.getSession().setAttribute(MODULE_ID,obj.toString());
		
		
		obj = request.getParameter("userID");
		if (obj == null) { return false; }
		request.getSession().setAttribute(USER_ID,obj.toString());
		
		return true;
	}

	
	public static String createModuleRUL(String url, String userID,String loginName , 
			String userName, String classCode, String moduleID, String refID){
		//模块的URL中，url,userID,refID,moduleID是必须填写，且不能为空的
		if(url==null || url.length()<1 || userID==null || userID.length()<1 || 
				refID==null || refID.length()<1 || moduleID==null || moduleID.length()<1 )
			return null;
		
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		
		//consider the url has some parameters already
		if(url.indexOf("?")!=-1){
			if(url.indexOf("=")!=-1)
				sb.append("&");
		}else
			sb.append("?");
		
		sb.append("userID=").append(userID)
		  .append("&loginName=").append(loginName)
		  .append("&userName=").append(userName)
		  .append("&classCode=").append(classCode)
		  .append("&moduleID=").append(moduleID)
		  .append("&refID=").append(refID);		
		
		return sb.toString();
	}
	
	public static void initConfig(){
		if(props == null){
			props = new Properties();
			ClassLoader loader = RedPlatformPlugin.class.getClassLoader();
			InputStream is = loader.getResourceAsStream("/conf/redConfig.properties");
			try{
				props.load(is);
			} catch (IOException e) {
				props = null;
				e.printStackTrace();
			}finally{
				try{
					if(is!=null) is.close();
				}catch(Exception ex){}
				is = null;				
			}
		}
	}
	
}