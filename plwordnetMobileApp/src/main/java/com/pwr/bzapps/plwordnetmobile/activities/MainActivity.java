package com.pwr.bzapps.plwordnetmobile.activities;

import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.DrawerMenuActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class MainActivity extends DrawerMenuActivity {

    private EditText searchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectionProvider.setContext(getApplicationContext());
        SQLiteConnector.setContext(getApplicationContext());
        if(SQLiteDBFileManager.doesLocalDBExists(Settings.getDbType()))
            SQLiteConnector.reloadInstance(getApplicationContext());
        Settings.loadSettings(getApplicationContext());
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        //prevents auto-opening keybord
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preparePartnersWebLinks();

        searchEdit = findViewById(R.id.search_edit);
        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkIfContainsText(searchEdit.getText().toString())){
                    Intent intent = new Intent(getApplicationContext(), SearchResultsListActivity.class);
                    intent.putExtra("search_value",searchEdit.getText().toString());
                    Settings.putSearchEntryToHistory(getApplicationContext(),searchEdit.getText().toString());
                    wentToNextActivity = true;
                    startActivity(intent);
                }
                else{

                }
            }
        });
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(checkIfContainsText(searchEdit.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(), SearchResultsListActivity.class);
                        intent.putExtra("search_value",searchEdit.getText().toString());
                        Settings.putSearchEntryToHistory(getApplicationContext(),searchEdit.getText().toString());
                        wentToNextActivity = true;
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if(source.charAt(i)==' '){

                    }
                    else if (!Character.isLetterOrDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        searchEdit.setFilters(new InputFilter[]{filter});
        ImageButton clear_text_button = findViewById(R.id.clear_text_button);
        clear_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEdit.setText("");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.getLocaleName()!=this.curr_language) {
            recreate();
        }
        if(!wentToNextActivity){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if(clipboard.hasPrimaryClip())
                if(clipboard.getPrimaryClip().getItemCount()>0)
                    if(clipboard.getPrimaryClip().getItemAt(0)!=null){
                        String string = "";
                        Object item = clipboard.getPrimaryClip().getItemAt(0).getText();
                        if(item instanceof android.text.SpannableString){
                            string = ((android.text.SpannableString)item).subSequence(0,((SpannableString) item).length()).toString();
                        }
                        else if(item instanceof String){
                            string = (String) item;
                        }
                        if(string!=null)
                            if(string.length()<40)
                                searchEdit.setText(clipboard.getPrimaryClip().getItemAt(0).getText());
                    }
        }
        wentToNextActivity = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    private void preparePartnersWebLinks(){
        ((ImageView)findViewById(R.id.mnisw_logo)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.mnisw_link)));
                wentToNextActivity = true;
                startActivity(intent);
            }
        });

        ((ImageView)findViewById(R.id.clarin_pl_logo)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.clarinpl_link)));
                wentToNextActivity = true;
                startActivity(intent);
            }
        });

        ((ImageView)findViewById(R.id.pwr_logo)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.pwr_link)));
                wentToNextActivity = true;
                startActivity(intent);
            }
        });

        TextView nekstLink = findViewById(R.id.nekst_link);
        nekstLink.setMovementMethod(LinkMovementMethod.getInstance());

        ((ImageView)findViewById(R.id.synat_logo)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.synat_link)));
                wentToNextActivity = true;
                startActivity(intent);
            }
        });
    }

    private boolean checkIfContainsText(String string){
        String cuted = string.replaceAll(" ", "");
        if(cuted.equals(""))
            return false;
        return true;
    }
}
