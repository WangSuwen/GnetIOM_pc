package com.gnet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


/**
 * 描述：封装了一些采用HttpClient发送HTTP请求的方法
 */
public class HttpUtil {
	
	private HttpUtil(){}
	public static boolean send(String psReqURL,String message,String topic) {	
		boolean isSuccess=true;
	    BufferedReader reader = null;		
	    String result = null;		
	    StringBuffer sbf = new StringBuffer();	
	    String encode = java.net.URLEncoder.encode(message);
	    String httpUrl = psReqURL + topic + "&message=" + encode+"&timeout=500";
	    try {		
	        URL url = new URL(httpUrl);		
	        HttpURLConnection connection = (HttpURLConnection) url		
	                .openConnection();		
	        connection.setRequestMethod("POST");
	        connection.setReadTimeout(2000);
	        connection.connect();	
	        InputStream is = connection.getInputStream();		
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));		
	        String strRead = null;		
	        while ((strRead = reader.readLine()) != null) {		
	            sbf.append(strRead);		
	            sbf.append("\r\n");		
	        }		
	        reader.close();		
	        result = sbf.toString();
			JSONObject  resultJson = JSONObject.fromObject(result.toString());
			String resultString=resultJson.getString("RECODE");
			if ("1000".equals(resultString)||"1000"==resultString||resultString==null||resultString=="") {
				isSuccess=false;
			}
			return isSuccess;
	    } catch (Exception e) {		
	        e.printStackTrace();
			isSuccess=false;
			return isSuccess;
	    }		
	   // return result;		
	}
	
	/**
	 * 描述：发送HTTP_GET请求
	 * @see 该方法会自动关闭连接,释放资源
	 * 
	 * @param psReqURL
	 * 				请求地址(含参数)
	 * @param psDecodeCharset 
	 * 				解码字符集,解析响应数据时用之,默认采用UTF-8解码
	 * 
	 * @return 远程主机响应正文
	 */
	@SuppressWarnings({ "deprecation", "resource", "unused" })
	public static String sendGetRequest(String psReqURL){
		
		long responseLength = 0;       //响应长度
		String responseContent = null; //响应内容
		HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例
		HttpGet httpGet = new HttpGet(psReqURL);           //创建org.apache.http.client.methods.HttpGet
		try{
			HttpResponse response = httpClient.execute(httpGet); //执行GET请求
			HttpEntity entity = response.getEntity();            //获取响应实体
			if(null != entity){
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); //Consume response content
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(ParseException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源
		}
		return responseContent;
	}
	
	/**
	 * 描述：发送HTTP_GET请求
	 * @see 该方法会自动关闭连接,释放资源
	 * 
	 * @param psReqURL
	 * 				请求地址(含参数)
	 * @param psDecodeCharset 
	 * 				解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @return 远程主机响应正文
	 */
	@SuppressWarnings({ "deprecation", "resource", "unused" })
	public static String sendGetRequest(String psReqURL, String psDecodeCharset){
		long responseLength = 0;       //响应长度
		String responseContent = null; //响应内容
		HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例
		HttpGet httpGet = new HttpGet(psReqURL);           //创建org.apache.http.client.methods.HttpGet
		try{
			HttpResponse response = httpClient.execute(httpGet); //执行GET请求
			HttpEntity entity = response.getEntity();            //获取响应实体
			if(null != entity){
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, psDecodeCharset==null ? "UTF-8" : psDecodeCharset);
				EntityUtils.consume(entity); //Consume response content
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(ParseException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源
		}
		return responseContent;
	}
	
	
	/**
	 * 描述：发送HTTP_POST请求
	 * @see 该方法为<code>sendPostRequest(String,String,boolean,String,String)</code>的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 * @param isEncoder 用于指明请求数据是否需要UTF-8编码,true为需要
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder){
		return sendPostRequest(reqURL, sendData, isEncoder, null, null);
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param sendData      请求参数,若有多个参数则应拼接成param11=value11m22=value22m33=value33的形式后,传入该参数中
	 * @param isEncoder     请求数据是否需要encodeCharset编码,true为需要
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder, String encodeCharset, String decodeCharset){
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(reqURL);
		//httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
//		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try{
			if(isEncoder){
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for(String str : sendData.split("&")){
					formParams.add(new BasicNameValuePair(str.substring(0,str.indexOf("=")), str.substring(str.indexOf("=")+1)));
				}
				httpPost.setEntity(new StringEntity(URLEncodedUtils.format(formParams, encodeCharset==null ? "UTF-8" : encodeCharset)));
			}else{
				httpPost.setEntity(new StringEntity(URLEncoder.encode(sendData,"UTF-8")));
//				httpPost.setEntity(new StringEntity(sendData,"UTF-8"));
				
			}
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		}catch(Exception e){
			e.printStackTrace();
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param params        请求参数
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset){
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
		for(Map.Entry<String,String> entry : params.entrySet()){
			formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	
	/**
	 * 发送HTTPS_POST请求
	 * @see 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params){
		return sendPostSSLRequest(reqURL, params, null, null);
	}
	
	
	/**
	 * 发送HTTPS_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param params        请求参数
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset){
		String responseContent = "";
		HttpClient httpClient = new DefaultHttpClient();
		X509TrustManager xtm = new X509TrustManager(){
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() {return null;}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[]{xtm}, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			
			HttpPost httpPost = new HttpPost(reqURL);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			for(Map.Entry<String,String> entry : params.entrySet()){
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 若发送的<code>params</code>中含有中文,记得按照双方约定的字符集将中文<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL 请求地址
	 * @param params 发送到远程主机的正文数据,其数据类型为<code>java.util.Map<String, String></code>
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code>若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL, Map<String, String> params){
		StringBuilder sendData = new StringBuilder();
		for(Map.Entry<String, String> entry : params.entrySet()){
			sendData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		if(sendData.length() > 0){
			sendData.setLength(sendData.length() - 1); //删除最后一个&符号
		}
		return sendPostRequestByJava(reqURL, sendData.toString());
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 若发送的<code>sendData</code>中含有中文,记得按照双方约定的字符集将中文<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL   请求地址
	 * @param sendData 发送到远程主机的正文数据
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code>若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL, String sendData){
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; //写
		InputStream in = null;   //读
		int httpStatusCode = 0;  //远程主机响应的HTTP状态码
		try{
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);        //指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(30000); //30秒连接超时
			httpURLConnection.setReadTimeout(30000);    //30秒读取超时
			
			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes());
			
			//清空缓冲区,发送数据
			out.flush();
			
			//获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();
			
			//该方法只能获取到[HTTP/1.0 200 OK]中的[OK]
			//若对方响应的正文放在了返回报文的最后一行,则该方法获取不到正文,而只能获取到[OK],稍显遗憾
			//respData = httpURLConnection.getResponseMessage();
			
//			//处理返回结果
//			BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//			String row = null;
//			String respData = "";
//			if((row=br.readLine()) != null){ //readLine()方法在读到换行[\n]或回车[\r]时,即认为该行已终止
//				respData = row;              //HTTP协议POST方式的最后一行数据为正文数据
//			}
//			br.close();
			
			in = httpURLConnection.getInputStream();             
			byte[] byteDatas = new byte[in.available()];
			in.read(byteDatas);
			return new String(byteDatas) + "`" + httpStatusCode;
		}catch(Exception e){
			e.printStackTrace();
			return "Failed`" + httpStatusCode;
		}finally{
			if(out != null){
				try{
					out.close();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			if(in != null){
				try{
					in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(httpURLConnection != null){
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}
}