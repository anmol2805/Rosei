package com.anmol.rosei.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anmol.rosei.Model.MessStatus;
import com.anmol.rosei.Model.mess1;
import com.anmol.rosei.Model.mess2;
import com.anmol.rosei.Mysingleton;
import com.anmol.rosei.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anmol on 10/18/2017.
 */

public class RequestServiceStatus extends IntentService {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid()).child("rosei");
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid());

    public RequestServiceStatus() {
        super("RequestServiceStatus");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final JSONObject jsonObject = new JSONObject();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null && dataSnapshot.child("sid").getValue(String.class)!=null && dataSnapshot.child("pwd").getValue(String.class)!=null){
                    String sid = dataSnapshot.child("sid").getValue(String.class);
                    String pwd = dataSnapshot.child("pwd").getValue(String.class);

                    try {
                        jsonObject.put("un",sid);
                        jsonObject.put("pw",pwd);
                        jsonObject.put("pass","encrypt");
                        jsonObject.put("mess","m001");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.status), jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int c = 0;
                                while (c<response.getJSONArray("messStatus").length()){
                                    JSONObject object = response.getJSONArray("messStatus").getJSONObject(c);
                                    String brk = object.getString("breakfast");
                                    String lnch = object.getString("lunch");
                                    String dnnr = object.getString("dinner");
                                    MessStatus messStatus = new MessStatus(brk,lnch,dnnr);
                                    db.child("messstatus").child(String.valueOf(c)).setValue(messStatus);
                                    c++;
                                }
//                                int d = 0;
//                                while (d<response.getJSONArray("mess2").length()){
//                                    JSONObject object = response.getJSONArray("mess2").getJSONObject(d);
//                                    String date = object.getString("day");
//                                    String brkfast = object.getString("brkfast");
//                                    String lnch = object.getString("lnch");
//                                    String dinnr = object.getString("dinnr");
//                                    mess2 mess2 = new mess2(date,brkfast,lnch,dinnr);
//                                    db.child("mess2").child(String.valueOf(d)).setValue(mess2);
//                                    d++;
//                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Mysingleton.getInstance(getApplicationContext()).addToRequestqueue(jsonObjectRequest);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
