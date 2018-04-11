package com.anmol.rosei.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotifyService extends IntentService {

    public NotifyService() {
        super("NotifyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat ddf = new SimpleDateFormat("EEEE");
        String day = ddf.format(date);
        System.out.println("todayday:"+day);
        String todaysdate = sdf.format(date);
        if (day.contains("Wed") || day.contains("wed")){
            db.child("wednesday").setValue(todaysdate);
        }
        else if(day.contains("Thu") || day.contains("thu")){
            db.child("thursday").setValue(todaysdate);
        }
    }
}
