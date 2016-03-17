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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listActivity extends AppCompatActivity {
private ListView mlistView;
public String tid;
    public String address;
    int j=0;
    boolean z=true;
    private Data app;
    private int K = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        app = (Data)getApplication();
        System.out.println(app.start);
        System.out.println(app.end);
        System.out.println(app.theDate);
        address="http://apis.juhe.cn/train/yp?key=0e075d561520f869209e100bdc1e7d83&dtype=json&from="+app.start+"&to="+app.end+"&date="+app.theDate+"&tt="+app.type;
        mlistView=(ListView)findViewById(R.id.lv_main);

        new trainsAsyncTask().execute(address);
    }
    private List<trainsBean> getjsondata(String url){
//        final Data app = (Data)getApplication();
        List<trainsBean> transBeanlist = new ArrayList<>();
        try{
        String jsonstring=readStream(new URL(url).openStream());
            JSONObject jsonObject;
            trainsBean trainsbean;
            jsonObject=new JSONObject(jsonstring);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            jsonArray.length();
            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                trainsbean =new trainsBean();
                trainsbean.trainnumber=jsonObject.getString("train_no");
                trainsbean.starttime=jsonObject.getString("start_time");
                trainsbean.arrivaltime=jsonObject.getString("arrive_time");
                trainsbean.lasttime=jsonObject.getString("lishi");
                trainsbean.startplace=jsonObject.getString("from_station_name");
                trainsbean.endplace=jsonObject.getString("to_station_name");
                trainsbean.ticketsnum=jsonObject.getString("ze_num");
                if (trainsbean.ticketsnum.contains("--"))
                {trainsbean.ticketsnum=jsonObject.getString("yz_num");trainsbean.seattype="硬座";}
                else{trainsbean.ticketsnum=jsonObject.getString("ze_num");trainsbean.seattype="二等座";}
                transBeanlist.add(trainsbean);

                app.trainnum=new String[jsonArray.length()];
                app.trainnum[i]=trainsbean.trainnumber;
                z=z && trainsbean.ticketsnum.contains("无");
            }

        }
        catch (IOException e) {
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

    class trainsAsyncTask extends AsyncTask<String,Void,List<trainsBean>>{

        @Override
        protected List<trainsBean> doInBackground(String... params) {
            return getjsondata(params[0]);
        }
        @Override
        protected void onPostExecute(List<trainsBean> trainsBeans){
            super.onPostExecute(trainsBeans);

            if(z) {
                for (int k = 0; k < 5; k++) {
                    K = k;
                    new AsyncTask<String, Void, Void>() {
                        @Override
                        protected Void doInBackground(String... params) {
                            getJsonData2(params[0], K);
                            return null;
                        }
                    }.execute("http://apis.juhe.cn/train/s?name=" + app.trainnum[k] + "&key=0e075d561520f869209e100bdc1e7d83");
                }
                Intent intent = new Intent();
                intent.setClass(listActivity.this, List3Activity.class);
                startActivity(intent);
            } else {
                trainsAdapter adapter = new trainsAdapter(listActivity.this, trainsBeans);
                mlistView.setAdapter(adapter);
            }
        }
    }

    private void getJsonData2(String url, int k) {
        try {
            String jsonstring1 = readStream(new URL(url).openStream());
            JSONObject jsonObject1;
            trainsBean trainsbean1;
            jsonObject1 = new JSONObject(jsonstring1);
            JSONArray jsonArray1 = jsonObject1.getJSONArray("station_list");
            for (j = 0; j < jsonArray1.length(); j++) {
                jsonObject1 = jsonArray1.getJSONObject(j);
                trainsbean1 = new trainsBean();
                trainsbean1.endplace = jsonObject1.getString("station_name");
                if (trainsbean1.endplace.contains(app.end) && j < jsonArray1.length() - 1) {
                    jsonObject1 = jsonArray1.getJSONObject(j+1);
                    trainsbean1 = new trainsBean();
                    trainsbean1.endplace = jsonObject1.getString("station_name");
                    app.stationname = new String[30];
                    app.stationname[k] = trainsbean1.endplace;
                    final Map<String,String> map2 = new HashMap<String,String>();
                    map2.put(app.stationname[k], app.trainnum[k]);
                    break;
                }else if(trainsbean1.endplace.contains(app.end) && j == jsonArray1.length() - 1){break;}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
