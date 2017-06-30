package com.whysoserious.neeraj.hospitalmanagementsystem;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.whysoserious.neeraj.hospitalmanagementsystem.Desktop_Admin.Desktop_Admin;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Doctor;
import com.whysoserious.neeraj.hospitalmanagementsystem.Patient.Patient;
import com.whysoserious.neeraj.hospitalmanagementsystem.Staff_Member.Staff_Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 17-Mar-16.
 */
public class Register extends AppCompatActivity {

    EditText fname, lname, age, dd, yy, city, pincode, mobno, uname, password;
    String fnames, lnames, ages, sexs, bgroups, dobs, dds, yys, mms, citys, pincodes, mobnos, unames, passwords, utypes;
    Button register;
    Spinner usertype, mm, sex, bgroup;

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //DEFINING ALL VIEWS
        fname = (EditText) findViewById(R.id.etfname);
        lname = (EditText) findViewById(R.id.etlname);
        age = (EditText) findViewById(R.id.etage);
        dd = (EditText) findViewById(R.id.etdd);
        yy = (EditText) findViewById(R.id.etyy);
        city = (EditText) findViewById(R.id.etcity);
        pincode = (EditText) findViewById(R.id.etpin);
        mobno = (EditText) findViewById(R.id.etmobile);
        uname = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        register = (Button) findViewById(R.id.bregister);
        usertype = (Spinner) findViewById(R.id.spinnerusertype);
        mm = (Spinner) findViewById(R.id.spinnermonth);
        sex = (Spinner) findViewById(R.id.spinnersex);
        bgroup = (Spinner) findViewById(R.id.spinnerbgroup);
        dbh = new DatabaseHelper(this);

        //SET UP THE SPINNER DROOPDOWN
        List<String> category = new ArrayList<>();
        category.add("Patient");
        category.add("Doctor");
        category.add("Staff Member");
        category.add("Desktop Admin");

        List<String> sexc = new ArrayList<>();
        sexc.add("Male");
        sexc.add("Female");

        List<String> bgroupc = new ArrayList<>();
        bgroupc.add("A+");
        bgroupc.add("A-");
        bgroupc.add("B+");
        bgroupc.add("B-");
        bgroupc.add("O+");
        bgroupc.add("O-");
        bgroupc.add("AB+");
        bgroupc.add("AB-");

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, category);
        ArrayAdapter<String> adapterm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bgroupc);
        ArrayAdapter<String> adapterb = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexc);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usertype.setAdapter(adapter);
        mm.setAdapter(adapterm);
        sex.setAdapter(adapters);
        bgroup.setAdapter(adapterb);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lnames = lname.getText().toString();
                fnames = fname.getText().toString();
                ages = age.getText().toString();
                dds = dd.getText().toString();
                yys = yy.getText().toString();
                citys = city.getText().toString();
                pincodes = pincode.getText().toString();
                unames = uname.getText().toString();
                passwords = password.getText().toString();
                mobnos = mobno.getText().toString();
                utypes = usertype.getSelectedItem().toString();
                mms = mm.getSelectedItem().toString();
                sexs = sex.getSelectedItem().toString();
                bgroups = bgroup.getSelectedItem().toString();

                if (fnames.equals("") || lnames.equals("") || ages.equals("") || dds.equals("") ||
                        yys.equals("") || citys.equals("") || pincodes.equals("") || unames.equals("") ||
                        passwords.equals("") || mobnos.equals("")) {
                    Message.message(Register.this, "Please Fill in all your Details");
                } else {

                    //CHECK WHETHER THE ENTRY ALREADY EXISTS
                    Cursor y = dbh.checkduplicates_in_user_credentials(unames, passwords, getResources().getString(R.string.user_credentials));
                    if (y.moveToFirst()) {
                        Message.message(Register.this, "User Already Exists");
                        Message.message(Register.this, "Login With Your Username and Password");
                        finish();
                    } else {
                        //SETUP DATABASE QUERY
                        if (dds.length() == 1)
                            dds = "0" + dds;
                        dobs = dds + " " + mms + " " + yys;

                        boolean b = dbh.insert_user_credentials(fnames, lnames, ages, dobs, citys, pincodes, unames, passwords, mobnos, utypes, sexs, bgroups);
                        if (b) {
                            Intent i;
                            Bundle bb = new Bundle();
                            bb.putString("username", unames);
                            bb.putString("password", passwords);
                            bb.putString("user_type", utypes);

                            if (utypes.equals("Patient")) {
                                i = new Intent(Register.this, Patient.class);
                            } else if (utypes.equals("Doctor")) {
                                i = new Intent(Register.this, Doctor.class);
                            } else if (utypes.equals("Staff Member")) {
                                i = new Intent(Register.this, Staff_Member.class);
                            } else {
                                i = new Intent(Register.this, Desktop_Admin.class);
                            }

                            i.putExtras(bb);
                            startActivity(i);
                            finish();
                        }
                    }
                }
            }
        });
    }
}
