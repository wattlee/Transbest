package com.baconpotato.limingzen.httpget;

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

public class list5Activity extends AppCompatActivity {
    private ListView mlistView4;
    private ListView mlistView5;
    private String address;
    private String address2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list5);
        final Data app = (Data)getApplication();
        address="http://apis.haoservice.com/lifeservice/train/ypcx?date="+app.theDate+"&from="+app.start+"&to="+app.start2+"&key=dd1fd60108d44e8aa50bd6601a106bbb";
        address2="http://apis.haoservice.com/lifeservice/train/ypcx?date="+app.theDate+"&from="+app.start2+"&to="+app.end+"&key=dd1fd60108d44e8aa50bd6601a106bbb";
        mlistView4=(ListView)findViewById(R.id.lv_main4);
        mlistView5=(ListView)findViewById(R.id.lv_main5);
        new trainsAsyncTask().execute(address);
        new trainsAsyncTask2().execute(address2);

    }
    private List<trainsBean> getjsondata(String url) {
        List<trainsBean> transBeanlist = new ArrayList<>();
        try {
            String jsonstring = readStream(new URL(url).openStream());
            System.out.println(jsonstring);
//            Log.d("TESTS", jsonstring);
            JSONObject jsonObject;
            trainsBean trainsbean;
            jsonObject = new JSONObject(jsonstring);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                trainsbean = new trainsBean();
                trainsbean.trainnumber = jsonObject.getString("station_train_code");
                trainsbean.starttime = jsonObject.getString("start_time");
                trainsbean.arrivaltime = jsonObject.getString("arrive_time");
                trainsbean.lasttime = jsonObject.getString("lishi");
                trainsbean.startplace = jsonObject.getString("from_station_name");
                trainsbean.endplace = jsonObject.getString("to_station_name");
                trainsbean.ticketsnum = jsonObject.getString("ze_num");
                if (trainsbean.ticketsnum.contains("--")) {
                    trainsbean.ticketsnum = jsonObject.getString("yz_num");
                    trainsbean.seattype = "硬座";
                } else {
                    trainsbean.ticketsnum = jsonObject.getString("ze_num");
                    trainsbean.seattype = "二等座";
                }
                transBeanlist.add(trainsbean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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
    class trainsAsyncTask extends AsyncTask<String,Void,List<trainsBean>> {

        @Override
        protected List<trainsBean> doInBackground(String... params) {
            return getjsondata(params[0]);
        }

        @Override
        protected void onPostExecute(List<trainsBean> trainsBean) {
            super.onPostExecute(trainsBean);
            trainsAdapter adapter = new trainsAdapter(list5Activity.this, trainsBean);
            mlistView4.setAdapter(adapter);
//            trainsAdapter adapter2 =new trainsAdapter(list5Activity.this,trainsBeans);
//            mlistView5.setAdapter(adapter2);
        }
    }

    class trainsAsyncTask2 extends AsyncTask<String, Void, List<trainsBean>> {

            @Override
            protected List<trainsBean> doInBackground(String... params) {
                return getjsondata(params[0]);
            }

            @Override
            protected void onPostExecute(List<trainsBean> trainsBeans) {
                super.onPostExecute(trainsBeans);
                trainsAdapter adapter2 = new trainsAdapter(list5Activity.this, trainsBeans);
                mlistView5.setAdapter(adapter2);
            }
    }

}
