package com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Leaves;

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
 * Created by Neeraj on 02-Apr-16.
 */
public class View_Leaves extends AppCompatActivity {

    String username, password, user_type;
    ListView lvy,lvn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_leaves);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        lvy = (ListView) findViewById(R.id.lvy);
        lvn = (ListView) findViewById(R.id.lvn);

        DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_leaves));

        if (y.moveToFirst()) {
            ArrayList<String> ay = new ArrayList<>();
            ArrayList<String> an = new ArrayList<>();

            if (y.getString(5).equals("N"))
                an.add("FROM: " + y.getString(3) + " TO: " + y.getString(4));
            else
                ay.add("FROM: " + y.getString(3) + " TO: " + y.getString(4));

            if (!y.isLast()) {
                y.moveToNext();
                while (true) {
                    if (y.getString(5).equals("N"))
                        an.add("FROM: " + y.getString(3) + " TO: " + y.getString(4));
                    else
                        ay.add("FROM: " + y.getString(3) + " TO: " + y.getString(4));

                    if (y.isLast())
                        break;

                    y.moveToNext();
                }
            }

            ArrayAdapter adaptery = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ay);
            ArrayAdapter adaptern = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, an);
            lvy.setAdapter(adaptery);
            lvn.setAdapter(adaptern);
        } else {
            Message.message(View_Leaves.this, "Sorry You have No Applications");
            finish();
        }
    }
}
