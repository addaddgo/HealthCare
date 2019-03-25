package com.kyle.healthcare.fragment_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kyle.healthcare.R;
import com.kyle.healthcare.UIInterface;
import com.kyle.healthcare.base_package.LogInActivity;
import com.kyle.healthcare.controller_data.FragmentAddressBook;

public class SettingsFragment extends PreferenceFragmentCompat{
    private View view;
    private UIInterface uiInterface;
    private ActionBar actionBar;
    private Button log_off;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiInterface = (UIInterface) getActivity();
        uiInterface.setTitle(R.string.settings);
    }


    @Override
    public void onStart() {
        super.onStart();
        uiInterface.setNavigationVisibility(View.GONE);
        actionBar = uiInterface.getBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        uiInterface.setNavigationVisibility(View.VISIBLE);
    }
}
