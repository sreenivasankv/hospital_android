package com.whysoserious.neeraj.hospitalmanagementsystem.Patient;

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
import java.util.List;

/**
 * Created by Neeraj on 08-Apr-16.
 */
public class Select_Doctor extends AppCompatActivity {

    String username, password, user_type, specialization, slot, ss, name, hs, he;
    ListView lv_doctors;
    List<String> s_doctors;
    List<String> u_d;
    List<String> p_d;
    int r1, r2, hsi, hei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_doctor);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");
        specialization = bb.getString("specialization");
        slot = bb.getString("slot");


        //SELECTING RANGE FOR SLOT
        if (slot.charAt(0) == 'M') {
            r1 = 9;
            r2 = 12;
        } else if (slot.charAt(0) == 'A') {
            r1 = 13;
            r2 = 16;
        } else {
            r1 = 17;
            r2 = 21;
        }

        lv_doctors = (ListView) findViewById(R.id.lv_doctors_available);
        s_doctors = new ArrayList<>();
        u_d = new ArrayList<>();
        p_d = new ArrayList<>();

        DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials("", "", getResources().getString(R.string.all_doctor_slots));

        if (y.moveToFirst()) {
            // CHECK SLOT
            if (y.getString(5).equals("Y")) {
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

                            hs = y.getString(3);
                            hsi = ((int) hs.charAt(0) - (int) '0') * 10;
                            hsi += ((int) hs.charAt(1) - (int) '0');

                            he = y.getString(4);
                            hei = ((int) he.charAt(0) - (int) '0') * 10;
                            hei += ((int) he.charAt(1) - (int) '0');


                            if (ad.length() != 0 && (ad.toString()).equals(specialization) && ((r1 <= hsi && r2 >= hsi) || (r1 <= hei && r2 >= hei))) {
                                DatabaseHelper dbh1 = new DatabaseHelper(this);
                                Cursor z = dbh1.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));

                                if (z.moveToFirst()) {
                                    name = "Dr. " + z.getString(1) + " " + z.getString(2);
                                    s_doctors.add(name);
                                    u_d.add(z.getString(12));
                                    p_d.add(z.getString(11));
                                }
                            }
                        }
                    }
                }

                if (!y.isLast()) {
                    y.moveToNext();
                    while (true) {

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

                                    hs = y.getString(3);
                                    hsi = ((int) hs.charAt(0) - (int) '0') * 10;
                                    hsi += ((int) hs.charAt(1) - (int) '0');

                                    he = y.getString(4);
                                    hei = ((int) he.charAt(0) - (int) '0') * 10;
                                    hei += ((int) he.charAt(1) - (int) '0');

                                    if (ad.length() != 0 && specialization.equals(ad) && ((r1 <= hsi && r2 >= hsi) || (r1 <= hei && r2 >= hei))) {
                                        DatabaseHelper dbh1 = new DatabaseHelper(this);
                                        Cursor z = dbh1.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));
                                        if (z.moveToFirst()) {
                                            name = "Dr. " + z.getString(1) + " " + z.getString(2);
                                            s_doctors.add(name);
                                            u_d.add(z.getString(12));
                                            p_d.add(z.getString(11));
                                        }
                                    }
                                }
                            }
                        }
                        if (y.isLast())
                            break;
                        y.moveToNext();
                    }
                }

                if (s_doctors.size() > 0) {
                    ArrayAdapter adapter_doc = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, s_doctors);
                    lv_doctors.setAdapter(adapter_doc);
                } else {
                    Message.message(Select_Doctor.this, "Sorry, You have No Specialization");
                }
            } else {
                Message.message(Select_Doctor.this, "Sorry no Doctor's available");
            }
        }


        //ON ITEM CLICK LISTENER FOR DOCTORS

        lv_doctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Select_Doctor.this, Apply.class);
                Bundle b = new Bundle();
                b.putString("d_username", u_d.get(position));
                b.putString("d_password", p_d.get(position));
                b.putString("username", username);
                b.putString("password", password);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
