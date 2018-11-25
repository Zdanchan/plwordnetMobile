package com.pwr.bzapps.plwordnetmobile.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.pwr.bzapps.plwordnetmobile.R;

public class SettingsVisualFragment extends Fragment {

    private LinearLayout container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.settings_visual_fragment, container, false);

        return view;

    }
}
