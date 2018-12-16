package com.pwr.bzapps.plwordnetmobile.activities;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.widget.ImageButton;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.layout.listener.OnClickExpander;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class GraphBrowserActivity extends BackButtonActivity{

    private WebView webView;
    private ImageButton toolbar_hide_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_graph_browser);
        super.onCreate(savedInstanceState);
        webView = findViewById(R.id.web_graph_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        int synset_id = getIntent().getExtras().getInt("synset_id",2914);
        webView.loadUrl("file:///android_asset/index.html?synset_id=" + synset_id + "&language=" + Settings.getShortLocalName());
        toolbar_hide_button = findViewById(R.id.toolbar_hide_button);
        toolbar_hide_button.setOnClickListener(new OnClickExpander(true,findViewById(R.id.main_toolbar),toolbar_hide_button,getApplicationContext()));
    }
}
