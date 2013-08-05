/**
 * 
 */
package com.ambow.trainingengine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanjunqi
 *
 */
public class FileUtil {
	
	/**
	 * 读取文件内容
	 * 
	 * @param address
	 *            文件存放路径
	 * @param charsetName
	 *            编码格式
	 * @return 文件的内容
	 */
	public static String readInpuStream(InputStream in, String encoding)
			throws Exception {
		StringBuffer content = new StringBuffer();

		InputStreamReader reader = null;
		LineNumberReader linereader = null;

		if (encoding == null) {
			reader = new InputStreamReader(in);
		} else {
			reader = new InputStreamReader(in, encoding);
		}
		linereader = new LineNumberReader(reader);
		String line = null;
		while ((line = linereader.readLine()) != null) {
			content.append(line);
		}

		linereader.close();
		reader.close();

		return content.toString();
	}
	
	/**
	 * 完全删除一个目录
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public static boolean fullyDelete(File dir) throws IOException {
		if (dir.isFile()) {
			if (!dir.delete()) {
				return false;
			}
			return true;
		}
		File contents[] = dir.listFiles();
		if (contents != null) {
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()) {
					if (!contents[i].delete()) {
						return false;
					}
				} else {
					if (!fullyDelete(contents[i])) {
						return false;
					}
				}
			}
		}
		return dir.delete();
	}
	
	/**
	 * 读取文件内容
	 * 
	 * @param address
	 *            文件存放路径
	 * @param charsetName
	 *            编码格式
	 * @return 文件的内容
	 */
	public static List<String> readFile(File file, String charsetName)
			throws Exception {
		List<String> strList = new ArrayList<String>();

		InputStreamReader reader = null;
		LineNumberReader linereader = null;

		if (!file.exists()) {
			return null;
		}

		if (!file.isFile()) {
			return null;
		}

		if (charsetName == null) {
			reader = new InputStreamReader(new FileInputStream(file));
		} else {
			reader = new InputStreamReader(new FileInputStream(file),
					charsetName);
		}
		linereader = new LineNumberReader(reader);
		String line = null;
		while ((line = linereader.readLine()) != null) {
			strList.add(line);
		}

		linereader.close();
		reader.close();

		return strList;
	}
	/**
	 * 写入内容到文件
	 * 
	 * @param fileStr
	 * @param content
	 * @throws Exception
	 */
	public static void writeFile(String fileStr, String content)
			throws Exception {
		File file = new File(fileStr);
		mkDirs(file);
		OutputStreamWriter writer = null;
		writer = new OutputStreamWriter(new FileOutputStream(fileStr));
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 创建目录
	 * 
	 * @param file
	 * @return
	 */
	public static boolean mkDirs(File file) {
		boolean retFlag = true;

		if (file.isDirectory()) {
			if (!file.exists()) {
				retFlag = file.mkdirs();
			}

		} else {
			File parent = file.getParentFile();
			if (!parent.exists()) {
				retFlag = parent.mkdirs();
			}
		}
		return retFlag;
	}
	

	/**
	 * 获取目录以及子目录的所有文件
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public static List<File> getFileList(String address,String suffix) throws Exception {
		File file = new File(address);
		return getFileList(file,suffix);
	}
	
	/**
	 * 获取目录以及子目录的所有文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static List<File> getFileList(File file,String suffix) throws Exception {
		List<File> fileList = new ArrayList<File>();

		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String name = files[i].getName();
				if(suffix == null){
					if(name.indexOf(".")==-1){
						fileList.add(files[i]);
					}
				}else{
					if(name.indexOf("."+suffix)>-1){
						fileList.add(files[i]);
					}
				}
				
			} else {
				List<File> subFileList = getFileList(files[i],suffix);
				fileList.addAll(subFileList);
			}
		}
		return fileList;
	}
	
	/**
	 * 读取文件内容
	 * 
	 * @param address
	 *            文件存放路径
	 * @param charsetName
	 *            编码格式
	 * @return 文件的内容
	 */
	public static List<String> readFile(String address, String charsetName)
			throws Exception {
		
		File file = new File(address);
		List<String> strList = readFile(file, charsetName);

		return strList;
	}

	
	public static void main(String[] args) throws Exception{
		List<String[]> arrList = new ArrayList<String[]>();
		List<File> list = FileUtil.getFileList("D:\\data\\", null);
		//System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			//System.out.println(list.get(i).getParentFile().getName());
			List<String> strList = FileUtil.readFile(list.get(i), "utf-8");
			if(strList != null && strList.size()>0){
				//System.out.println(strList.size());
				//System.out.println(strList.get(0));
				for(int j=0;j<strList.size();j=j+2){
					String[] arr = new String[3];
					String str = strList.get(j);
					String[] strArr = str.split("	");
					String str1 = strList.get(j+1);
					String[] strArr1 = str1.split("	");
					//System.out.println(str);
					//System.out.println(strArr.length);

//					//System.out.println(str1);
//					//System.out.println(strArr1.length);
					arr[0] = strArr[0];
					arr[1] = strArr1[0];
					arr[2] = strArr[1];
					arrList.add(arr);
				}
			}
		}
		
		for(int ii=0;ii<arrList.size();ii++){
			String[] arr = arrList.get(ii);
			//System.out.println(arr[0]+" " +arr[1]+" "+arr[2]);
		}
		
	}
}
