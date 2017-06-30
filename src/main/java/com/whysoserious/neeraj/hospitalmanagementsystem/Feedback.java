package com.whysoserious.neeraj.hospitalmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.D_Slot;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Doctor_Patient.Report_Upload;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Leaves.Leaves;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.Specialization;
import com.whysoserious.neeraj.hospitalmanagementsystem.Doctor.View_Assigned_Staff;

import java.util.ArrayList;

/**
 * Created by Neeraj on 02-Apr-16.
 */
public class Feedback extends AppCompatActivity {

    ListView lv_feed;
    EditText et_feed;
    String username, password, user_type, tmp;
    ArrayList<String> feedback = new ArrayList<>();

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        lv_feed = (ListView) findViewById(R.id.lv_feedback);
        et_feed = (EditText) findViewById(R.id.et_feedback);


        Cursor y = db.checkduplicates_in_user_credentials(username, password, "FEEDBACK");

        if (y.moveToFirst()) {
            while (true) {
                feedback.add(y.getString(2));

                if (y.isLast())
                    break;
                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedback);
            lv_feed.setAdapter(adapter);
        }

    }

    public void onClick(View view) {
        tmp = et_feed.getText().toString();
        if (tmp.length() == 0) {
            Message.message(Feedback.this, "Please type your feedback");
        } else {
            boolean b = db.insert_feedback(username, password, tmp);

            if (b) {
                Message.message(Feedback.this, "Feedback Submitted");
                finish();
            } else {
                Message.message(Feedback.this, "Feedback cannot be inserted");
            }
        }
    }
}