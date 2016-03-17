package com.baconpotato.limingzen.httpget;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class list2Activity extends AppCompatActivity {
    private ListView mlistView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);


        final Data app = (Data)getApplication();
        String address2="http://apis.haoservice.com/plan/InternationalFlightQueryByCity?dep="+app.fromplace+"&arr="+app.toplace+"&date="+app.date2+"&pageNum=1&key=e5f3caedbcf844c89ff37136d9384ee3";
        mlistView2=(ListView)findViewById(R.id.lv_main1);
System.out.println(app.fromplace);
        System.out.println(app.toplace);
        System.out.println(app.date2);
        new airsAsyncTask().execute(address2);
    }
    private List<airsBean> getjsondata(String url){
        List<airsBean> airsBeanlist = new ArrayList<>();
       Map<String,String> map1 = new HashMap<String,String>();
        map1.put("CA","国航");
        map1.put("MU","东方航空");
        map1.put("CZ","南方航空");
        map1.put("SZ","西南航空");
        map1.put("WH","西北航空");
        map1.put("CJ","北方航空");
        map1.put("F6","中国航空");
        map1.put("XO","新疆航空");
        map1.put("3Q","云南航空");
        map1.put("MF","厦门航空");
        map1.put("3U","四川航空");
        map1.put("FM","上海航空");
        map1.put("IJ","长城航空");
        map1.put("WU","武汉航空");
        map1.put("Z2","中原航空");
        map1.put("G4","贵州航空");
        map1.put("H4","海南航空");
        map1.put("X2","新华航空");
        map1.put("ZH","深圳航空");
        map1.put("2Z","长安航空");
        map1.put("IV","福建航空");
        map1.put("SC","山东航空");
        map1.put("8C","山西航空");
        map1.put("HU","海南航空");
        map1.put("KN","联合航空");
        map1.put("BK","奥凯航空");
        map1.put("9C","春秋航空");
        map1.put("EU","鹰联航空");
        map1.put("NS","东北航空");
        map1.put("PN","西部航空");
        map1.put("OQ","重庆航空");
        map1.put("VD","鲲鹏航空");
        map1.put("8C","东星航空");
        map1.put("HO","吉祥航空");
        map1.put("CN","大新航空");
        map1.put("G5","华夏航空");
        map1.put("DJ","金鹿航空");
        map1.put("JI","翡翠航空");
        map1.put("Y8","扬子江航");
        map1.put("8Y","邮政航空");
        map1.put("IJ","长城航空");
        map1.put("GD","银河航空");
        map1.put("GS","天津航空");
        map1.put("JD","首都航空");
        map1.put("KA","港龙航空");
        map1.put("CX","国泰航空");
        map1.put("NX","澳门航空");
        map1.put("HX","香港航空");
        map1.put("OZ","韩亚航空");
        map1.put("JL","日本航空");
        map1.put("QF","澳洲航空");
        map1.put("VS","维珍航空");
        map1.put("NH","全日空");
        map1.put("LH","汉莎航空");
        map1.put("AF","法国航空");
        map1.put("BA","英国航空");
        map1.put("AY","芬兰航空");
        map1.put("PG","曼谷航空");
        try{
            String jsonstring=readStream(new URL(url).openStream());
            JSONObject jsonObject;
            airsBean airsbean;
            jsonObject=new JSONObject(jsonstring);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                airsbean =new airsBean();
                airsbean.airnumber=jsonObject.getString("name");
                airsbean.flytime=jsonObject.getString("dep_time");
                airsbean.endport=jsonObject.getString("arr");
                airsbean.arrivaltime=jsonObject.getString("arr_time");
                if((airsbean.copyname=jsonObject.getString("company")).length()==2)
                {airsbean.copyname=map1.get(jsonObject.getString("company"));}
                airsbean.startport=jsonObject.getString("dep");
                airsbean.tkprice=jsonObject.getString("fly_time");
                airsBeanlist.add(airsbean);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return airsBeanlist;
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
    class airsAsyncTask extends AsyncTask<String,Void,List<airsBean>> {

        @Override
        protected List<airsBean> doInBackground(String... params) {
            return getjsondata(params[0]);
        }
        @Override
        protected void onPostExecute(List<airsBean> airsBeans){
            super.onPostExecute(airsBeans);
            airsAdapter adapter =new airsAdapter(list2Activity.this,airsBeans);
            mlistView2.setAdapter(adapter);
        }
    }
}

