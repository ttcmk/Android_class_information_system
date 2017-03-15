package com.example.popupmenutest;

import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class ProvinceAsyncTask extends AsyncTask<String, String, List<String>> {
	private MainActivity2 activity;
	private List<String> provinces;
//	private TextView sp_province;
	SQLfreeclassroom sqlclassroom;
	FreeclassroomModel classroommodel;
	
	public ProvinceAsyncTask (MainActivity2 activity,SQLfreeclassroom sql,FreeclassroomModel model){
		this.activity = activity;
//		this.sp_province = sp_province;
		sqlclassroom=sql;
		classroommodel=model;
	}

	@Override
	protected List<String> doInBackground(String... params) { 
		// TODO Auto-generated method stub
		provinces = WebServiceUtil.selectAllLectureInfor();
		return null;
	}

	@Override
	protected void onPostExecute(List<String> result) {
		// TODO Auto-generated method stub
//		sp_province.setText(provinces.toString());
		String[] end=provinces.toString().split(",");	
		String[][] msg=new String[end.length/14][14];
		end[0]=end[0].substring(1);
		end[end.length-1]=end[end.length-1].substring(0,end[end.length-1].indexOf("]"));
					
		for(int i=0;i<end.length/14;i++)
			for(int j=0;j<14;j++){
				msg[i][j]=end[i*14+j].trim();			    
			}

		sqlclassroom=new SQLfreeclassroom(activity);
		classroommodel=new FreeclassroomModel();
		

		for(int i=0;i<msg.length;i++){
			if (msg[i][13].equalsIgnoreCase("0")){
				Log.i(MainActivity2.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+i+" "+msg[i][1]+" "+msg[i][2]+" "+msg[i][8]);
				classroommodel.setId(""+i);
				classroommodel.setNum(msg[i][1]);
				classroommodel.setLouhao(msg[i][2]);
				classroommodel.setXingqi( Integer.valueOf(msg[i][3]).intValue());
				classroommodel.setJie(Integer.valueOf(msg[i][4]).intValue());
				classroommodel.setDanshuang(Integer.valueOf(msg[i][5]).intValue());
				classroommodel.setBegin(Integer.valueOf(msg[i][6]).intValue());
				classroommodel.setEnd(Integer.valueOf(msg[i][7]).intValue());
				classroommodel.setKecheng(msg[i][8]);
				classroommodel.setTeacher(msg[i][9]);
				classroommodel.setBiaozhi(Integer.valueOf(msg[i][13]).intValue());
				sqlclassroom.create(classroommodel);

			}
			else{
				Log.i(MainActivity2.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+i+" "+msg[i][1]+" "+msg[i][2]+" "+msg[i][8]);
				classroommodel.setId(""+i);
				classroommodel.setNum(msg[i][1]);
				classroommodel.setLouhao(msg[i][2]);
				classroommodel.setKecheng(msg[i][8]);
				classroommodel.setTeacher(msg[i][9]);
				classroommodel.setZhubanfang(msg[i][10]);
				classroommodel.setShijian(msg[i][11]);
				classroommodel.setMiaoshu(msg[i][12]);
				classroommodel.setBiaozhi(Integer.valueOf(msg[i][13]).intValue());
				sqlclassroom.create(classroommodel);
			}
		}

	}
}
