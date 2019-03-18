package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kyle.healthcare.R;
import com.kyle.healthcare.UIInterface;
import com.kyle.healthcare.bluetooth.Constants;

public class CenterFragment extends Fragment{
    UIInterface uiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_frag, container,false);
        return view;
    }
    // 添加菜单
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        uiInterface = (UIInterface) getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_center,menu );
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                uiInterface.setTitle(R.string.settings);
                uiInterface.replaceFragmentInFragment(Constants.frag_id_settings);
                break;
        }
        return super.onOptionsItemSelected(item);
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
