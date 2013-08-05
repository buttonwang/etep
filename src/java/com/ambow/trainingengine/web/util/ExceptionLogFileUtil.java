package com.ambow.trainingengine.web.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

public class ExceptionLogFileUtil {

	public static final String url="/log/";
	
	@SuppressWarnings("deprecation")
	public static void printExceptionInFo(HttpServletRequest request,String name,String stackTrace){
		String dir=request.getRealPath(url);
		Date date=new Date();
		String fileName=new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
		File file=new File(dir,fileName+".txt");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
		
			String time=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);			
			pw.append("["+time+"]  ["+request.getRemoteAddr()+"]\n");			
			Webuser webuser=(Webuser)request.getSession().getAttribute(SessionDict.WebUser);
			if(webuser!=null)
				pw.append("[用户ID] "+webuser.getId()+"\n");
			UserDataVO userData=(UserDataVO)request.getSession().getAttribute(SessionDict.UserData);
			if(userData!=null)
				pw.append("[用户流程ID] "+userData.getRefID()+"\n");
			pw.append(name+"\n");
			pw.append(stackTrace.replaceAll("<br>", "\n")+"\n");
			pw.append("\n");
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
