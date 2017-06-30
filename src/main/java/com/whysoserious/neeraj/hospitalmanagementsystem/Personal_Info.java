package com.whysoserious.neeraj.hospitalmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.whysoserious.neeraj.hospitalmanagementsystem.Patient.Confirmed_Appointment;
import com.whysoserious.neeraj.hospitalmanagementsystem.Patient.New_Appointment;
import com.whysoserious.neeraj.hospitalmanagementsystem.Patient.Wait_Appointment;

/**
 * Created by Neeraj on 01-Apr-16.
 */
public class Personal_Info extends AppCompatActivity {

    String username,password,user_type;
    DatabaseHelper db;
    TextView name,age,sex,dob,bgroup,utype,city,pincode,mobno,uname,pword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        db = new DatabaseHelper(this);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("password");

        name = (TextView) findViewById(R.id.name);
        age = (TextView) findViewById(R.id.age);
        sex = (TextView) findViewById(R.id.sex);
        dob = (TextView) findViewById(R.id.dob);
        bgroup = (TextView) findViewById(R.id.bgroup);
        utype = (TextView) findViewById(R.id.utype);
        city = (TextView) findViewById(R.id.city);
        pincode = (TextView) findViewById(R.id.pincode);
        mobno = (TextView) findViewById(R.id.tv_mno);
        uname = (TextView) findViewById(R.id.username);
        pword = (TextView) findViewById(R.id.password);

        Cursor y = db.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.user_credentials));

        if (y.moveToFirst()) {
            String name1 = y.getString(1);
            String name2 = y.getString(2);

            name.setText(name1+" "+name2);
            age.setText(y.getString(3));
            sex.setText(y.getString(6));
            dob.setText(y.getString(5));
            bgroup.setText(y.getString(4));
            utype.setText(y.getString(7));
            city.setText(y.getString(8));
            pincode.setText(y.getString(9));
            mobno.setText(y.getString(10));
            uname.setText(y.getString(12));
            pword.setText(y.getString(11));
        }
    }

    public void onClick(View view){

        Intent i;
        Bundle b = new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        b.putString("user_type",user_type);

        i = new Intent(Personal_Info.this, Update.class);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}
