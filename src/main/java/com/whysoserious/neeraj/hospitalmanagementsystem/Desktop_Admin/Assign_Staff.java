package com.whysoserious.neeraj.hospitalmanagementsystem.Desktop_Admin;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 09-Apr-16.
 */
public class Assign_Staff extends AppCompatActivity {

    Spinner s_doctor, s_staff, d, s;
    Button b_assign;
    DatabaseHelper dbh = new DatabaseHelper(this);
    List<String> d_u = new ArrayList<>();
    List<String> d_p = new ArrayList<>();
    List<String> s_u = new ArrayList<>();
    List<String> s_p = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_staff);

        s_doctor = (Spinner) findViewById(R.id.spinner_doctor);
        s_staff = (Spinner) findViewById(R.id.spinner_staff);
        b_assign = (Button) findViewById(R.id.b_assign);

        List<String> docs = new ArrayList<>();
        List<String> staff = new ArrayList<>();

        Cursor y = dbh.checkduplicates_in_user_credentials("", "", "get_all_doctors");

        if (y.moveToFirst()) {
            while (true) {
                if ((y.getString(7)).equals("Doctor")) {
                    docs.add("Dr. " + y.getString(1) + " " + y.getString(2));
                    d_u.add(y.getString(12));
                    d_p.add(y.getString(11));
                }

                if (y.isLast())
                    break;
                y.moveToNext();
            }
        }

        Cursor y1 = dbh.checkduplicates_in_user_credentials("", "", "get_all_doctors");

        if (y1.moveToFirst()) {
            while (true) {
                if (y1.getString(7).equals("Staff Member")) {
                    staff.add(y1.getString(1) + " " + y1.getString(2));
                    s_u.add(y1.getString(12));
                    s_p.add(y1.getString(11));
                }

                if (y1.isLast())
                    break;
                y1.moveToNext();
            }
        }

        ArrayAdapter adapter_d = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, docs);
        ArrayAdapter adapter_s = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, staff);
        adapter_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_doctor.setAdapter(adapter_d);
        s_staff.setAdapter(adapter_s);

        b_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = s_doctor.getSelectedItem().toString();
                String s = s_staff.getSelectedItem().toString();

                int id_d = s_doctor.getSelectedItemPosition();
                int id_s = s_staff.getSelectedItemPosition();

                Cursor z = dbh.checkduplicates_in_staff(s_u.get(id_s), s_p.get(id_s), d_u.get(id_d), d_p.get(id_d));

                if (z.moveToFirst()) {
                    Message.message(Assign_Staff.this, "Pair Already Assigned");
                } else {
                    boolean x = dbh.insert_staff(s_u.get(id_s), s_p.get(id_s), d_u.get(id_d), d_p.get(id_d), "Y");

                    if (x) {
                        Message.message(Assign_Staff.this, "Staff assigned");
                    } else {
                        Message.message(Assign_Staff.this, "Error occured");
                    }
                }
            }
        });
    }
}
