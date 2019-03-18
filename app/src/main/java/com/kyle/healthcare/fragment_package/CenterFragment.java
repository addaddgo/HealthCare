package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyle.healthcare.R;
import com.kyle.healthcare.UIInterface;
import com.kyle.healthcare.bluetooth.Constants;
import com.kyle.healthcare.controller_data.TimeSupport;
import com.kyle.healthcare.view.ScrollSelectView;

public class CenterFragment extends Fragment implements View.OnClickListener{

    UIInterface uiInterface;

    private TextView topEditButton;
    private TextView bottomEditButton;

    private EditText nameEdit;
    private EditText licenseEdit;
    private EditText phoneEdit;

    private ScrollSelectView year;
    private ScrollSelectView month;
    private ScrollSelectView day;
    private ScrollSelectView sex;

    private ImageView imageView;

    private String[] years;
    private String[] months;
    private String[] days;



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_frag, container,false);
        lineId(view);
        return view;
    }
    // 添加菜单
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        uiInterface = (UIInterface) getActivity();
        this.years = TimeSupport.timeSupport.getYears();
        this.months = turnIntsToStrings(TimeSupport.timeSupport.getMonthInYearByCurrentTime(TimeSupport.timeSupport.getYear(),TimeSupport.timeSupport.getMonth()));
        this.days = turnIntsToStrings(TimeSupport.timeSupport.getDayInMonthAndYearByCurrentTime(TimeSupport.timeSupport.getYear(),TimeSupport.timeSupport.getMonth(),TimeSupport.timeSupport.getDay()));
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

    private void lineId(View view){
        this.nameEdit = view.findViewById(R.id.car_name_center);
        this.sex = view.findViewById(R.id.sex_scroll_center);
        this.year = view.findViewById(R.id.year_scroll_center);
        this.month = view.findViewById(R.id.month_scroll_center);
        this.day = view.findViewById(R.id.day_scroll_center);
        this.phoneEdit = view.findViewById(R.id.phone_number_center);
        this.licenseEdit = view.findViewById(R.id.license_center);
        this.imageView = view.findViewById(R.id.portrait_center);
    }

    @Override
    public void onResume() {
        super.onResume();
        setWidgetUntouchable();
        initWidget();
    }

    //let them untouchable
    private void setWidgetUntouchable(){
        this.licenseEdit.setFocusableInTouchMode(false);
        this.licenseEdit.setFocusable(false);
        this.phoneEdit.setFocusableInTouchMode(false);
        this.phoneEdit.setFocusable(false);
        this.nameEdit.setFocusableInTouchMode(false);
        this.nameEdit.setFocusable(false);
        this.sex.setUnableTouched();
        this.year.setUnableTouched();
        this.month.setUnableTouched();
        this.day.setUnableTouched();
    }

    //let them can be edited
    private void setWidgetTouchable(){
        this.licenseEdit.setFocusableInTouchMode(true);
        this.licenseEdit.setFocusable(true);
        this.phoneEdit.setFocusableInTouchMode(true);
        this.phoneEdit.setFocusable(true);
        this.nameEdit.setFocusableInTouchMode(true);
        this.nameEdit.setFocusable(true);
        this.sex.setAbleTouched();
        this.year.setAbleTouched();
        this.month.setAbleTouched();
        this.day.setAbleTouched();
    }

    private void initWidget(){
        this.sex.setData(new String[]{"男","女"},1 );
        this.year.setData(this.years,4);
        this.month.setData(this.months,2);
        this.day.setData(this.days,1);
    }

    //scroll show one string
    private void showOneString(){
        this.sex.setShowOnlyOneString("男");
        this.year.setShowOnlyOneString(String.valueOf(TimeSupport.timeSupport.getYear()));
        this.month.setShowOnlyOneString(String.valueOf(TimeSupport.timeSupport.getMonth()));
        this.day.setShowOnlyOneString(String.valueOf(TimeSupport.timeSupport.getDay()));
    }

    //scroll start picking
    private void startPick(){
        try {
            this.day.starPick(0);
            this.sex.starPick(0);
            this.year.starPick(0);
            this.month.starPick(0);
        } catch (ScrollSelectView.NullDataException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

    }

    //turn int[] to string[]
    private String[] turnIntsToStrings(int[] a){
        String[] s = new String[a.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = String.valueOf(a[i]);
        }
        return s;
    }
}
