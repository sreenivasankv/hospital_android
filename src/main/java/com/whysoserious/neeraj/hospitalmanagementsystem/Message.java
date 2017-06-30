package com.whysoserious.neeraj.hospitalmanagementsystem;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Neeraj on 19-Mar-16.
 */
public class Message {
    public static void message(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
