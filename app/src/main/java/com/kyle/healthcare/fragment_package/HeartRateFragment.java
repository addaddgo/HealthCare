package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyle.healthcare.R;
import com.kyle.healthcare.view.HeartRateView;

public class HeartRateFragment extends Fragment {

    private HeartRateView heartRateView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heart_rate_frag,container,false);
        this.heartRateView = view.findViewById(R.id.heart_rate_frag_heart_rate_view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.heartRateView.stopDrawThread();
    }
}
