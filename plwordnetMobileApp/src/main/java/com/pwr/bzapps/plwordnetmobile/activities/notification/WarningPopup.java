package com.pwr.bzapps.plwordnetmobile.activities.notification;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.pwr.bzapps.plwordnetmobile.R;

import java.util.ArrayList;
import java.util.List;

public class WarningPopup {

    private AlertDialog dialog;
    private List<Button> buttons;

    public WarningPopup(Context context,
                        LayoutInflater inflater,
                        String title,
                        String reason,
                        String dismissText) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View convertView = (View) inflater.inflate(R.layout.warning_popup, null);
        TextView titleB = (TextView) convertView.findViewById(R.id.title);
        TextView reasonB = (TextView) convertView.findViewById(R.id.reason);
        buttons = new ArrayList<>();
        Button dismissButton = (Button) convertView.findViewById(R.id.ok_button);
        titleB.setText(title);
        reasonB.setText(reason);
        dismissButton.setText(dismissText);
        builder.setView(convertView);
        dialog = builder.create();
        buttons.add(dismissButton);
    }

    public WarningPopup(Context context,
                        LayoutInflater inflater,
                        String title,
                        String reason,
                        String redButton,
                        String greyButton) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View convertView = (View) inflater.inflate(R.layout.warning_two_buttons_popup, null);
        TextView titleB = (TextView) convertView.findViewById(R.id.title);
        TextView reasonB = (TextView) convertView.findViewById(R.id.reason);
        buttons = new ArrayList<>();
        Button red_button = (Button) convertView.findViewById(R.id.button_red);
        Button grey_button = (Button) convertView.findViewById(R.id.button_grey);
        titleB.setText(title);
        reasonB.setText(reason);
        red_button.setText(redButton);
        grey_button.setText(greyButton);
        builder.setView(convertView);
        dialog = builder.create();
        buttons.add(red_button);
        buttons.add(grey_button);
    }

    public void show(){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public void addOnClickListener(int buttonId, View.OnClickListener onClickListener){
        if(buttonId<buttons.size()) {
            Button button = buttons.get(buttonId);
            button.setOnClickListener(onClickListener);
        }
    }
}
