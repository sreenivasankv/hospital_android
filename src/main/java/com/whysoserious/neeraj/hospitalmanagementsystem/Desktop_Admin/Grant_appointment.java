package com.whysoserious.neeraj.hospitalmanagementsystem.Desktop_Admin;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.whysoserious.neeraj.hospitalmanagementsystem.CustomListViewAdapter;
import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;
import com.whysoserious.neeraj.hospitalmanagementsystem.RowItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 08-Apr-16.
 */
public class Grant_appointment extends AppCompatActivity {

    ListView lv_appointment;
    List<String> u_p;
    List<String> p_p;
    List<RowItem> rowItems;
    ArrayList<String> doc = new ArrayList<>();
    ArrayList<String> pat = new ArrayList<>();
    ArrayList<String> pro = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grant_appointment);

        lv_appointment = (ListView) findViewById(R.id.lv_pending_appontments);
        u_p = new ArrayList<>();
        p_p = new ArrayList<>();

        final DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials("", "", getResources().getString(R.string.all_pending_appointment));

        if (y.moveToFirst()) {
            while (true) {

                //pateinet approvl has three mode W - wait, A - approved, F - finished

                if (y.getString(4).equals("W")) {
                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));
                    DatabaseHelper dbh2 = new DatabaseHelper(this);
                    Cursor z2 = dbh2.checkduplicates_in_user_credentials(y.getString(2), y.getString(3), getResources().getString(R.string.user_credentials));
                    u_p.add(y.getString(0));
                    p_p.add(y.getString(1));

                    if (z1.moveToNext()) {
                        pat.add(z1.getString(1) + " " + z1.getString(2));
                    }

                    if (z2.moveToNext()) {
                        doc.add(z2.getString(1) + " " + z2.getString(2));
                    }
                    pro.add(y.getString(5));

                    dbh1.close();
                    dbh2.close();
                }

                if (y.isLast())
                    break;

                y.moveToNext();
            }

            rowItems = new ArrayList<>();

            for (int i = 0; i < doc.size(); i++) {
                RowItem item = new RowItem(doc.get(i), pat.get(i), pro.get(i));
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.custom_adapter, rowItems);
            lv_appointment.setAdapter(adapter);
        } else {
            Message.message(Grant_appointment.this, "No Pending Apppointments");
            finish();
        }

        lv_appointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor x = dbh.checkduplicates_in_user_credentials(u_p.get(position), p_p.get(position), pro.get(position));
                boolean y = false;
                if (x.moveToFirst()) {
                    y = dbh.update_doctor_patient(x.getString(0), x.getString(1), x.getString(2), x.getString(3), "A", x.getString(5), x.getString(6), x.getString(7));
                }

                if (y) {
                    Message.message(Grant_appointment.this, "Application Approved");
                    finish();
                } else {
                    Message.message(Grant_appointment.this, "Not Approved");
                }
            }
        });
    }
}
