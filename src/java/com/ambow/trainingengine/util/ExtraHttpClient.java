/**
 * 
 */
package com.ambow.trainingengine.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * @author yuanjunqi
 *
 */
public class ExtraHttpClient {
	private HttpClient httpClient = null;
	private HttpMethodBase httpMethod = null;

	public ExtraHttpClient(){
		httpClient = new HttpClient();
	}
	
	/**
	 * 建立连接
	 * @param url
	 * @return
	 */
	public boolean createConnection(String url,String type) {
		boolean retFlag = true;
		try {
			if(type == null){
				type = "get";
			}
			if(type.equals("get")){
				httpMethod = new GetMethod(url);
			}else if(type.equals("post")){
				httpMethod = new PostMethod(url);
			}else if(type.equals("head")){
				httpMethod = new HeadMethod(url);
			}else if(type.equals("options")){ 
				httpMethod = new OptionsMethod(url);
			}
			MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
			HttpConnectionManagerParams params = new HttpConnectionManagerParams();
			params.setMaxTotalConnections(20);
			// 延时时间设置为30秒
			int verupRetryInterval = 30 * 1000;
			// 设置HTTP连接延时
			params.setConnectionTimeout(verupRetryInterval);
			connectionManager.setParams(params);
			
			httpClient.setHttpConnectionManager(connectionManager);
			
//			HttpConnectionManager manager = httpClient
//					.getHttpConnectionManager();
//			HttpConnectionParams params = manager.getParams();
			


			// HTTP执行GET操作
			//int statusCode = 
			httpClient.executeMethod(httpMethod);

		} catch (IOException e) {
			retFlag = false;
		}
		return retFlag;
	}
	
	/**
	 * 获得针对由请求URL(request url)标志的资源在请求/应答的通信过程可以使用的功能选项。
	 * @param url
	 * @return
	 */
	public Enumeration getAllowedMethods(String url){
		Enumeration allowedMethods = null;
		boolean conn = this.createConnection(url, "options");
		if(conn){
			OptionsMethod options = (OptionsMethod)httpMethod;
			allowedMethods = options.getAllowedMethods();
			options.releaseConnection();
		}
		return allowedMethods;
	}
	
	/**
	 * 与Get方法完全一致，唯一的差别是服务器不能在应答包中包含主体(message-body)，而且一定不能包含主体。使用这个方法，可以使得客户无需将资源下载回就可就以得到一些关于它的基本信息。
	 * 这个方法常用来检查超链的可访问性以及资源最近有没有被修改。 
	 * @param url
	 * @return
	 */
	public Header[] getResponseHeaders(String url){
		boolean conn = this.createConnection(url, "head");
		Header[] headers = null;
		if(conn){
			headers = httpMethod.getResponseHeaders();
//			headers = httpMethod.getRequestHeaders();
//			try {
//				System.out.println(httpMethod.getURI().getDefaultDocumentCharset());
//			} catch (URIException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		return headers;
	}
	
	/**
	 * 获取回应的数据流
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public InputStream getResponseBodyAsStream(String url) throws IOException{
		InputStream in = null;
		boolean conn = this.createConnection(url,"get");
		if(conn){
			in = httpMethod.getResponseBodyAsStream();
		}
		return in;
	}
	
	/**
	 * 通过POST方式获取回应的数据流
	 * @param url
	 * @param userPair
	 * @param passPair
	 * @return
	 * @throws IOException
	 */
	public InputStream getResponseBodyAsStream(String url,String[] userPair,String[] passPair) throws IOException{
		InputStream in = null;
		boolean conn = this.createConnection(url,"post");
		PostMethod postMethod = (PostMethod)httpMethod;
		NameValuePair[] postData = new NameValuePair[2];   
		postData[0] = new NameValuePair(userPair[0], userPair[1]);   
		postData[1] = new NameValuePair(passPair[0], passPair[1]);
		postMethod.setRequestBody(postData);
//		postMethod.addParameters(postData);   
		if(conn){
			in = postMethod.getResponseBodyAsStream();
			postMethod.releaseConnection();
		}
		return in;
	}
	
	/**
	 * 设置代理主机和端口
	 * @param host
	 * @param port
	 */
	public void setProxy(String host, int port)
	{
		httpClient.getHostConfiguration().setProxy(host, port);

	}
	
	/**
	 * 释放连接
	 *
	 */
    private void releaseConnection()
    {
        // getMethod判断是否为空
        if (httpMethod != null)
        {

            // HTTP连接释放
        	httpMethod.releaseConnection();
        }

    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExtraHttpClient httpClient = new ExtraHttpClient();
		//httpClient.setProxy("11.111.46.200", 6789);

		try {
			long begin = System.currentTimeMillis();
			InputStream in = httpClient.getResponseBodyAsStream("http://124.42.8.101/red/portal/getUsersByTeacher.jsp?teacherID=000000017697");
			
			String content = FileUtil.readInpuStream(in, "GBK");
			System.out.println("aa="+content);
			httpClient.releaseConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

