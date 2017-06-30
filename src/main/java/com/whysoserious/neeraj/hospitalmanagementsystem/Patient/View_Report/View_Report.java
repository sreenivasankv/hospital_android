package com.whysoserious.neeraj.hospitalmanagementsystem.Patient.View_Report;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Doctor_Patient.Write_Report;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

import java.util.ArrayList;

/**
 * Created by Neeraj on 08-Apr-16.
 */
public class View_Report extends AppCompatActivity {

    ListView lv_report;
    String username, password, user_type;
    DatabaseHelper dbh = new DatabaseHelper(this);
    ArrayList<String> p_problem = new ArrayList<>();
    ArrayList<String> p_report = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        lv_report = (ListView) findViewById(R.id.lv_reports);
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, "patient_identify");

        if (y.moveToFirst()) {
            while (true) {
                if (y.getString(4).equals("F")) {
                    p_problem.add(y.getString(5));
                    p_report.add(y.getString(7));
                }

                if (y.isLast())
                    break;
                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, p_problem);
            lv_report.setAdapter(adapter);
        } else {
            Message.message(View_Report.this, "Sorry You have No Reports Right, Now");
            finish();
        }

        lv_report.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i;
                Bundle b = new Bundle();
                b.putString("report", p_report.get(position));

                i = new Intent(View_Report.this, Final_View_Report.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
