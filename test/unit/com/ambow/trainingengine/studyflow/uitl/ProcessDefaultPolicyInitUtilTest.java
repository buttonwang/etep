package com.ambow.trainingengine.studyflow.uitl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Date;

import testing.Zhu_BaseTest;
import testing.Zhu_NodeToJSONStr;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.studyflow.util.ProcessDefaultPolicyInitUtil;

public class ProcessDefaultPolicyInitUtilTest extends Zhu_BaseTest{
	public static void main(String[] args) {		 
		//test ();
		//xx_test_xx();
		//replaceMathText();
		setDefaultPolicyToProcess();
	 
		 
	}
	public static void setDefaultPolicyToProcess(){
		 
		// 决胜09中考物理，14 复制报错    			51 复制不对
		// 决胜09高考物理，15 复制报错     			52 复制不对     57
		// 决胜09高考数学(大纲版理科)，    			19  复制通过 	53 复制不对
		// 决胜09中考数学，20 复制通过                		54  可以跑
		// 决胜09高考化学，33 复制通过，不能发布      	55  可以跑
		 Zhu_NodeToJSONStr z = new Zhu_NodeToJSONStr();
		 z.setUp();
		 HibernateGenericDao dao =  z.getGenService();
		
		 long processIdArr[] = {66};//64 65	66	 
		 for (long l : processIdArr) {
			 StringBuilder sb = new StringBuilder();
			 sb.append("------------------------------- start -------------------------------\n");
			 long processId = l;
			 sb.append("\n\n");
			 Date start = new Date ();
			 ProcessDefaultPolicyInitUtil.setDefaultPolicyToProcess(processId,dao,sb);
			 Date end = new Date();
			 System.out.println(processId+" success 用时:"+(start.getTime ()-end.getTime ())+"ms。  |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
			 sb.append("\n------------------------------- end -------------------------------");
			 try {
				 File f = new File("c:\\process\\process_"+processId+".txt","rw");//创建第一个目标文件
		         BufferedWriter bf = new BufferedWriter(new FileWriter(f));//创建第一个目标文件读取器
		         FileOutputStream out =  new FileOutputStream(f) ;
		         out.write(sb.toString().getBytes());
				System.out.println("success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("----------");
				e.printStackTrace();
			}
		}
		
		
		System.out.println("success!!!");
	}
	
	}
