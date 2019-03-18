package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyle.healthcare.R;
import com.kyle.healthcare.UIInterface;

public class HistoryLogFragment extends Fragment {
    private UIInterface uiInterface;
    private ActionBar actionBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_log_frag, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiInterface = (UIInterface) getActivity();
        uiInterface.setTitle(R.string.settings);
        uiInterface.setNavigationVisibility(View.GONE);
        actionBar = uiInterface.getBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        uiInterface.setNavigationVisibility(View.VISIBLE);
    }
}
