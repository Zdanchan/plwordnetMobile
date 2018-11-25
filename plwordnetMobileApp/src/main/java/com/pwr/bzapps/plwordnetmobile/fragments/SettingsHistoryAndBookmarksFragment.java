package com.pwr.bzapps.plwordnetmobile.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class SettingsHistoryAndBookmarksFragment extends Fragment {

    private LinearLayout container;
    private RelativeLayout clear_history, clear_bookmarks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.settings_history_and_bookmarks_fragment, container, false);

        clear_history = view.findViewById(R.id.clear_history);
        clear_bookmarks = view.findViewById(R.id.clear_bookmarks);

        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.clearHistory();
                Toast.makeText(getActivity(),getResources().getString(R.string.clear_history_toast), Toast.LENGTH_LONG).show();
            }
        });

        clear_bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.clearBookmarks();
                Toast.makeText(getActivity(),getResources().getString(R.string.clear_bookmarks_toast), Toast.LENGTH_LONG).show();
            }
        });

        return view;

    }
}
