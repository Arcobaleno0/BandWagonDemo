package com.laomaizi.bandwagondemo.entity;

/**
 * 
 * @author Tony
 * @20160414
 * ��������࣬������¼��ǰ��¼���û��������룬�Ա����Ժ����
 * ��������������һ�����������û��������룬һ��������ȡ�û���
 */
import android.util.Log;

public class UserInfomation {
	
	private static final String TAG = "BandWagon";
	static String userNameString = null;
	static String userPasswordString = null;

	public static void setUserInfomation(String userName,String password) {
		
		Log.d(TAG,"��UserInformation�н�д���û���" + userName);
		userNameString = userName;
		userPasswordString = password;
		Log.d(TAG, "��UserInformation��д���û���" + userNameString);	
		
	}
	public static String getUserName()
	{
		return userNameString;
		
	}
	
}
