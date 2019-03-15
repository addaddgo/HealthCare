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

public class CenterFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_frag, container,false);
        return view;
    }
//
//    @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.log_out:
//                    Intent intent = new Intent("com.kyle.healthcare.LOG_OUT");
//                    getActivity().sendBroadcast(intent);
//                    break;
//                default:
//                    break;
//            }
//        }
    }
