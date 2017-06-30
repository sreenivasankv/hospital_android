package com.whysoserious.neeraj.hospitalmanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Neeraj on 18-Mar-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "HMS_DATABASE.db";
    private static final String TABLE_NAME_USER = "USER_CREDENTIALS";
    private static final String TABLE_NAME_D_LEAVES = "DOCTOR_LEAVES";
    private static final String TABLE_NAME_D_SLOT = "DOCTOR_SLOT";
    private static final String TABLE_NAME_DOCTOR_PATIENT = "DOCTOR_PATIENT";
    private static final String TABLE_NAME_STAFF = "STAFF";
    private static final String TABLE_NAME_FEEDBACK = "FEEDBACK";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TABLE FOR USER CREDENTIAL
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME_USER + " (" +
                            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "first_name VARCHAR," +
                            "last_name VARCHAR," +
                            "age VARCHAR," +
                            "sex VARCHAR," +
                            "dob VARCHAR," +
                            "blood_group VARCHAR," +
                            "u_type VARCHAR," +
                            "city VARCHAR," +
                            "pincode VARCHAR," +
                            "mobile_number VARCHAR," +
                            "password VARCHAR," +
                            "username VARCHAR);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************************TABLE FOR DOCTOR LEAVES**************************************
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME_D_LEAVES + " (" +
                            "username VARCHAR," +
                            "password VARCHAR," +
                            "user_type VARCHAR," +
                            "date_from VARCHAR," +
                            "date_to VARCHAR," +
                            "approval VARCHAR);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************************TABLE FOR DOCTOR SLOTS**************************************
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME_D_SLOT + " (" +
                            "username VARCHAR," +
                            "password VARCHAR," +
                            "specialization VARCHAR," +
                            "slot_from VARCHAR," +
                            "slot_to VARCHAR," +
                            "available VARCHAR);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************************TABLE FOR DOCTOR PATIENT**************************************
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME_DOCTOR_PATIENT + " (" +
                            "p_username VARCHAR," +
                            "p_password VARCHAR," +
                            "d_username VARCHAR," +
                            "d_password VARCHAR," +
                            "granted VARCHAR," +
                            "problem VARCHAR," +
                            "fees_paid VARCHAR," +
                            "report VARCHAR);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //*****************************TABLE FOR STAFF **************************************
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME_STAFF + " (" +
                            "S_username VARCHAR," +
                            "S_password VARCHAR," +
                            "d_username VARCHAR," +
                            "d_password VARCHAR," +
                            "assigned VARCHAR);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME_FEEDBACK + " (" +
                            "username VARCHAR," +
                            "password VARCHAR," +
                            "feedback VARCHAR);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_D_LEAVES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_D_SLOT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DOCTOR_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STAFF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FEEDBACK);
        onCreate(db);
    }

    //*************************************USER CREDENTIALS TABLE* *********************************************************
    //CHECHK THAT THE REGISTERED USER ALREADY EXIST ******** AND RETURNS ALL FAVOURABLE VALUES

    //CURSUR RETRUN FUNCTION
    public Cursor checkduplicates_in_user_credentials(String user_name, String password, String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;

        if (table.equals(TABLE_NAME_D_LEAVES)) {
            res = db.rawQuery("select * from " + TABLE_NAME_D_LEAVES + " where username=? and password=?", new String[]{user_name, password});
        } else if (table.equals(TABLE_NAME_D_SLOT)) {
            res = db.rawQuery("select * from " + TABLE_NAME_D_SLOT + " where username=? and password=?", new String[]{user_name, password});
        } else if (table.equals(TABLE_NAME_USER)) {
            res = db.rawQuery("select * from " + TABLE_NAME_USER + " where username=? and password=?", new String[]{user_name, password});
        } else if (table.equals(TABLE_NAME_DOCTOR_PATIENT)) {
            res = db.rawQuery("select * from " + TABLE_NAME_DOCTOR_PATIENT + " where d_username=? and d_password=?", new String[]{user_name, password});
        } else if (table.equals("get_all_doctors")) {
            res = db.rawQuery("select * from " + TABLE_NAME_USER, new String[]{});
        } else if (table.equals("all_doctor_slots")) {
            res = db.rawQuery("select * from " + TABLE_NAME_D_SLOT, new String[]{});
        } else if (table.equals(TABLE_NAME_STAFF)) {
            res = db.rawQuery("select * from " + TABLE_NAME_STAFF, new String[]{});
        } else if (table.equals("all_pending_appointment")) {
            res = db.rawQuery("select * from " + TABLE_NAME_DOCTOR_PATIENT, new String[]{});
        } else if (table.equals("patient_identify")) {
            res = db.rawQuery("select * from " + TABLE_NAME_DOCTOR_PATIENT + " where p_username=? and p_password=?", new String[]{user_name, password});
        } else if (table.equals(TABLE_NAME_FEEDBACK)) {
            res = db.rawQuery("select * from " + TABLE_NAME_FEEDBACK + " where username=? and password=?", new String[]{user_name, password});
        } else if (table.equals("all_feedback")) {
            res = db.rawQuery("select * from " + TABLE_NAME_FEEDBACK, new String[]{});
        } else {
            res = db.rawQuery("select * from " + TABLE_NAME_DOCTOR_PATIENT + " where p_username=? and p_password=? and problem=?", new String[]{user_name, password, table});
        }
        return res;
    }

    //INSERT INTO USER CREDENTIALS check duplication or alredy exists
    public boolean insert_user_credentials(String fnames, String lnames, String ages, String dobs, String citys, String pincodes, String unames, String passwords, String mobnos, String utypes, String sexs, String bgroups) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", fnames);
        contentValues.put("last_name", lnames);
        contentValues.put("age", ages);
        contentValues.put("sex", sexs);
        contentValues.put("blood_group", bgroups);
        contentValues.put("dob", dobs);
        contentValues.put("city", citys);
        contentValues.put("pincode", pincodes);
        contentValues.put("u_type", utypes);
        contentValues.put("mobile_number", mobnos);
        contentValues.put("username", unames);
        contentValues.put("password", passwords);

        long l = db.insert(TABLE_NAME_USER, null, contentValues);

        if (l != -1) {
            Message.message(context, "new entry inserted");
            return true;
        } else {
            Message.message(context, "Registration Failed");
            return false;
        }
    }

    public boolean update_user_credentials(String ou, String op, String fnames, String lnames, String ages, String dobs, String citys, String pincodes, String unames, String passwords, String mobnos, String utypes, String sexs, String bgroups) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", fnames);
        contentValues.put("last_name", lnames);
        contentValues.put("age", ages);
        contentValues.put("sex", sexs);
        contentValues.put("blood_group", bgroups);
        contentValues.put("dob", dobs);
        contentValues.put("city", citys);
        contentValues.put("pincode", pincodes);
        contentValues.put("u_type", utypes);
        contentValues.put("mobile_number", mobnos);
        contentValues.put("username", unames);
        contentValues.put("password", passwords);

        long l = db1.update(TABLE_NAME_USER, contentValues, "username=? and password=?", new String[]{ou, op});
        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean delete_user_credentials(String ou, String op) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        long l = db1.delete(TABLE_NAME_USER, "username=? and password=?", new String[]{ou, op});

        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }

    //*************************************************DOCTOR LEAVES TABLE ********************************************************
    //insert leaves

    public boolean insert_leaves(String username, String password, String user_type, String dfrom, String dto, String approval) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("user_type", user_type);
        contentValues.put("date_from", dfrom);
        contentValues.put("date_to", dto);
        contentValues.put("approval", approval);

        long l = db1.insert(TABLE_NAME_D_LEAVES, null, contentValues);

        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }

    //**********************************************DOCTOR SLOT TABLE ***********************************************************
    //insert slots

    public boolean insert_slot(String username, String password, String specialization, String dfrom, String dto, String available) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("specialization", specialization);
        contentValues.put("slot_from", dfrom);
        contentValues.put("slot_to", dto);
        contentValues.put("available", available);

        long l = db1.insert(TABLE_NAME_D_SLOT, null, contentValues);
        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean update_slot(String username, String password, String specialization, String dfrom, String dto, String available) {

        SQLiteDatabase db1 = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("specialization", specialization);
        contentValues.put("slot_from", dfrom);
        contentValues.put("slot_to", dto);
        contentValues.put("available", available);

        long l = db1.update(TABLE_NAME_D_SLOT, contentValues, "username=? and password=?", new String[]{username, password});
        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }


    //**********************************************DOCTOR SLOT TABLE ***********************************************************
    //insert appointment

    public boolean insert_doctor_patient(String p_username, String p_password, String d_username, String d_password, String granted, String problem, String fees_paid, String report) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("p_username", p_username);
        contentValues.put("p_password", p_password);
        contentValues.put("d_username", d_username);
        contentValues.put("d_password", d_password);
        contentValues.put("granted", granted);
        contentValues.put("problem", problem);
        contentValues.put("fees_paid", fees_paid);
        contentValues.put("report", report);

        long l = db1.insert(TABLE_NAME_DOCTOR_PATIENT, null, contentValues);
        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }

    //update appointment

    public boolean update_doctor_patient(String p_username, String p_password, String d_username, String d_password, String granted, String problem, String fees_paid, String report) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("p_username", p_username);
        contentValues.put("p_password", p_password);
        contentValues.put("d_username", d_username);
        contentValues.put("d_password", d_password);
        contentValues.put("granted", granted);
        contentValues.put("problem", problem);
        contentValues.put("fees_paid", fees_paid);
        contentValues.put("report", report);

        long l = db1.update(TABLE_NAME_DOCTOR_PATIENT, contentValues, "p_username=? and p_password=? and problem=?", new String[]{p_username, p_password, problem});
        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }


    //**********************************************STAFF TABLE ***********************************************************
    //insert appointment

    public boolean insert_staff(String s_username, String s_password, String d_username, String d_password, String assigned) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("s_username", s_username);
        contentValues.put("s_password", s_password);
        contentValues.put("d_username", d_username);
        contentValues.put("d_password", d_password);
        contentValues.put("assigned", assigned);

        long l = db1.insert(TABLE_NAME_STAFF, null, contentValues);
        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }

    //update appointment

    public boolean update_doctor_patient(String s_username, String s_password, String d_username, String d_password, String assigned) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("s_username", s_username);
        contentValues.put("s_password", s_password);
        contentValues.put("d_username", d_username);
        contentValues.put("d_password", d_password);
        contentValues.put("assigned", assigned);

        long l = db1.update(TABLE_NAME_STAFF, contentValues, "s_username=? and s_password=?", new String[]{s_username, s_password});
        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor checkduplicates_in_staff(String s, String s1, String s2, String s3) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_STAFF + " where s_username=? and s_password=? and d_username=? and d_password=?", new String[]{s, s1, s2, s3});
        return res;
    }


    //********************************************FEEDBACK*******************************
    public boolean insert_feedback(String username, String password, String feecback) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("feedback", feecback);

        long l = db1.insert(TABLE_NAME_FEEDBACK, null, contentValues);
        if (l != -1) {
            return true;
        } else {
            return false;
        }
    }
}