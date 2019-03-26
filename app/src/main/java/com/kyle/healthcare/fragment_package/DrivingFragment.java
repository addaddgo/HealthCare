package com.kyle.healthcare.fragment_package;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.kyle.healthcare.R;
import com.kyle.healthcare.controller_data.DataManger;
import com.kyle.healthcare.controller_data.DrivingData;
import com.kyle.healthcare.UIInterface;
import com.kyle.healthcare.bluetooth.Constants;
import com.kyle.healthcare.controller_data.FragmentAddressBook;

import java.util.ArrayList;
import java.util.List;

public class DrivingFragment extends Fragment {
    private UIInterface uiInterface;
    private TextView totalDistance;
    private TextView averageSpeech;
    private TextView totalTime;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.driving_frag, container,false);
        totalDistance = view.findViewById(R.id.driving_record_fra_total_distance);
        averageSpeech = view.findViewById(R.id.driving_record_fra_average_speech);
        totalTime = view.findViewById(R.id.driving_record_fra_time);
        mapView = view.findViewById(R.id.driving_map_view);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        DataManger dataManger = DataManger.dataManger;
        if(dataManger != null && dataManger.getLatestDrivingInformation() != null){
            DrivingData drivingData = dataManger.getLatestDrivingInformation();
            update(String.valueOf(drivingData.totalTime),String.valueOf(drivingData.totalDistance),String.valueOf(drivingData.averageSpeech));
        }
        return view;
    }

    // 添加菜单
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        uiInterface = (UIInterface) getActivity();
        client = new LocationClient(getContext());
        client.registerLocationListener(new LocationLister());
        latLngs = new ArrayList<>();
        request();
    }

    @Override
    public void onResume() {
        super.onResume();
        uiInterface.setTitle(R.string.title_driving);
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        client.stop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_driving,menu );
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_history_log:
                uiInterface.replaceFragmentInFragment(FragmentAddressBook.frag_id_history_log);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateTotalTime(String string){
        this.totalTime.setText(string);
    }

    public void updateTotalDistance(String string){
        this.totalDistance.setText(string);
    }

    public void updateAverageSpeech(String string){
        this.averageSpeech.setText(string);
    }

    public void update(String time,String distance,String speech){
        if(this.totalDistance != null && this.totalTime != null && this.averageSpeech != null){
            this.totalDistance.setText(distance);
            this.totalTime.setText(time);
            this.averageSpeech.setText(speech);
        }
    }


    //map
    private MapView mapView;
    private LocationClient client;
    private BaiduMap baiduMap;
    public final static  int INTERVAL_NAVIGATE = 2000;



    private void request(){
        List<String> permission = new ArrayList<>();
        if(getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(getActivity().checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)!=PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if(getActivity().checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if(getActivity().checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if(getActivity().checkSelfPermission(Manifest.permission.WAKE_LOCK)!=PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.WAKE_LOCK);
        }
        if(getActivity().checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.INTERNET);
        }
        if(!permission.isEmpty()){
            String[] permissions = permission.toArray(new String[permission.size()]);
            requestPermissions(permissions,FragmentAddressBook.frag_id_driving);
        }else {
            requestLocation();
        }
    }

    public void requestLocation(){
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setScanSpan(INTERVAL_NAVIGATE);
        client.setLocOption(locationClientOption);
        this.client.start();
    }

    private ArrayList<LatLng> latLngs;

    public class LocationLister extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        if(baiduMap != null){
                            LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                            baiduMap.animateMapStatus(mapStatusUpdate);
                            mapStatusUpdate = MapStatusUpdateFactory.zoomTo(16f);
                            baiduMap.animateMapStatus(mapStatusUpdate);
                            MyLocationData.Builder builder = new MyLocationData.Builder();
                            builder.latitude(latLng.latitude);
                            builder.longitude(latLng.longitude);
                            MyLocationData myLocationData = builder.build();
                            baiduMap.setMyLocationData(myLocationData);
                            latLngs.add(latLng);
                            if (latLngs.size() >= 2) {
                                OverlayOptions overlayOptions = new PolylineOptions().width(10).points(latLngs);
                                Overlay overlay = baiduMap.addOverlay(overlayOptions);
                                latLngs.remove(0);
                            }
                            Handler handler = uiInterface.getHandler();
                            Message message = handler.obtainMessage();
                            message.what = Constants.LATLNG;
                            message.obj = latLng;
                            handler.sendMessage(message);
                        }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FragmentAddressBook.frag_id_driving){
            requestLocation();
        }
    }
}
