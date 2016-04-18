package com.laomaizi.bandwagondemo;

/**
 * 
 * @author Tony
 * UserService�ӿڵ�ʵ���࣬ʵ����UserService�ľ���ҵ��
 * ������Ҫ�ǵ�¼��ע��ҵ��
 * ʹ��mysql���ݿ���ʵ�ֵ�¼��ע��
 * conn.iniΪ���ݿ������ļ���ʹ���и������ݿ���Զ��vps�ϻ��Ǳ��أ�
 * ��Ҫ���벻ͬ���ļ�
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.util.Log;
import com.laomaizi.bandwagondemo.ServiceRulesException;


public class UserServiceImpl implements UserService {
	protected static final String TAG = "BandWagon";
	private static final boolean ISREMOTE = true;
	@Override
	public void userLogin(String loginName, String loginPassword)
			throws Exception {

		URL url = null;
		HttpURLConnection urlConnection = null;

		Thread.sleep(500);

		Log.d(TAG, "====��ʼ��URL");
		if(ISREMOTE){
			
			url = new URL("http://104.224.144.115/tony/login.do");
		}
		else {
			
			url = new URL("http://192.168.43.239:8080/Tony_Bandwagon_Server/login.do");
		}
		urlConnection = (HttpURLConnection) url.openConnection();
		// ��������ĳ�ʱʱ��
		urlConnection.setConnectTimeout(3000);
		// ������Ӧ�ĳ�ʱʱ��
		urlConnection.setReadTimeout(3000);
		 // ���������ͷ  
        urlConnection.setRequestProperty("Connection", "keep-alive");  
        // ���������ͷ  
        urlConnection.setRequestProperty("Content-Type",  
                "application/x-www-form-urlencoded");  
        // ���������ͷ  
        String content = "name="+ URLEncoder.encode(loginName, "UTF-8") +
        		"&password="+URLEncoder.encode(loginPassword, "UTF-8");
        Log.d(TAG, "=========="+content+"------------");
        urlConnection.setRequestProperty("Content-Length",  
                String.valueOf(content.getBytes().length));  
        // ���������ͷ  
        urlConnection  
                .setRequestProperty("User-Agent",  
                        "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");  

		// ������
		urlConnection.setDoInput(true);
		// д����
		urlConnection.setDoOutput(true);
		// ��������Ϊpost����
		urlConnection.setRequestMethod("POST");
		// Post������ʹ�û���
		urlConnection.setUseCaches(false);

		urlConnection.connect();
		Log.d(TAG, "====�������======");

		OutputStream out = urlConnection.getOutputStream();
		//out = (DataOutputStream) urlConnection.getOutputStream();
		Log.d(TAG, "====��ʼ��outputstream======");


		out.write(content.getBytes());
		out.flush();
	
		if (urlConnection.getResponseCode()!= HttpURLConnection.HTTP_OK) {
			throw new ServiceRulesException(LoginActivity.MSG_SERVER_ERROR);
			
		}
        if (urlConnection.getResponseCode() == 200) {  
     
       // ��ȡ��Ӧ������������  
            InputStream is = urlConnection.getInputStream();  
            // �����ֽ����������  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            // �����ȡ�ĳ���  
            int len = 0;  
            // ���建����  
            byte buffer[] = new byte[1024];  
            // ���ջ������Ĵ�С��ѭ����ȡ  
            while ((len = is.read(buffer)) != -1) {  
                // ���ݶ�ȡ�ĳ���д�뵽os������  
                baos.write(buffer, 0, len);  
            }  
            // �ͷ���Դ  
            is.close();  
            baos.close();  
            // �����ַ���  
            final String result = new String(baos.toByteArray());  

    		if (result.equals("��¼�ɹ�")) {
    			
    			System.out.println("success");

    		} else {
    			System.out.println("failed");
    			throw new ServiceRulesException(LoginActivity.MSG_LOGIN_ERROR);
    		}

     

        } else {  
            System.out.println("����ʧ��.........");  
        } 
		
		
		if (out != null) {
			out.close();
		}
		if (urlConnection != null) {
			urlConnection.disconnect();
		}
	}
	@Override
	public void userRegister(String regName, String regPassword, String regEmail)
			throws Exception {
		URL url = null;
		HttpURLConnection urlConnection = null;

		Thread.sleep(500);

		if(ISREMOTE){
			
			url = new URL("http://104.224.144.115/tony/register.do");
		}
		else {
			
			url = new URL("http://192.168.43.239:8080/Tony_Bandwagon_Server/register.do");
		}


		Log.d(TAG, "====��URL���======");
		urlConnection = (HttpURLConnection) url.openConnection();
		// ��������ĳ�ʱʱ��
		urlConnection.setConnectTimeout(3000);
		// ������Ӧ�ĳ�ʱʱ��
		urlConnection.setReadTimeout(3000);
		 // ���������ͷ  
        urlConnection.setRequestProperty("Connection", "keep-alive");  
        // ���������ͷ  
        urlConnection.setRequestProperty("Content-Type",  
                "application/x-www-form-urlencoded");  
        // ���������ͷ  
        String content = "name="+ URLEncoder.encode(regName, "UTF-8") +
        		"&password="+URLEncoder.encode(regPassword, "UTF-8")+
        		"&email="+URLEncoder.encode(regEmail, "UTF-8");
        Log.d(TAG, "=========="+content+"------------");
        urlConnection.setRequestProperty("Content-Length",  
                String.valueOf(content.getBytes().length));  
        // ���������ͷ  
        urlConnection  
                .setRequestProperty("User-Agent",  
                        "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");  

		// ������
		urlConnection.setDoInput(true);
		// д����
		urlConnection.setDoOutput(true);
		// ��������Ϊpost����
		urlConnection.setRequestMethod("POST");
		// Post������ʹ�û���
		urlConnection.setUseCaches(false);

		urlConnection.connect();
		Log.d(TAG, "====�������======");

		OutputStream out = urlConnection.getOutputStream();
		//out = (DataOutputStream) urlConnection.getOutputStream();
		Log.d(TAG, "====��ʼ��outputstream======");


		out.write(content.getBytes());
		out.flush();
	
		if (urlConnection.getResponseCode()!= HttpURLConnection.HTTP_OK) {
			throw new ServiceRulesException(LoginActivity.MSG_SERVER_ERROR);
			
		}
        if (urlConnection.getResponseCode() == 200) {  
     
       // ��ȡ��Ӧ������������  
            InputStream is = urlConnection.getInputStream();  
            // �����ֽ����������  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            // �����ȡ�ĳ���  
            int len = 0;  
            // ���建����  
            byte buffer[] = new byte[1024];  
            // ���ջ������Ĵ�С��ѭ����ȡ  
            while ((len = is.read(buffer)) != -1) {  
                // ���ݶ�ȡ�ĳ���д�뵽os������  
                baos.write(buffer, 0, len);  
            }  
            // �ͷ���Դ  
            is.close();  
            baos.close();  
            // �����ַ���  
            final String result = new String(baos.toByteArray());  

    		if (result.equals("ע���û��ɹ�")) {
    			
    			System.out.println("success");

    		} else {
    			System.out.println("failed");
    			throw new ServiceRulesException(LoginActivity.MSG_LOGIN_ERROR);
    		}

     

        } else {  
            System.out.println("����ʧ��.........");  
        } 
		
		
		if (out != null) {
			out.close();
		}
		if (urlConnection != null) {
			urlConnection.disconnect();
		}
		
	}
	@Override
	//�û�ͷ���ϴ�����@20160417
	public String userPortraitUpload(InputStream in, Map<String, String> data,String pathName) throws Exception {
		// TODO Auto-generated method stub
				HttpClient client = new DefaultHttpClient();
				URL url = null;

				if(ISREMOTE){
					
					url = new URL("http://104.224.144.115/tony/uploadportrait.do");
				}
				else {
					
					url = new URL("http://192.168.43.239:8080/Tony_Bandwagon_Server/uploadportrait.do");
				}

				HttpPost post = new HttpPost(url.toString());
				// Ҫ�����ݷ�װ��post��

				MultipartEntityBuilder builder = MultipartEntityBuilder.create();

				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.setCharset(Charset.forName("UTF-8"));
				for (Map.Entry<String, String> entry : data.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					builder.addPart(key, new StringBody(value, ContentType.TEXT_PLAIN));
				}

				File newFile = new File(pathName);
				FileBody fileBody = new FileBody(newFile);
				builder.addPart("image", fileBody);
				HttpEntity httpEntity = builder.build();

				post.setEntity(httpEntity);

				HttpResponse response = client.execute(post);

				int statuscode = response.getStatusLine().getStatusCode();

				if (statuscode != HttpStatus.SC_OK) {
					throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
				}

				String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

				return result;

		
	/*	Log.d(TAG, "��ʼͼƬ�ϴ�");
		URL url = null;
		HttpURLConnection urlConnection = null;
		String successString = null;
		if(ISREMOTE){
			url = new URL("http://104.224.144.115/tony/uploadportrait.do");
		}
		else {
			url = new URL("http://192.168.43.239:8080/Tony_Bandwagon_Server/uploadportrait.do");
		}

		Log.d(TAG, "ͼƬ�ϴ�urlΪ"+url.toString());
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(3000);
		urlConnection.setReadTimeout(3000);
         ����Input��Output����ʹ��Cache 
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
         ���ô��͵�method=POST 
        urlConnection.setRequestMethod("POST");
         setRequestProperty 
        urlConnection.setRequestProperty("Connection", "Keep-Alive");
        urlConnection.setRequestProperty("Charset", "UTF-8");
       // File file = new File(imgPath);
        //======================
        urlConnection.setRequestProperty("Content-Type",
                           "multipart/form-data; boundary=--------------laomaizi");
        OutputStream out = new DataOutputStream(urlConnection.getOutputStream());

        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
        	out.write(bufferOut, 0, bytes);
        }
        in.close();
        out.flush();
        //����ļ����ϴ�
		if (urlConnection.getResponseCode()!= HttpURLConnection.HTTP_OK) {
			throw new ServiceRulesException(UserSettingActivity.MSG_SERVER_ERROR);
			
		}
        if (urlConnection.getResponseCode() == 200) {  
            InputStream is = urlConnection.getInputStream();  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            int len = 0;  
            byte buffer[] = new byte[1024];  
            while ((len = is.read(buffer)) != -1) {  
                baos.write(buffer, 0, len);  
            }  
            is.close();  
            baos.close();  
            final String result = new String(baos.toByteArray());  

    		if (result.equals("�ϴ�ͼƬ�ɹ�")) {
    			
    			System.out.println("success");
    			successString= "�ϴ�ͼƬ�ɹ�";


    		} else {
    			System.out.println("failed");
    			successString= "�ϴ�ͼƬʧ��";
    			throw new ServiceRulesException(UserSettingActivity.MSG_UPLOAD_USER_PORTRAIT_ERROR);
    		}

        } else {  
            System.out.println("����ʧ��.........");  
        } 
		
		
		if (out != null) {
			out.close();
		}
		if (urlConnection != null) {
			urlConnection.disconnect();
		}

		return successString;
		// TODO Auto-generated method stub
		
*/	}
}
