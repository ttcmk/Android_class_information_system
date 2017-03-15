package com.example.popupmenutest;

	import java.util.ArrayList;
	import java.util.List;
	import android.os.AsyncTask;
	import android.util.Log;
	import android.widget.TextView;

	public class ProvinceAsyncTask2 extends AsyncTask<String, String, List<String>> {
		private Huodongjiangzuo activity;
		private List<String> provinces;
//		private TextView sp_province;
		SQLhuodong sqlhuodong;
		HuodongModel huodongmodel;
		
		public ProvinceAsyncTask2 (Huodongjiangzuo huodongjiangzuo,SQLhuodong sql,HuodongModel model){
			this.activity = huodongjiangzuo;
//			this.sp_province = sp_province;
			sqlhuodong=sql;
			huodongmodel=model;
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
//			sp_province.setText(provinces.toString());
			String[] end=provinces.toString().split(",");	
			String[][] msg=new String[end.length/14][14];
			end[0]=end[0].substring(1);
			end[end.length-1]=end[end.length-1].substring(0,end[end.length-1].indexOf("]"));
						
			for(int i=0;i<end.length/14;i++)
				for(int j=0;j<14;j++){
					msg[i][j]=end[i*14+j].trim();			    
				}

			sqlhuodong=new SQLhuodong(activity);
			huodongmodel=new HuodongModel();
			

			for(int i=0;i<msg.length;i++){
				if (msg[i][13].equalsIgnoreCase("0")){
//					Log.i(MainActivity.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+i+" "+msg[i][1]+" "+msg[i][2]+" "+msg[i][8]);
//					huodongmodel.setId(""+i);
//					huodongmodel.setNum(msg[i][1]);
//					huodongmodel.setLouhao(msg[i][2]);
//					huodongmodel.setXingqi( Integer.valueOf(msg[i][3]).intValue());
//					huodongmodel.setJie(Integer.valueOf(msg[i][4]).intValue());
//					huodongmodel.setDanshuang(Integer.valueOf(msg[i][5]).intValue());
//					huodongmodel.setBegin(Integer.valueOf(msg[i][6]).intValue());
//					huodongmodel.setEnd(Integer.valueOf(msg[i][7]).intValue());
//					huodongmodel.setKecheng(msg[i][8]);
//					huodongmodel.setTeacher(msg[i][9]);
//					huodongmodel.setBiaozhi(Integer.valueOf(msg[i][13]).intValue());
//					sqlhuodong.create(huodongmodel);

				}
				else{
					Log.i(MainActivity.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+i+" "+msg[i][1]+" "+msg[i][2]+" "+msg[i][8]);
					huodongmodel.setId(""+i);
					huodongmodel.setNum(msg[i][1]);
					huodongmodel.setLouhao(msg[i][2]);
					huodongmodel.setKecheng(msg[i][8]);
					huodongmodel.setTeacher(msg[i][9]);
					huodongmodel.setZhubanfang(msg[i][10]);
					huodongmodel.setShijian(msg[i][11]);
					huodongmodel.setMiaoshu(msg[i][12]);
					huodongmodel.setBiaozhi(Integer.valueOf(msg[i][13]).intValue());
					sqlhuodong.create(huodongmodel);
				}
			}

		}
	}
