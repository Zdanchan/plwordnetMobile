package com.pwr.bzapps.plwordnetmobile.activities.template;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.settings.LanguageManager;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.Locale;

public abstract class BackButtonActivity extends AppCompatActivity{

    protected Toolbar toolbarMain;
    protected ActionBar actionbar;
    protected ImageView toolbarAppIcon;

    protected BackButtonActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarMain = findViewById(R.id.main_toolbar);

        //setting toolbar
        toolbarMain.setTitle("");
        setSupportActionBar(toolbarMain);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        toolbarAppIcon = findViewById(R.id.toolbar_app_icon);
        centerToolbarAppIcon();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageManager.setLanguage(base));
    }

    private void centerToolbarAppIcon(){
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        float ratio = (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        int iconWidth = (int)(50 * (ratio));
        int screenWidth;
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        screenWidth = size. x;

        int margin = (screenWidth/2) - ((int)((float)iconWidth)/2);

        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(iconWidth, iconWidth);
        rp.setMargins(0, 0, margin, 0);
        rp.addRule(RelativeLayout.CENTER_VERTICAL);
        rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        toolbarAppIcon.setLayoutParams(rp);

    }
}
