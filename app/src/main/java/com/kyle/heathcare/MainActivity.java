package com.kyle.heathcare;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.kyle.heathcare.fragment_package.CenterFragment;
import com.kyle.heathcare.fragment_package.DrivingFragment;
import com.kyle.heathcare.fragment_package.HealthFragment;
import com.kyle.heathcare.fragment_package.HomepageFragment;

public class MainActivity extends AppCompatActivity {

    // TODO: 2019/3/11 1)四个Fragment
    // TODO: 2019/3/11 2)异常判断及异常Fragment（响铃、倒计时、电话权限）
    // TODO: 2019/3/11 3)注册
    // TODO: 2019/3/11 4)多账户数据库
    // TODO: 2019/3/11 5)健康数据数据库
    // TODO: 2019/3/11 6)本地偏好设置

    private HomepageFragment homepageFragment;
    private HealthFragment healthFragment;
    private DrivingFragment drivingFragment;
    private CenterFragment centerFragment;
    private TextView toolbarTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_homepage:
                    toolbarTitle.setText(R.string.title_homepage);
                    replaceFragment(homepageFragment);
                    return true;

                case R.id.navigation_health:
                    toolbarTitle.setText(R.string.title_health);
                    replaceFragment(healthFragment);
                    return true;

                case R.id.navigation_driving:
                    toolbarTitle.setText("行驶记录");
                    replaceFragment(drivingFragment);
                    return true;

                case R.id.navigation_center:
                    toolbarTitle.setText(R.string.title_center);
                    replaceFragment(centerFragment);
                    return true;
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_frag, fragment);
        transaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homepageFragment = new HomepageFragment();
        healthFragment = new HealthFragment();
        drivingFragment = new DrivingFragment();
        centerFragment = new CenterFragment();
        toolbarTitle = findViewById(R.id.toolbar_title);

        toolbarTitle.setText(R.string.title_homepage);
        replaceFragment(homepageFragment);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}
