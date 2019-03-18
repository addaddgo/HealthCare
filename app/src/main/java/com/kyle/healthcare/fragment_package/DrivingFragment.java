package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
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

import com.kyle.healthcare.R;
import com.kyle.healthcare.UIInterface;
import com.kyle.healthcare.bluetooth.Constants;

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
        return view;
    }

    // 添加菜单
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        uiInterface = (UIInterface) getActivity();
        uiInterface.setTitle(R.string.title_driving);
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
                uiInterface.replaceFragmentInFragment(Constants.frag_id_history_log);
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
        this.totalDistance.setText(distance);
        this.totalTime.setText(time);
        this.averageSpeech.setText(speech);
    }
}
