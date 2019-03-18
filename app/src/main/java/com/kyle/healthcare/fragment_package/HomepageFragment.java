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

public class HomepageFragment extends Fragment {
    private UIInterface uiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_frag, container,false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        uiInterface = (UIInterface) getActivity();
        uiInterface.setTitle(R.string.title_driving);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_homepage,menu );
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_driving_habit:
                // TODO: 2019/3/18 导向驾驶习惯fragment
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
