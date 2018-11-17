package com.pwr.bzapps.plwordnetmobile.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;

import java.util.ArrayList;

public class SearchEntryAdapter extends ArrayAdapter<String> {

    private ArrayList<String> data;

    public SearchEntryAdapter(Context context, ArrayList<String> data){
        super(context, R.layout.result_row_template, data);
        this.data=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String entry = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.search_history_row_template, parent, false);
        TextView entry_value = ((TextView) convertView.findViewById(R.id.search_value));

        entry_value.setText(entry);
        return convertView;
    }
}
