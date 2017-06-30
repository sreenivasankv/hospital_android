package com.whysoserious.neeraj.hospitalmanagementsystem.Patient;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.whysoserious.neeraj.hospitalmanagementsystem.DatabaseHelper;
import com.whysoserious.neeraj.hospitalmanagementsystem.Message;
import com.whysoserious.neeraj.hospitalmanagementsystem.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Neeraj on 07-Apr-16.
 */
public class New_Appointment extends AppCompatActivity {

    String username, password, user_type, spl, slt, hs, he, ss;
    Spinner specialization, slot;
    int hsi, hei, tmp;

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_appointment);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        specialization = (Spinner) findViewById(R.id.spinner_specialization);
        slot = (Spinner) findViewById(R.id.spinner_slot);

        List<String> s_slot = new ArrayList<>();
        s_slot.add("Morning (9-12)");
        s_slot.add("Afternoon (1-4)");
        s_slot.add("Evening (5-9)");

        ArrayAdapter<String> adapter_slot = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, s_slot);
        adapter_slot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slot.setAdapter(adapter_slot);
    }

    public void onClick(View view) {

        Bundle b = new Bundle();
        b.putString("username", username);
        b.putString("password", password);
        b.putString("user_type", user_type);

        switch (view.getId()) {
            case R.id.b_refresh:
                slt = slot.getSelectedItem().toString();
                int r1, r2;

                //SELECTING RANGE FOR SLOT
                if (slt.charAt(0) == 'M') {
                    r1 = 9;
                    r2 = 12;
                } else if (slt.charAt(0) == 'A') {
                    r1 = 13;
                    r2 = 16;
                } else {
                    r1 = 17;
                    r2 = 21;
                }

                dbh = new DatabaseHelper(this);
                Cursor y = dbh.checkduplicates_in_user_credentials("", "", getResources().getString(R.string.all_doctor_slots));

                if (y.moveToFirst()) {
                    List<String> s_specialization = new ArrayList<>();
                    Set<String> set_s = new HashSet<>();

                    // CHECK SLOT
                    if (y.getString(5).equals("Y")) {

                        hs = y.getString(3);
                        hsi = ((int) hs.charAt(0) - (int) '0') * 10;
                        hsi += ((int) hs.charAt(1) - (int) '0');

                        he = y.getString(4);
                        hei = ((int) he.charAt(0) - (int) '0') * 10;
                        hei += ((int) he.charAt(1) - (int) '0');


                        if ((r1 <= hsi && r2 >= hsi) || (r1 <= hei && r2 >= hei)) {
                            ss = y.getString(2);

                            for (int i = 0; i < ss.length(); i++) {
                                if (ss.charAt(i) == '_') {
                                    if (i + 1 < ss.length()) {
                                        String ad = "";
                                        for (int j = i + 1; j < ss.length(); j++) {

                                            if (ss.charAt(j) == '_') {
                                                i = j - 1;
                                                break;
                                            }
                                            ad += ss.charAt(j);
                                        }

                                        if (ad.length() != 0)
                                            set_s.add(ad);
                                    }
                                }
                            }
                        }
                    }

                    if (!y.isLast()) {
                        y.moveToNext();
                        while (true) {
                            hs = y.getString(3);
                            hsi = ((int) hs.charAt(0) - (int) '0') * 10;
                            hsi += ((int) hs.charAt(1) - (int) '0');

                            he = y.getString(4);
                            hei = ((int) he.charAt(0) - (int) '0') * 10;
                            hei += ((int) he.charAt(1) - (int) '0');

                            if ((r1 <= hsi && r2 >= hsi) || (r1 <= hei && r2 >= hei)) {
                                ss = y.getString(2);

                                for (int i = 0; i < ss.length(); i++) {
                                    if (ss.charAt(i) == '_') {
                                        if (i + 1 < ss.length()) {
                                            String ad = "";
                                            for (int j = i + 1; j < ss.length(); j++) {

                                                if (ss.charAt(j) == '_') {
                                                    i = j - 1;
                                                    break;
                                                }
                                                ad += ss.charAt(j);
                                            }

                                            if (ad.length() != 0)
                                                set_s.add(ad);
                                        }
                                    }
                                }
                            }


                            if (y.isLast())
                                break;
                            y.moveToNext();
                        }
                    }


                    Iterator iterator = set_s.iterator();

                    while (iterator.hasNext()) {
                        s_specialization.add(String.valueOf(iterator.next()));
                    }

                    Message.message(New_Appointment.this, String.valueOf(s_specialization.size() + " specializations found"));


                    ArrayAdapter<String> adapter_spl = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, s_specialization);
                    adapter_spl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    specialization.setAdapter(adapter_spl);
                } else {
                    Message.message(New_Appointment.this, "Sorry no slots available");
                }
                break;
            case R.id.b_search :
                slt = slot.getSelectedItem().toString();
                spl = specialization.getSelectedItem().toString();
                b.putString("specialization", spl);
                b.putString("slot", slt);

                Intent i = new Intent(New_Appointment.this, Select_Doctor.class);
                i.putExtras(b);
                startActivity(i);
                break;
        }
    }
}
