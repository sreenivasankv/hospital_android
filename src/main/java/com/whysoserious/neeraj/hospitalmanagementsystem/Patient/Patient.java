package com.whysoserious.neeraj.hospitalmanagementsystem.Patient;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Feedback;
import com.whysoserious.neeraj.hospitalmanagementsystem.Patient.View_Report.View_Report;
import com.whysoserious.neeraj.hospitalmanagementsystem.Personal_Info;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

/**
 * Created by Neeraj on 17-Mar-16.
 */
public class Patient extends AppCompatActivity {

    String username, password, user_type;
    DatabaseHelper dbh;
    TextView pname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient);

        dbh = new DatabaseHelper(this);
        pname = (TextView) findViewById(R.id.tv_p_name);


        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.user_credentials));

        if (y.moveToFirst()) {
            String name = y.getString(1);
            pname.setText(name);
        }
    }

    public void onClick(View view) {

        Intent i;
        Bundle b = new Bundle();
        b.putString("username", username);
        b.putString("password", password);
        b.putString("user_type", user_type);

        switch (view.getId()) {
            case R.id.b_p_info:
                i = new Intent(Patient.this, Personal_Info.class);
                break;
            case R.id.b_p_appointment:
                i = new Intent(Patient.this, Appointment.class);
                break;
            case R.id.b_p_report:
                i = new Intent(Patient.this, View_Report.class);
                break;
            case R.id.b_p_bills:
                i = new Intent(Patient.this, Bills.class);
                break;
            default:
                i = new Intent(Patient.this, Feedback.class);
                break;
        }

        i.putExtras(b);
        startActivity(i);
    }
}
