package com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Doctor_Patient;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Neeraj on 02-Apr-16.
 */
public class Report_Upload extends AppCompatActivity {

    String username, password, user_type;
    ArrayList<String> p_name = new ArrayList<>();
    ArrayList<String> p_u = new ArrayList<>();
    ArrayList<String> p_p = new ArrayList<>();
    ArrayList<String> p_f = new ArrayList<>();
    ArrayList<String> p_problem = new ArrayList<>();
    ArrayList<Map<String,String>> list = new ArrayList<>();
    ListView lv_patients;

    DatabaseHelper dbh = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_upload);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        lv_patients = (ListView) findViewById(R.id.lv_current_patients);

        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_patient));

        if (y.moveToFirst()) {
            while (true) {
                if (y.getString(4).equals("A")) {

                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));

                    p_u.add(y.getString(0));
                    p_p.add(y.getString(1));
                    p_f.add(y.getString(6));
                    if (z1.moveToNext()) {
                        p_name.add(z1.getString(1) + " " + z1.getString(2));
                    }

                    p_problem.add(y.getString(5));

                    dbh1.close();
                }

                if (y.isLast())
                    break;

                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, p_name);
            lv_patients.setAdapter(adapter);
        }
        else {
            Message.message(Report_Upload.this, "Sorry You have No Patients Right, Now");
            finish();
        }

        lv_patients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i;
                Bundle b = new Bundle();
                b.putString("username",username);
                b.putString("password",password);
                b.putString("user_type",user_type);
                b.putString("p_username",p_u.get(position));
                b.putString("p_password",p_p.get(position));
                b.putString("problem",p_problem.get(position));
                b.putString("fees",p_f.get(position));

                i = new Intent(Report_Upload.this,Write_Report.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
