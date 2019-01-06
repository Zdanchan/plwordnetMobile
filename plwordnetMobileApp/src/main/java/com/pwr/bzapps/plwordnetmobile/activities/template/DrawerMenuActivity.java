package com.pwr.bzapps.plwordnetmobile.activities.template;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.*;
import com.pwr.bzapps.plwordnetmobile.language.LanguageManager;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public abstract class DrawerMenuActivity extends AppCompatActivity {

    final protected DrawerMenuActivity instance;

    protected DrawerLayout mDrawerMain;
    protected Toolbar toolbarMain;
    protected ActionBar actionbar;
    protected ImageView toolbarAppIcon;
    protected boolean wentToNextActivity=false;
    protected String curr_language;

    protected DrawerMenuActivity(){
        super();
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarMain = findViewById(R.id.main_toolbar);
        mDrawerMain = findViewById(R.id.main_drawer);
        curr_language = Settings.getLocaleName();

        mDrawerMain.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //setting toolbar
        toolbarMain.setTitle("");
        setSupportActionBar(toolbarMain);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        mDrawerMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_recent_searches:
                                mDrawerMain.closeDrawer(GravityCompat.START);
                                wentToNextActivity=true;
                                startActivity(new Intent(getApplicationContext(), RecentSearchesActivity.class));
                                return true;
                            case R.id.nav_bookmarks:
                                mDrawerMain.closeDrawer(GravityCompat.START);
                                wentToNextActivity=true;
                                startActivity(new Intent(getApplicationContext(), BookmarksActivity.class));
                                return true;
                                case R.id.nav_change_language:
                                mDrawerMain.closeDrawer(GravityCompat.START);
                                wentToNextActivity=true;
                                startActivity(new Intent(getApplicationContext(), ChangeLanguageActivity.class));
                                return true;
                            case R.id.nav_settings:
                                mDrawerMain.closeDrawer(GravityCompat.START);
                                wentToNextActivity=true;
                                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                                return true;
                            case R.id.nav_about_us:
                                mDrawerMain.closeDrawer(GravityCompat.START);
                                wentToNextActivity=true;
                                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                                return true;
                            case R.id.nav_faq:
                                mDrawerMain.closeDrawer(GravityCompat.START);
                                wentToNextActivity=true;
                                startActivity(new Intent(getApplicationContext(), FAQActivity.class));
                                return true;
                        }
                        return true;
                    }
                });
        toolbarAppIcon = findViewById(R.id.toolbar_app_icon);
        centerToolbarAppIcon();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MenuItem home = (MenuItem)item;
                if(mDrawerMain.isDrawerOpen(GravityCompat.START)){
                    mDrawerMain.closeDrawer(GravityCompat.START);
                }
                else {
                    mDrawerMain.openDrawer(GravityCompat.START);
                }
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
