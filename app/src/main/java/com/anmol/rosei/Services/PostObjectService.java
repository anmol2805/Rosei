package com.anmol.rosei.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anmol on 11/2/2017.
 */

public class PostObjectService extends IntentService {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid()).child("rosei");
    public PostObjectService(String PostObjectService) {
        super("PostObjectService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Map<String,Object> map = new HashMap<>();
        map.put("monbfmt","veg");
        map.put("monlunmt","veg");
        map.put("mondinmt","veg");
        map.put("tuebfmt","veg");
        map.put("tuelunmt","veg");
        map.put("tuedinmt","veg");
        map.put("wedbfmt","veg");
        map.put("wedlunmt","veg");
        map.put("weddinmt","veg");
        map.put("thubfmt","veg");
        map.put("thulunmt","veg");
        map.put("thudinmt","veg");
        map.put("fribfmt","veg");
        map.put("frilunmt","veg");
        map.put("fridinmt","veg");
        map.put("satbfmt","veg");
        map.put("satlunmt","veg");
        map.put("satdinmt","veg");
        map.put("sunbfmt","veg");
        map.put("sunlunmt","veg");
        map.put("sundinmt","veg");


    }
}
