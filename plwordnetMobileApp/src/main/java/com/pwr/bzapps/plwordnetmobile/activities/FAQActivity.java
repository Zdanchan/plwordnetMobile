package com.pwr.bzapps.plwordnetmobile.activities;

import android.os.Bundle;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;

public class FAQActivity extends BackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_faq);
        super.onCreate(savedInstanceState);
    }
}
