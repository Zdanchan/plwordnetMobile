package com.pwr.bzapps.plwordnetmobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.pwr.bzapps.plwordnetmobile.R;

public class SettingsCategoriesFragment extends Fragment {

    private LinearLayout container;
    private RelativeLayout local_database, visual, history_and_bookmarks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.settings_categories_fragment, container, false);

        container = view.findViewById(R.id.settings_categories);
        local_database = container.findViewById(R.id.local_database);
        visual = container.findViewById(R.id.visual);
        history_and_bookmarks = container.findViewById(R.id.history_and_bookmarks);


        local_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentChangeListener)getActivity()).replaceFragment(SettingsLocalDatabaseFragment.class);
            }
        });
        visual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentChangeListener)getActivity()).replaceFragment(SettingsVisualFragment.class);
            }
        });
        history_and_bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentChangeListener)getActivity()).replaceFragment(SettingsHistoryAndBookmarksFragment.class);
            }
        });

        return view;

    }

}
