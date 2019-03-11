package com.kyle.heathcare;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

    private TextView mTextMessage;
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_homepage:
                    mTextMessage.setText(R.string.title_homepage);
                    replaceFragment(new HomepageFragment());
                    return true;

                case R.id.navigation_health:
                    mTextMessage.setText(R.string.title_health);
                    replaceFragment(new HealthFragment());
                    return true;

                case R.id.navigation_driving:
                    mTextMessage.setText(R.string.title_driving);
                    replaceFragment(new DrivingFragment());
                    return true;

                case R.id.navigation_center:
                    mTextMessage.setText(R.string.title_center);
                    replaceFragment(new CenterFragment());
                    return true;
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frag, fragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
