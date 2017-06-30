package com.whysoserious.neeraj.hospitalmanagementsystem.Patient;

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
public class Bills extends AppCompatActivity {

    String username, password, user_type;
    DatabaseHelper dbh = new DatabaseHelper(this);
    ArrayList<String> d_name = new ArrayList<>();
    ListView lv_bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bills);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        lv_bills = (ListView) findViewById(R.id.lv_bills);
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, "patient_identify");

        if (y.moveToFirst()) {
            while (true) {
                if (y.getString(4).equals("F")) {

                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(2), y.getString(3), getResources().getString(R.string.user_credentials));

                    if (z1.moveToNext()) {
                        d_name.add("Dr. "+z1.getString(1) + " " + z1.getString(2) +"  ( 1000/- )");
                    }
                }

                if (y.isLast())
                    break;
                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, d_name);
            lv_bills.setAdapter(adapter);
        } else {
            Message.message(Bills.this, "Sorry You have No Bills Right, Now");
            finish();
        }
    }
}
