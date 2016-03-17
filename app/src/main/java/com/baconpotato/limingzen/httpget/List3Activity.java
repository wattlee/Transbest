package com.baconpotato.limingzen.httpget;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class List3Activity extends AppCompatActivity {
    private ListView mlistView3;
    public int i= 0;
    public String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list3);
        final Data app = (Data)getApplication();

      address="http://apis.haoservice.com/lifeservice/train/ypcx?date="+app.theDate+"&from="+app.start+"&to="+app.trainnum[i]+"&key=dd1fd60108d44e8aa50bd6601a106bbb";
        mlistView3=(ListView)findViewById(R.id.lv_main3);

        new trainsAsyncTask().execute(address);
    }
    private List<trainsBean> getjsondata(String url) {
        final Data app = (Data) getApplication();
        List<trainsBean> transBeanlist = new ArrayList<>();
        for (i = 0; i < app.trainnum.length; i++) {
            new trainsAsyncTask().execute(address);
            try {
                String jsonstring = readStream(new URL(url).openStream());
                JSONObject jsonObject;
                trainsBean trainsbean;
                jsonObject = new JSONObject(jsonstring);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (int j = 0; j < jsonArray.length(); j++) {
                    jsonObject = jsonArray.getJSONObject(j);
                    trainsbean = new trainsBean();
                    trainsbean.trainnumber = jsonObject.getString("train_no");
                    if(trainsbean.trainnumber.contains(app.trainnum[i]))
                    {
                    trainsbean.starttime = jsonObject.getString("start_time");
                    trainsbean.arrivaltime = jsonObject.getString("arrive_time");
                    trainsbean.lasttime = jsonObject.getString("lishi");
                    trainsbean.startplace = jsonObject.getString("from_station_name");
                    trainsbean.endplace = jsonObject.getString("to_station_name");
                    trainsbean.ticketsnum = jsonObject.getString("ze_num");
                        trainsbean.latterGetOffStation=app.stationname[i]+"下车";
                    if (trainsbean.ticketsnum.contains("--")) {
                        trainsbean.ticketsnum = jsonObject.getString("yz_num");
                        trainsbean.seattype = "硬座";
                    } else {
                        trainsbean.ticketsnum = jsonObject.getString("ze_num");
                        trainsbean.seattype = "二等座";
                    }
                    transBeanlist.add(trainsbean);
                    }else{break;}
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return transBeanlist;

    }

    private String readStream(InputStream is){
        InputStreamReader isr;
        String result="";
        try{
            String line="";
            isr = new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            while((line=br.readLine())!=null)
            {result+=line;}
        }catch(UnsupportedEncodingException e){e.printStackTrace();} catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    class trainsAsyncTask extends AsyncTask<String,Void,List<trainsBean>>{

        @Override
        protected List<trainsBean> doInBackground(String... params) {
            return getjsondata(params[0]);
        }
        @Override
        protected void onPostExecute(List<trainsBean> trainsBeans){
            super.onPostExecute(trainsBeans);
            trainsAdapter adapter =new trainsAdapter(List3Activity.this,trainsBeans);
            mlistView3.setAdapter(adapter);
        }
    }
}
