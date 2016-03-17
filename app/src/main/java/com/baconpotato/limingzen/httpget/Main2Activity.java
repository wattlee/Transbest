package com.baconpotato.limingzen.httpget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.RoutePlanSearch;

public class Main2Activity extends AppCompatActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mlLocationClient;
    private MyLocationListener mLocationListener;
    private Boolean isFirstIn = true;
    private Context context;
    private double mlatitude;
    private double mlongtitude;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main2);
        this.context=this;
        initView();
        initlocation();
            Button button2=(Button)findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(Main2Activity.this, webviewActivity.class);
                    startActivity(intent);
                }
            });
    }


    private void initlocation() {
        mlLocationClient=new LocationClient(this);
        mLocationListener = new MyLocationListener();
    mlLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption Option =new LocationClientOption();
        Option.setCoorType("bd09ll");
        Option.setIsNeedAddress(true);
        Option.setOpenGps(true);
        Option.setScanSpan(3000);
        mlLocationClient.setLocOption(Option);
    }

    public void initView()
    {
        mMapView = (MapView)findViewById(R.id.bmapView);
        mBaiduMap=mMapView.getMap();
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);

    }
    protected void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
        if(!mlLocationClient.isStarted())
        mlLocationClient.start();

    }
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    protected void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        //停止定位
            mlLocationClient.stop();
    }
    public boolean onCreatOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptitionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.common:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.sitemap:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.id_map_location:
                ctmlocation();
            default: break;
        }
        return true;
    }
    private void ctmlocation()
    {
        LatLng latLng = new LatLng(mlatitude,mlongtitude);
        MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
    }
    private class MyLocationListener implements BDLocationListener
    {
        @Override
      public void onReceiveLocation(BDLocation location)
        {
            MyLocationData data=new MyLocationData.Builder()//
            .accuracy(location.getRadius())//
            .latitude(location.getLatitude())//
            .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(data);
            mlatitude=location.getLatitude();
            mlongtitude=location.getLongitude();
            if(isFirstIn)
            {
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;
                Toast.makeText(context,location.getAddrStr(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

