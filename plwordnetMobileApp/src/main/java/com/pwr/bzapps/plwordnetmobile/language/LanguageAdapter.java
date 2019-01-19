package com.pwr.bzapps.plwordnetmobile.language;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;

public class LanguageAdapter extends ArrayAdapter<String>  {
    Context context;

    public LanguageAdapter(Context context, ArrayList<String> data){
        super(context, R.layout.language_row_template, data);
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String symbol = getItem(position);
        LanguageAdapter.LanguageRowItem rowLanguageItem = new LanguageAdapter.LanguageRowItem();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.language_row_template, parent, false);
        rowLanguageItem.language_text = ((TextView) convertView.findViewById(R.id.language_text));
        rowLanguageItem.language_icon = ((ImageView) convertView.findViewById(R.id.language_icon));
        rowLanguageItem.row = (RelativeLayout) convertView.findViewById(R.id.row);

        String name = context.getResources().getString(context.getResources().getIdentifier("locale_"+symbol,"string",context.getPackageName()));
        int flag_resource = context.getResources().getIdentifier("flag_" + name,"drawable",context.getPackageName());
        flag_resource = (flag_resource == 0 ? R.drawable.flag_unknown : flag_resource);

        rowLanguageItem.language_text.setText(context.getResources().getString(context.getResources().getIdentifier("lang_" + symbol,"string",context.getPackageName())));
        rowLanguageItem.language_icon.setImageResource(flag_resource);
        if(Settings.getLocaleName().equals(symbol)){
            rowLanguageItem.row.setBackgroundColor(context.getResources().getColor(R.color.colorHighlight,null));
        }
        else{
            rowLanguageItem.row.setBackgroundColor(context.getResources().getColor(R.color.alpha,null));
        }
        //convertView.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        String[] locales = context.getResources().getStringArray(R.array.locales_symbols);
        //        Settings.setLocale(locales[position]);
        //        notifyDataSetChanged();
        //    }
        //});

        return convertView;
    }

    private static class LanguageRowItem{
        TextView language_text;
        ImageView language_icon;
        RelativeLayout row;
    }
}
