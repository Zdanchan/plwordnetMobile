package com.pwr.bzapps.plwordnetmobile.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.database.access.task.CheckLocalSQLiteDBWithServerTask;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class SettingsLocalDatabaseFragment extends Fragment {

    private LinearLayout container;
    private RelativeLayout offline_mode;
    private Switch offline_mode_switch;
    private RelativeLayout polish_db, english_db;
    private CheckBox polish_checkbox, english_checkbox;
    private CheckLocalSQLiteDBWithServerTask checkLocalSQLiteDBWithServerTask;
    private Animation rotation_anim;
    private ProgressBar sync_progress;
    private ImageView sync_icon;
    private TextView status_value;
    private boolean buttons_enabled;
    private int status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.settings_database_fragment, container, false);
        buttons_enabled = true;
        offline_mode = view.findViewById(R.id.offline_mode);
        polish_db = view.findViewById(R.id.polish_dictionary);
        english_db = view.findViewById(R.id.english_dictionary);
        polish_checkbox = view.findViewById(R.id.polish_checkbox);
        english_checkbox = view.findViewById(R.id.english_checkbox);
        offline_mode_switch = offline_mode.findViewById(R.id.offline_mode_switch);
        offline_mode_switch.setChecked(Settings.isOfflineMode());
        offline_mode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Settings.setOfflineMode(b);
            }
        });
        polish_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled) {
                    polish_checkbox.setChecked(!polish_checkbox.isChecked());
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.sync_please_wait), Toast.LENGTH_LONG).show();
                }
            }
        });
        english_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled){
                    english_checkbox.setChecked(!english_checkbox.isChecked());
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.sync_please_wait), Toast.LENGTH_LONG).show();
                }
            }
        });
        sync_progress = (ProgressBar) view.findViewById(R.id.sync_progress_bar);
        sync_progress.setVisibility(View.GONE);
        sync_icon = (ImageView) view.findViewById(R.id.sync_databases_icon);
        status_value = view.findViewById(R.id.status_value);

        RelativeLayout synchronize_button = view.findViewById(R.id.sync_databases);
        synchronize_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled) {
                    String local_db_langs = manageCheckedDictionaries();
                    Settings.setLocal_db_langs(local_db_langs);
                    if(!local_db_langs.equals("none")) {
                        sync_progress.setVisibility(View.VISIBLE);
                        status_value.setText(R.string.status_synchronizing);
                        status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorSynchronizing));
                        if (rotation_anim == null) {
                            rotation_anim = (Animation) AnimationUtils.loadAnimation(
                                    getActivity().getApplicationContext(), R.anim.rotate_360_anim);
                        }
                        rotation_anim.setRepeatCount(Animation.INFINITE);
                        sync_icon.startAnimation(rotation_anim);
                        rotation_anim.setFillAfter(true);
                        disableButtons();
                        if (status != 0) {

                        }
                    }
                    else{
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_db_selected), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.sync_please_wait), Toast.LENGTH_LONG).show();
                }
            }
        });

        RelativeLayout remove_button = view.findViewById(R.id.remove_databases);
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled) {
                    clearChecked();
                    SQLiteDBFileManager.removeLocalDB();
                    status_value.setText(R.string.status_no_local_db);
                    status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNoLocalDB));
                    setStatus(2);
                    Toast.makeText(getActivity(), getResources().getString(R.string.remove_local_db_toast), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.sync_please_wait), Toast.LENGTH_LONG).show();
                }
            }
        });

        checkLocalSQLiteDBWithServerTask =
                new CheckLocalSQLiteDBWithServerTask((RelativeLayout)view.findViewById(R.id.status),this,
                        getActivity().getApplicationContext());
        status_value.setText(R.string.status_checking);
        status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorSynchronizing));
        checkLocalSQLiteDBWithServerTask.execute(Settings.getLocal_db_langs());

        return view;

    }

    private void clearChecked(){
        polish_checkbox.setChecked(false);
        english_checkbox.setChecked(false);
        manageCheckedDictionaries();
    }

    public void disableButtons(){
        buttons_enabled = false;
    }

    public void enableButtons(){
        buttons_enabled = true;
    }

    public void setStatus(int status){
        this.status = status;
    }

    private String manageCheckedDictionaries(){
        if(polish_checkbox.isChecked() && english_checkbox.isChecked() ){
            return "all";
        }
        else if(polish_checkbox.isChecked()){
            return "polish";
        }
        else if(english_checkbox.isChecked() ){
            return "english";
        }
        return "none";
    }
}
