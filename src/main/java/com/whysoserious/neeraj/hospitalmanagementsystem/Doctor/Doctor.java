package com.whysoserious.neeraj.hospitalmanagementsystem.Doctor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Doctor_Patient.Report_Upload;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Leaves.Leaves;
import com.whysoserious.neeraj.hospitalmanagementsystem.Feedback;
import com.whysoserious.neeraj.hospitalmanagementsystem.Personal_Info;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

/**
 * Created by Neeraj on 17-Mar-16.
 */
public class Doctor extends AppCompatActivity {

    String username,password,user_type;
    DatabaseHelper dbh;
    TextView dname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor);

        dbh = new DatabaseHelper(this);
        dname = (TextView) findViewById(R.id.tv_d_name);


        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        Cursor y = dbh.checkduplicates_in_user_credentials(username, password,getResources().getString(R.string.user_credentials));

        if (y.moveToFirst()) {
            String name = y.getString(1);
            dname.setText("Welcome Dr. "+name);
        }
    }

    public void onClick(View view){

        Intent i;
        Bundle b = new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        b.putString("user_type",user_type);

        switch (view.getId())
        {
            case R.id.b_d_info:
                i = new Intent(Doctor.this, Personal_Info.class);
                break;
            case R.id.b_add_specialization:
                i = new Intent(Doctor.this, Specialization.class);
                break;
            case R.id.b_d_leaves:
                i = new Intent(Doctor.this, Leaves.class);
                break;
            case R.id.b_d_upload_report:
                i = new Intent(Doctor.this, Report_Upload.class);
                break;
            case R.id.b_d_staff_assigned:
                i = new Intent(Doctor.this, View_Assigned_Staff.class);
                break;
            case R.id.b_d_slot:
                i = new Intent(Doctor.this, D_Slot.class);
                break;
            default:
                i = new Intent(Doctor.this, Feedback.class);
                break;
        }
        i.putExtras(b);
        startActivity(i);
    }
}