package com.whysoserious.neeraj.hospitalmanagementsystem.Patient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.whysoserious.neeraj.hospitalmanagementsystem.R;

/**
 * Created by Neeraj on 07-Apr-16.
 */
public class Confirmed_Appointment extends AppCompatActivity {

    String username,password,user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmed_appointment);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");
    }
}
