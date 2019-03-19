package com.kyle.healthcare.fragment_package;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyle.healthcare.R;
import com.kyle.healthcare.UIInterface;
import com.kyle.healthcare.bluetooth.Constants;
import com.kyle.healthcare.camera.CameraActivity;
import com.kyle.healthcare.controller_data.FragmentAddressBook;
import com.kyle.healthcare.controller_data.TimeSupport;
import com.kyle.healthcare.view.CircleImage;
import com.kyle.healthcare.view.ScrollSelectView;

public class CenterFragment extends Fragment implements View.OnClickListener{

    UIInterface uiInterface;

    private TextView topEditButton;
    private TextView bottomEditButton;

    private EditText nameEdit;
    private EditText licenseEdit;
    private EditText phoneEdit;

    private EditText year;
    private EditText month;
    private EditText day;
    private EditText sex;

    private CircleImage imageView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_frag, container, false);
        lineId(view);
        return view;
    }

    // 添加菜单
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        uiInterface = (UIInterface) getActivity();
        uiInterface.setTitle(R.string.title_center);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_center, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                uiInterface.setTitle(R.string.settings);
                uiInterface.replaceFragmentInFragment(FragmentAddressBook.frag_id_settings);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void lineId(View view) {
        this.nameEdit = view.findViewById(R.id.car_name_center);
        this.sex = view.findViewById(R.id.sex_edit_center);
        this.year = view.findViewById(R.id.year_edit_center);
        this.month = view.findViewById(R.id.month_edit_center);
        this.day = view.findViewById(R.id.day_edit_center);
        this.phoneEdit = view.findViewById(R.id.phone_number_center);
        this.licenseEdit = view.findViewById(R.id.license_center);
        this.imageView = view.findViewById(R.id.portrait_center);
        this.topEditButton = view.findViewById(R.id.correct_person_information_center);
        this.bottomEditButton = view.findViewById(R.id.correct_license_center);
    }

    @Override
    public void onResume() {
        super.onResume();
        uiInterface.setTitle(R.string.title_center);
        initWidget();
        this.bottomEditButton.setOnClickListener(this);
        this.topEditButton.setOnClickListener(this);
        this.imageView.setOnClickListener(this);
    }

    //let them untouchable
    private void setWidgetUntouchableDown() {
        this.licenseEdit.setFocusableInTouchMode(false);
        this.licenseEdit.setFocusable(false);
        this.phoneEdit.setFocusableInTouchMode(false);
        this.phoneEdit.setFocusable(false);
    }

    private void setWidgetUntouchableUp() {
        this.nameEdit.setFocusable(false);
        this.nameEdit.setFocusableInTouchMode(false);
        this.year.setFocusableInTouchMode(false);
        this.year.setFocusable(false);
        this.month.setFocusableInTouchMode(false);
        this.month.setFocusable(false);
        this.day.setFocusable(false);
        this.day.setFocusableInTouchMode(false);
        this.sex.setFocusableInTouchMode(false);
        this.sex.setFocusable(false);

    }

    //let them can be edited
    private void setWidgetTouchableDown() {
        this.licenseEdit.setFocusableInTouchMode(true);
        this.licenseEdit.setFocusable(true);
        this.phoneEdit.setFocusableInTouchMode(true);
        this.phoneEdit.setFocusable(true);
    }

    private void setWidgetTouchableUp() {
        this.nameEdit.setFocusable(true);
        this.nameEdit.setFocusableInTouchMode(true);
        this.year.setFocusableInTouchMode(true);
        this.year.setFocusable(true);
        this.month.setFocusableInTouchMode(true);
        this.month.setFocusable(true);
        this.day.setFocusable(true);
        this.day.setFocusableInTouchMode(true);
        this.sex.setFocusableInTouchMode(true);
        this.sex.setFocusable(true);
    }

    private void initWidget() {
        setWidgetUntouchableDown();
        setWidgetUntouchableUp();
    }
    //init data
    private void initData(){
        //TODO(): 注意：initData之后，需要根据用户的生日来调整ScorllSelectView 的数据
    }

    private boolean topIsEdited;
    private boolean bottomIsEdited;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.correct_person_information_center:
                if(this.topIsEdited){
                    Log.d("center","information");
                    this.topEditButton.setText("编辑");
                    this.topIsEdited = false;
                    setWidgetUntouchableUp();
                }else{
                    this.topEditButton.setText("保存");
                    this.topIsEdited = true;
                    setWidgetTouchableUp();
                }
                break;
            case R.id.correct_license_center:
                if(bottomIsEdited){
                    this.bottomEditButton.setText("编辑");
                    setWidgetUntouchableDown();
                    this.bottomIsEdited = false;

                }else{
                    this.bottomIsEdited = true;
                    setWidgetTouchableDown();
                    this.bottomEditButton.setText("保存");
                }
                break;
            case R.id.portrait_center:
                Intent intent = new Intent(getActivity(),CameraActivity.class);
                startActivity(intent);
                break;
        }
    }

    //turn int[] to string[]
    private String[] turnIntsToStrings(int[] a) {
        String[] s = new String[a.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = String.valueOf(a[i]);
        }
        return s;
    }

    private String yearSelected;
    private String monthSelected;
    private String daySelected;


    public String getYearSelected() {
        return yearSelected;
    }

    public String getMonthSelected() {
        return monthSelected;
    }

    public String getDaySelected() {
        return daySelected;
    }


}

