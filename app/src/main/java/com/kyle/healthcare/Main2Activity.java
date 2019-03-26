package com.kyle.healthcare;

import android.Manifest;
import android.os.Bundle;
import android.app.Activity;

import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.kyle.healthcare.R;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {


    public LocationClient client;
    private MapView mapView;
    private BaiduMap baiduMap;
    private ArrayList<LatLng> latLngs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main2);
        mapView = findViewById(R.id.map_view);
        client = new LocationClient(getApplicationContext());
        client.registerLocationListener(new LocationLister() );
        baiduMap = mapView.getMap();
        latLngs = new ArrayList<>();
        baiduMap.setMyLocationEnabled(true);
        request();
    }

    //申请权限
    private void request(){
        List<String> permission = new ArrayList<>();
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)!=PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if(checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if(checkSelfPermission(Manifest.permission.WAKE_LOCK)!=PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.WAKE_LOCK);
        }
        if(checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.INTERNET);
        }
        if(!permission.isEmpty()){
            String[] permissions = permission.toArray(new String[permission.size()]);
            requestPermissions(permissions,1);
        }else {
            requestLocation();
        }
    }

    private void requestLocation(){
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setScanSpan(1000);
        client.setLocOption(locationClientOption);
        this.client.start();
    }

    private double offset = 0.00001;
    private boolean first = true;

    private void navigateTo(BDLocation bdLocation){
        LatLng latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(mapStatusUpdate);
        if(first){
            mapStatusUpdate = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(mapStatusUpdate);
            MyLocationData.Builder builder = new MyLocationData.Builder();
            builder.latitude(latLng.latitude);
            builder.longitude(latLng.longitude);
            MyLocationData myLocationData = builder.build();
            baiduMap.setMyLocationData(myLocationData);
            first = false;
        }
        offset+=0.0001;
        latLng = new LatLng(bdLocation.getLatitude()+offset,bdLocation.getLongitude()+offset);
        latLngs.add(latLng);
        if(latLngs.size() >= 2){
            OverlayOptions overlayOptions = new PolylineOptions().width(10).points(latLngs);
            Overlay overlay = baiduMap.addOverlay(overlayOptions);
            latLngs.remove(0);
        }
    }

    //申请权限结结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0){
                for(int result : grantResults){
                    if(result != PackageManager.PERMISSION_GRANTED){
                        Log.i("location","activity finish");
                    }
                }
                requestLocation();
            }
        }
    }

    public class LocationLister extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    navigateTo(bdLocation);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        client.stop();
        baiduMap.setMyLocationEnabled(false);
    }
}