package com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Leaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 02-Apr-16.
 */
public class Leaves extends AppCompatActivity {

    Spinner mm1, mm2;
    EditText dd1, dd2, yy1, yy2;
    String ds1, ds2, ms1, ms2, ys1, ys2, username, password, user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaves);

        dd1 = (EditText) findViewById(R.id.etdd1);
        dd2 = (EditText) findViewById(R.id.etdd2);
        yy1 = (EditText) findViewById(R.id.etyy1);
        yy2 = (EditText) findViewById(R.id.etyy2);
        mm1 = (Spinner) findViewById(R.id.spinnermonth1);
        mm2 = (Spinner) findViewById(R.id.spinnermonth2);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        List<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");

        ArrayAdapter<String> adapterm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mm1.setAdapter(adapterm);
        mm2.setAdapter(adapterm);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_previous:

                Bundle b1 = new Bundle();
                b1.putString("username",username);
                b1.putString("password",password);
                b1.putString("user_type",user_type);
                Intent i = new Intent(Leaves.this, View_Leaves.class);
                i.putExtras(b1);
                startActivity(i);
                break;
            default:
                ds1 = dd1.getText().toString();
                ds2 = dd2.getText().toString();
                ys1 = yy1.getText().toString();
                ys2 = yy2.getText().toString();
                ms1 = mm1.getSelectedItem().toString();
                ms2 = mm2.getSelectedItem().toString();

                if (ds1.equals("") || ds2.equals("") || ys1.equals("") || ys2.equals("")) {
                    Message.message(Leaves.this, "Please Fill in all your Details");
                } else {
                    //CHECK DUPLICATES EXIST OR NOT
                    if (ds1.length() == 1)
                        ds1 = "0" + ds1;

                    if (ds2.length() == 1)
                        ds2 = "0" + ds2;

                    String dobs1 = ds1 + " " + ms1 + " " + ys1;
                    String dobs2 = ds2 + " " + ms2 + " " + ys2;
                    String nn = "N";

                    DatabaseHelper dbh = new DatabaseHelper(this);
                    boolean b = dbh.insert_leaves(username, password, user_type, dobs1, dobs2, nn);

                    if (b) {
                        Message.message(Leaves.this, "Application Sent");
                    } else {
                        Message.message(Leaves.this, "This Application is Already Sent");
                    }
                    dbh.close();
                }
        }
    }
}
