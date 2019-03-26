package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyle.healthcare.UIInterface;
import com.kyle.healthcare.R;
import com.kyle.healthcare.bluetooth.Constants;
import com.kyle.healthcare.controller_data.FragmentAddressBook;
import com.kyle.healthcare.view.FatigueRateView;
import com.kyle.healthcare.view.HeartRateView;

public class HealthFragment extends Fragment {

    private HeartRateView heartRateView;
    private ImageView fatigueRateView;
    private UIInterface uiInterface;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_frag,container,false);
        heartRateView = view.findViewById(R.id.health_fra_heart_rate_view);
        this.fatigueRateView = view.findViewById(R.id.health_fra_fatigue_rate_view);
        this.textView = view.findViewById(R.id.health_fra_health_rate_text);
        addListener();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiInterface = (UIInterface) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        uiInterface.setTitle(R.string.title_health);
    }

    private void addListener(){
        this.fatigueRateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiInterface.replaceFragmentInFragment(FragmentAddressBook.frag_id_fatigue_rate);
            }
        });
        this.heartRateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiInterface.replaceFragmentInFragment(FragmentAddressBook.frag_id_heart_rate);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if(heartRateView != null && fatigueRateView != null){
            heartRateView.stopDrawThread();
            heartRateView.close();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    //add new data
    public void addNewData(int heartRate,int fatigueRate){
        if(this.textView != null){
            this.textView.setText(String.valueOf(heartRate));
        }
        if(this.heartRateView != null && this.fatigueRateView != null){
            this.heartRateView.addData(heartRate);
        }
    }
}
