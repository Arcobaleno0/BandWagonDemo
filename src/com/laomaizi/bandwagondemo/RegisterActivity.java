package com.laomaizi.bandwagondemo;
/**
 * @author Tony
 * 20160409
 * ע��activity������name passwoed��email��������
 * email��������������ʽУ��
 * ͬ����ע��ľ���ҵ��Ҳ��userservice��ʵ��
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	protected static final String TAG = "BandWagon";
	public static final String MSG_LOGIN_ERROR = "��¼����";
	public static final String MSG_SERVER_ERROR = "�������������";
	private static ProgressDialog dialog;
	private EditText txtLoginName;
	private EditText txtPassword;
	private EditText txtEmailaddress;
	private EditText txtRepeatPassword;
	private Button regButton;
	private  String strLoginName= null;
	private String strPassword = null;
	private String strRepeatPassword = null;
	private String strEmail =null;
	private UserService userService = new UserServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.init();
		regButton.setOnClickListener(new OnClickListener() {
			
	
				public void onClick(View arg0) {
					strLoginName = txtLoginName.getText().toString();
					strPassword = txtPassword.getText().toString();
					strRepeatPassword = txtRepeatPassword.getText().toString();
					strEmail = txtEmailaddress.getText().toString();
					
					if (validateinput(strLoginName,strPassword,strEmail) == true)
					{
					
					
					//�ȵ����ȴ�����
					if (dialog == null) {
						dialog = new ProgressDialog(RegisterActivity.this);
					}
					dialog.setCancelable(false);
					dialog.setTitle("Waiting");
					dialog.setMessage("ע����... ...");
					dialog.show();
					
					//������¼�߳�
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								Log.d(TAG, "=============��ʼע���߳�=============");
								
								userService.userRegister(strLoginName, strPassword,strEmail);
								dialog.dismiss();
								runOnUiThread(new Runnable() {
									public void run() {
										
										Toast.makeText(RegisterActivity.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
									}
								});
								
								Log.d(TAG, "=============ע���߳̽���=============");
								
							}	catch(ServiceRulesException e){
		//						e.printStackTrace();
								if (e.getMessage().equals(MSG_LOGIN_ERROR)) {
									
									runOnUiThread(new Runnable() {
										public void run() {
											dialog.dismiss();
											
											Toast.makeText(RegisterActivity.this, "ע��ʧ��", Toast.LENGTH_SHORT).show();
										}
									});
								
									
								}
							if (e.getMessage().equals(MSG_SERVER_ERROR)) {
									
									runOnUiThread(new Runnable() {
										public void run() {
											dialog.dismiss();
											
											Toast.makeText(RegisterActivity.this, "�������������", Toast.LENGTH_SHORT).show();
										}
									});
								
									
								}
								
							}						
						 
							
							catch (Exception e) {
								e.printStackTrace();
							}
							
							
						}
					}).start();
				}
				
			}
		});
	}

	protected boolean validateinput(String strLoginName, String strPassword,
			String strEmail) {

		String regEx =  
			    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"  
			        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"  
			        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."  
			        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"  
			        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"  
			        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";  
		String strEmailTrim = strEmail.trim();
	    Matcher matcherObj = Pattern.compile(regEx).matcher(strEmailTrim);  
		if ((strPassword.equals(strRepeatPassword))&& (matcherObj.matches())){
			Toast.makeText(RegisterActivity.this, "����У��ɹ�", Toast.LENGTH_SHORT).show();
			return true;
			
		}
		else{
			Toast.makeText(RegisterActivity.this, "����У��������������ַ��", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	private void init() {
		regButton = (Button) this.findViewById(R.id.btn_register);
		txtLoginName = (EditText) this.findViewById(R.id.register_name_edit);
		txtPassword = (EditText) this.findViewById(R.id.register_pwd_edit);
		txtRepeatPassword = (EditText) this.findViewById(R.id.repeat_register_pwd_edit);
		txtEmailaddress = (EditText) this.findViewById(R.id.register_email_edit);
		
			
	}

}
