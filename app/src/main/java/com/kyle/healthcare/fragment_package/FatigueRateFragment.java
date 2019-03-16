package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyle.healthcare.R;
import com.kyle.healthcare.view.FatigueRateView;

public class FatigueRateFragment extends Fragment {

    private FatigueRateView fatigueRateView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fatigue_rate_frag,container,false);
        this.fatigueRateView = view.findViewById(R.id.fatigue_rate_fra_rate_view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.fatigueRateView.stopDrawThread();
    }
}
