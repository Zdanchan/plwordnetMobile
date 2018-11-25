package com.pwr.bzapps.plwordnetmobile.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class SettingsLocalDatabaseFragment extends Fragment {

    private LinearLayout container;
    private RelativeLayout offline_mode;
    private Switch offline_mode_switch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.settings_database_fragment, container, false);

        offline_mode = view.findViewById(R.id.offline_mode);
        offline_mode_switch = offline_mode.findViewById(R.id.offline_mode_switch);
        offline_mode_switch.setChecked(Settings.isOfflineMode());
        offline_mode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Settings.setOfflineMode(b);
            }
        });

        return view;

    }
}
