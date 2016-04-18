package com.laomaizi.bandwagondemo;
/**
 * @author Tony
 * �������logoҳ��ʹ��handler��postdelay���ܣ�
 * ִ��һ��Runnable�̣߳�������LoginActivity
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartupActivity extends Activity {

	private static final long LOAD_DIS_TIME = 800;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent myintent = new Intent(StartupActivity.this, LoginActivity.class) ;
				
				StartupActivity.this.startActivity(myintent);
				StartupActivity.this.finish();
			}
		}, LOAD_DIS_TIME);
	}

	
}
