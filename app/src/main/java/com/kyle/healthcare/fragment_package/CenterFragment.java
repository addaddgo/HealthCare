package com.kyle.healthcare.fragment_package;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kyle.healthcare.R;

public class CenterFragment extends Fragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_frag, container,false);
        return view;
    }


    @Override
    public void onClick(View v) {

    }
}
