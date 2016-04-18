package com.laomaizi.bandwagondemo.lightpainting;

/**
 * 
 * @author Tony
 * �����ʷ��¼�б�
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;



import com.laomaizi.bandwagondemo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentFavList extends Fragment {


	private static final String TAG = "BandWagon";
	private List<FavItem> favlists = new ArrayList<FavItem>();
	private FavItemAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "��ʼ��ʼ��Favlist�б�");
		View contentView = inflater.inflate(R.layout.favlist, container, false);

		initFavLists();

		adapter = new FavItemAdapter(getActivity(), R.layout.favitem, favlists);
		ListView plistview = (ListView) contentView.findViewById(R.id.listview2);
		plistview.setAdapter(adapter);
		registerForContextMenu(plistview);
		
		Log.d(TAG, "��ʼ��Favlist�б����");
		
		plistview.setOnItemClickListener(new OnItemClickListener() {
		//���ĳһ����Ŀ��������Ϊ��
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FavItem f = adapter.getItem(position);
				Log.d(TAG,f.getText() + "was Clicked");
				int speed =50;
				
				int iedit = f.getText().length();
				if (iedit > 0) {
					speed = Integer.parseInt(f.getTime()) * 30 / iedit;
				} 
				
				Intent intent = new Intent(getActivity().getApplicationContext(), StartShowActivity.class);
				String text = f.getText();
				intent.putExtra("showtext", text);
				String color = f.getColor();
				intent.putExtra("color", color);
				String fontsize = f.getFontsize();
				intent.putExtra("fontsize", fontsize);
				intent.putExtra("speed", speed);

				int iWaittoBlank = iedit *  Integer.parseInt(f.getDelay())/ Integer.parseInt(f.getTime());
				intent.putExtra("stop", iWaittoBlank);
				
				startActivity(intent);
		
			}
		});

		//return null;
		Log.d(TAG, "׼������contentview");
		return contentView;
		}

	private void initFavLists() {
		//��ʼ����XML�ĵ�
		try {
			Log.d(TAG, "��ʼ�����ĵ�");
			XmlPullParserTest();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
/*		FavItem pItem1 = new FavItem("First", "#0000FF", "120", "5", "5", "1");
		favlists.add(pItem1);
*/

	}
	
	public List<FavItem> XmlPullParserTest() throws Exception {
		  
        //����һ��InputStreamͨ������õ���ǰ�۵Ķ���Ȼ��getClassLoader().getResourceAsStream();  
        
        String inputFileName = getActivity().getApplicationContext().getFilesDir().getAbsolutePath()+ "/fav/"+ "fav.xml";
        Log.d(TAG,inputFileName);
        File inputfile = new File(inputFileName);
        InputStream inStream = new FileInputStream(inputfile);
        
        //Xml.newPullParser()�õ�һ��XmlPullParser  
        XmlPullParser parser = Xml.newPullParser();  
        
        try {  
            parser.setInput(inStream, "UTF-8");  
            int eventType = parser.getEventType();  

            FavItem currentFav = null;  
           // List<FavItem> favlists = null;  

            while (eventType != XmlPullParser.END_DOCUMENT) {  
                        switch (eventType) {  
                        case XmlPullParser.START_DOCUMENT://�ĵ���ʼ�¼�,���Խ������ݳ�ʼ������  
                        	favlists = new ArrayList<FavItem>();  
                                    break;  

                         case XmlPullParser.START_TAG://��ʼԪ���¼�  
                                    String name = parser.getName();  
                                    if (name.equalsIgnoreCase("lightpainting")) {  
                                    	currentFav = new FavItem();  
                                    	//currentFav.setId(new Integer(parser.getAttributeValue(null, "id")));  
                                    } else if (currentFav != null) {  
                                                if (name.equalsIgnoreCase("text")) {  
                                                	currentFav.setText(parser.nextText());// ���������TextԪ��,����������ֵ  
                                                } else if (name.equalsIgnoreCase("fontsize")) {  
                                                	currentFav.setFontsize(parser.nextText());  
                                                }  
                                                else if (name.equalsIgnoreCase("time")) {  
                                                	currentFav.setTime(parser.nextText());  
                                                }  
                                                else if (name.equalsIgnoreCase("delay")) {  
                                                	currentFav.setDelay(parser.nextText());  
                                                }  
                                                else if (name.equalsIgnoreCase("color")) {  
                                                	currentFav.setColor(parser.nextText());  
                                                }  
                                    }  
                                    break;  

                        case XmlPullParser.END_TAG://����Ԫ���¼�  
                                    if (parser.getName().equalsIgnoreCase("lightpainting") && currentFav != null) { 
                                    	currentFav = new FavItem(currentFav.getText(),currentFav.getColor(),currentFav.getFontsize(),
                                    			currentFav.getTime(),currentFav.getDelay(),currentFav.getId())		;
                                    	favlists.add(currentFav);  
                                    	currentFav = null;  
                                    }  

                                    break;  
                        }  

                         eventType = parser.next();  
            }  


				
				} catch (Exception e) { 
					Log.d(TAG,e.toString());
				            e.printStackTrace();  
				           // return null;
				}  
        Log.d(TAG, "�������");
		inStream.close();  
		return favlists; 
                
            
    }  

	public void onClick(View v) {
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//ʹ�� adaptercontextmenuinfo����ȡ�˵���Ϣ
		
		String local_file = getActivity().getApplicationContext()
				.getFilesDir().getAbsolutePath()
				+ "/fav/"+ "fav.xml";
		File newXmlFile = new File(local_file);
		
		XmlSerializer serializer = Xml.newSerializer();
		FileOutputStream fileos = null;
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		int position = info.position;
		
		switch (item.getItemId()) {
		case R.id.delete_item:
			favlists.remove(position);
			adapter.notifyDataSetChanged();
				
			//������Ҫ��favlistsȫ����װ��ȥ
			
			try {
				fileos = new FileOutputStream(newXmlFile);
				serializer.setOutput(fileos, "UTF-8");
				serializer.startDocument(null, Boolean.valueOf(true));
				
				for(FavItem f:favlists){
					serializer.startTag(null, "lightpainting");
					serializer.attribute(null, "xmlns",
							"http://www.laomaizi.com");
					serializer.startTag(null, "text");
					serializer.text(f.getText());
					serializer.endTag(null, "text");
					serializer.startTag(null, "fontsize");
					serializer.text(f.getFontsize());
					serializer.endTag(null, "fontsize");
					serializer.startTag(null, "time");
					serializer.text(f.getTime());
					serializer.endTag(null, "time");
					serializer.startTag(null, "delay");
					serializer.text(f.getDelay());
					serializer.endTag(null, "delay");
					serializer.startTag(null, "color");
					serializer.text(f.getColor());
					serializer.endTag(null, "color");
					serializer.endTag(null, "lightpainting");
			}
				
				
				serializer.endDocument();
				serializer.flush();
				Log.d(TAG,"ɾ���б����ݣ�д����ļ����");
				fileos.close();
				
			} catch (FileNotFoundException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}

			
			
			break;

		case R.id.delete_item_all:
		
			try {
				fileos = new FileOutputStream(newXmlFile);
				serializer.setOutput(fileos, "UTF-8");
				serializer.startDocument(null, Boolean.valueOf(true));
				serializer.endDocument();
				serializer.flush();
				Log.d(TAG,"ɾ���б����ݣ�д����ļ����");
				fileos.close();
					
				favlists.removeAll(favlists);
				adapter.notifyDataSetChanged();
				
			} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
	
			
			
			break;
		}
		
		
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getActivity().getMenuInflater().inflate(R.menu.fav_item_list_context, menu);
		
	}

}
