package com.whysoserious.neeraj.hospitalmanagementsystem.Desktop_Admin;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

import java.util.ArrayList;

/**
 * Created by Neeraj on 08-Apr-16.
 */
public class Remaining_patient_bills extends AppCompatActivity {

    String username, password, user_type;
    DatabaseHelper dbh = new DatabaseHelper(this);
    ArrayList<String> p_name = new ArrayList<>();
    ListView lv_remiaing_bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remaining_patient_bills);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        lv_remiaing_bills = (ListView) findViewById(R.id.lv_remaining_bills);
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, "all_pending_appointment");

        if (y.moveToFirst()) {
            while (true) {
                if (y.getString(4).equals("F")) {
                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));

                    if (z1.moveToNext()) {
                        p_name.add(z1.getString(1) + " " + z1.getString(2) + "  ( 1000/- )");
                    }
                }

                if (y.isLast())
                    break;
                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, p_name);
            lv_remiaing_bills.setAdapter(adapter);
        } else {
            Message.message(Remaining_patient_bills.this, " No Patient with unpaid Bills");
            finish();
        }
    }
}
