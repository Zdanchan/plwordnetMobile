package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SQLiteDictionaryAdapter {
    private Context context;
    private ArrayList<String> data;
    private Set<SQLiteDictionaryRowItem> rows;
    private boolean isEnabled = true;

    public SQLiteDictionaryAdapter(Context context, ArrayList<String> data){
        this.context=context;
        this.data = data;
        rows = new LinkedHashSet<SQLiteDictionaryRowItem>();
    }

    public void notifyDataSetChanged(){
        checkDictionaryByID(getCheckedDictionaryID());
        int i = 0;
        for(SQLiteDictionaryRowItem row : rows){
            if(SQLiteDBFileManager.doesLocalDBExists(data.get(i)))
                row.cell_remove_button.setVisibility(View.VISIBLE);
            else
                row.cell_remove_button.setVisibility(View.GONE);

            if(data.get(i).equals(Settings.getDbType()) || (i==0 && "none".equals(Settings.getDbType())))
                row.cell_radio_button.setChecked(true);

            i++;
        }

    }

    public View getView(final int position) {
        View convertView;
        final String item = data.get(position);
        SQLiteDictionaryAdapter.SQLiteDictionaryRowItem sqLiteDictionaryRowItem = new SQLiteDictionaryAdapter.SQLiteDictionaryRowItem();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.sqlite_pack_row, null);
        sqLiteDictionaryRowItem.cell = ((RelativeLayout) convertView.findViewById(R.id.dictionary_cell));
        sqLiteDictionaryRowItem.cell_text = ((TextView) convertView.findViewById(R.id.cell_text));
        sqLiteDictionaryRowItem.cell_remove_button = ((ImageButton) convertView.findViewById(R.id.cell_remove_button));
        sqLiteDictionaryRowItem.cell_radio_button = (RadioButton) convertView.findViewById(R.id.cell_radio_button);

        sqLiteDictionaryRowItem.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(SQLiteDictionaryRowItem row : rows){
                    if(row.cell_radio_button.equals(view))
                        row.cell_radio_button.setChecked(true);
                    else
                        row.cell_radio_button.setChecked(false);
                }
            }
        });
        sqLiteDictionaryRowItem.cell_radio_button.setClickable(false);


        String name = context.getResources().getString(context.getResources().getIdentifier("dictionary_"+item,"string",context.getPackageName()));
        sqLiteDictionaryRowItem.cell_text.setText(name);

        sqLiteDictionaryRowItem.cell_remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEnabled){
                    SQLiteDBFileManager.removeLocalDB(item);
                    notifyDataSetChanged();
                }
            }
        });

        if(!SQLiteDBFileManager.doesLocalDBExists(item))
            sqLiteDictionaryRowItem.cell_remove_button.setVisibility(View.GONE);

        if(item.equals(Settings.getDbType()) || (position==0 && "none".equals(Settings.getDbType())))
            sqLiteDictionaryRowItem.cell_radio_button.setChecked(true);

        rows.add(sqLiteDictionaryRowItem);
        return convertView;
    }

    public int getCheckedDictionaryID(){
        int i = 0;
        for(SQLiteDictionaryRowItem row : rows){
            if(row.cell_radio_button.isChecked())
                return i;
            i++;
        }
        return -1;
    }

    public void checkDictionaryByID(int id){
        int i = 0;
        for(SQLiteDictionaryRowItem row : rows){
            if(i==id)
                row.cell_radio_button.setChecked(true);
            else
                row.cell_radio_button.setChecked(false);
            i++;
        }
    }

    public String getCheckedDictionary(){
        int i = 0;
        for(SQLiteDictionaryRowItem row : rows){
            if(row.cell_radio_button.isChecked())
                return data.get(i);
            i++;
        }
        return "none";
    }

    public void setTextColor(Integer color){
        for(SQLiteDictionaryRowItem row : rows){
            row.cell_text.setTextColor(color);
        }
    }

    public void setBackgrounds(Integer color){
        for(SQLiteDictionaryRowItem row : rows){
            row.cell.setBackgroundColor(color);
        }
    }

    public void setIsEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }

    private static class SQLiteDictionaryRowItem{
        RelativeLayout cell;
        TextView cell_text;
        ImageButton cell_remove_button;
        RadioButton cell_radio_button;
    }
}
