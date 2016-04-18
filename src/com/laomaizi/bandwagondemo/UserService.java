package com.laomaizi.bandwagondemo;

import java.io.InputStream;
import java.util.Map;

/**
 * 
 * @author Tony
 * @20160409
 * �û�ҵ���߼��Ľӿڣ�Ȼ��ʹ��userserviceimpl�����̳�����ӿڣ��������Է����ʵ������
 * �����Զ��岻ͬ��userservice�������̳�ͬһ��UserService�࣬��ʵ�ֲ�ͬ�Ľӿڣ�����
 *  UserService userservice = new UserServiceImpl();
 *  UserService userserviceanother = new UserServiceAnotherImpl();
 *  
 *
 */
public interface UserService {
	public void userLogin(String loginName, String loginPassword)
			throws Exception;

	public void userRegister(String regName, String regPassword, String regEmail)
			throws Exception;
	
	public String userPortraitUpload(InputStream in,Map<String, String> data, String pathName ) 
			throws Exception;


}
