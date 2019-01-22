package com.anmol.rosei.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anmol.rosei.Adapter.Mess2Adapter;
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
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by anmol on 10/20/2017.
 */

public class first extends Fragment {
    Button load;
    ListView list;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid());
    List<mess2>mess2s = new ArrayList<>();
    Mess2Adapter mess2Adapter;
    TextView amt2,total;
    private CircularProgressBar bookpgr;
    private TextView booktext;
    Button bookm2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first,container,false);
        load = (Button)v.findViewById(R.id.load);
        list = (ListView)v.findViewById(R.id.menu);
        amt2 = (TextView)v.findViewById(R.id.amt2);
        total = (TextView)v.findViewById(R.id.total);
//        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
//        ViewGroup footer = (ViewGroup)layoutInflater.inflate(R.layout.footer2,list,false);
//        list.addFooterView(footer,null,false);
        bookm2 = (Button)v.findViewById(R.id.bookm2);
        booktext = (TextView)v.findViewById(R.id.bookingtext);
        bookpgr = (CircularProgressBar)v.findViewById(R.id.bookpgr);
        bookm2.setVisibility(View.VISIBLE);
        booktext.setVisibility(View.INVISIBLE);
        bookpgr.setVisibility(View.INVISIBLE);

        db.child("messStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("amount2").getValue(String.class)!=null){
                    amt2.setText(dataSnapshot.child("amount2").getValue(String.class));
                }
                if(dataSnapshot.child("total").getValue(String.class)!=null){
                    total.setText(dataSnapshot.child("total").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setVisibility(View.GONE);

            }
        });
        loaddata();
        db.child("messStatus").child("mess2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mess2s.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String day = data.child("date").getValue().toString();
                    String brkfast = data.child("brkfast").getValue().toString();
                    String lnch = data.child("lnch").getValue().toString();
                    String dinnr = data.child("dinnr").getValue().toString();
                    String bs = data.child("bs").getValue().toString();
                    String ls = data.child("ls").getValue().toString();
                    String ds = data.child("ds").getValue().toString();
                    mess2 mess2 = new mess2(day,brkfast,lnch,dinnr,bs,ls,ds);
                    mess2s.add(mess2);
                }
                if(getActivity()!=null){
                    mess2Adapter = new Mess2Adapter(getActivity(),R.layout.menu,mess2s);
                    mess2Adapter.notifyDataSetChanged();

                    if(!mess2Adapter.isEmpty()){
                        load.setVisibility(View.GONE);
                        list.setAdapter(mess2Adapter);
                        bookm2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bookm2.setVisibility(View.INVISIBLE);
                                bookpgr.setVisibility(View.VISIBLE);
                                booktext.setVisibility(View.VISIBLE);
                                final JSONObject jsonObject = mess2Adapter.getJsonObject();
                                System.out.println("jsonobj:" + jsonObject);
                                db.child("rosei").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot!=null && dataSnapshot.child("sid").getValue(String.class)!=null && dataSnapshot.child("pwd").getValue(String.class)!=null) {
                                            String sid = dataSnapshot.child("sid").getValue(String.class);
                                            String pwd = dataSnapshot.child("pwd").getValue(String.class);
                                            try {
                                                jsonObject.put("un",sid);
                                                jsonObject.put("pw",pwd);
                                                jsonObject.put("pass","encrypt");
                                                jsonObject.put("check",2);
                                                System.out.println("jsonobj:" + jsonObject);
                                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://14.139.198.171/api/rosei/booking", jsonObject, new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        bookm2.setVisibility(View.VISIBLE);
                                                        bookpgr.setVisibility(View.INVISIBLE);
                                                        booktext.setVisibility(View.INVISIBLE);

                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        bookm2.setVisibility(View.VISIBLE);
                                                        bookpgr.setVisibility(View.INVISIBLE);
                                                        booktext.setVisibility(View.INVISIBLE);
                                                        Toast.makeText(getActivity(),"Coupon booking failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                Mysingleton.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        bookm2.setVisibility(View.VISIBLE);
                                        bookpgr.setVisibility(View.INVISIBLE);
                                        booktext.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        });
                    }
                    else{
                        load.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    private void loaddata() {

    }
}
