package com.whysoserious.neeraj.hospitalmanagementsystem.Doctor;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

import java.util.ArrayList;

/**
 * Created by Neeraj on 03-Apr-16.
 */

public class D_Slot extends AppCompatActivity {

    Spinner ss, ts, se, te;
    String s, e, hr, ap, username, password, user_type;
    TextView tvs,tve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_slot);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        ss = (Spinner) findViewById(R.id.ss);
        ts = (Spinner) findViewById(R.id.ts);
        se = (Spinner) findViewById(R.id.se);
        te = (Spinner) findViewById(R.id.te);
        tvs = (TextView) findViewById(R.id.tv_current_slot_s);
        tve = (TextView) findViewById(R.id.tv_current_slot_e);

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor y = db.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_slot));
        if (y.moveToFirst()){
            tvs.setText(y.getString(3));
            tve.setText(y.getString(4));
        }

        ArrayList<String> hour = new ArrayList<>();
        ArrayList<String> ampm = new ArrayList<>();
        ArrayList<String> shour = new ArrayList<>();

        String ss1,ss2;
        for (int i = 9; i <= 21; i++) {

            ss1 = Integer.toString(i);
            if (ss1.length() == 1)
                ss1 = "0" + ss1;

            hour.add(ss1);
        }


        ampm.add("AM");
        ampm.add("PM");

        ArrayAdapter<String> adapterh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hour);

        ArrayAdapter<String> adaptera = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ampm);
        adapterh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adaptera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ss.setAdapter(adapterh);
        ts.setAdapter(adaptera);
        se.setAdapter(adapterh);
        te.setAdapter(adaptera);
    }

    public void onClick(View view) {

        s = ss.getSelectedItem().toString();
        s+= " ";
        s += ts.getSelectedItem().toString();
        e = se.getSelectedItem().toString();
        e+= " ";
        e += te.getSelectedItem().toString();

        tvs.setText(s);
        tve.setText(e);

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor y = db.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_slot));

        if (y.moveToFirst()) {
            boolean b = db.update_slot(username, password, y.getString(2), s, e, "Y");
            if (b) {
                Message.message(D_Slot.this, "Your Slot Has been Updated");
            } else {
                Message.message(D_Slot.this, "Some Error Occured, Try Again");
            }
        } else {
            boolean b = db.insert_slot(username, password, "", s, e, "Y");
            if (b) {
                Message.message(D_Slot.this, "Your Slot Has been Inserted");
            } else {
                Message.message(D_Slot.this, "Some Error Occured, Try Again");
            }
        }

        db.close();
    }
}
