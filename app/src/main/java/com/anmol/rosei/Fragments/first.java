package com.anmol.rosei.Fragments;


import android.content.Context;
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
import com.anmol.rosei.Helpers.AuthUser;
import com.anmol.rosei.Helpers.CouponDb;
import com.anmol.rosei.Helpers.MessUpMenuDb;
import com.anmol.rosei.Model.CouponStatus;
import com.anmol.rosei.Model.Mess_Menu;
import com.anmol.rosei.Model.mess2;
import com.anmol.rosei.Mysingleton;
import com.anmol.rosei.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by anmol on 10/20/2017.
 */

public class first extends Fragment {

    ListView list;

    List<mess2>mess2s = new ArrayList<>();
    Mess2Adapter mess2Adapter;
    TextView amt2,total;
    private CircularProgressBar bookpgr;
    private TextView booktext;
    Button bookm2;
    AuthUser authUser;
    View footerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first,container,false);

        list = (ListView)v.findViewById(R.id.menu);
        amt2 = (TextView)v.findViewById(R.id.amt2);
        total = (TextView)v.findViewById(R.id.total);
        if(getActivity()!=null){
            footerView =  ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer2, null, false);
        }
        list.addFooterView(footerView);
        bookm2 = (Button)footerView.findViewById(R.id.bookm2);
        booktext = (TextView)v.findViewById(R.id.bookingtext);
        bookpgr = (CircularProgressBar)v.findViewById(R.id.bookpgr);
        bookm2.setVisibility(View.VISIBLE);
        booktext.setVisibility(View.INVISIBLE);
        bookpgr.setVisibility(View.INVISIBLE);
        if(getActivity()!=null){
            authUser = new AuthUser(getActivity());
        }
        loaddata();
        return v;
    }

    private void loaddata() {
        final ArrayList<String> days = new ArrayList<>();
        days.add("mon");
        days.add("tue");
        days.add("wed");
        days.add("thr");
        days.add("fri");
        days.add("sat");
        days.add("sun");
        if(getActivity()!=null){
            MessUpMenuDb messUpMenuDb = new MessUpMenuDb(getActivity());
            List<Mess_Menu> mess_menus = new ArrayList<>();
            mess_menus.clear();
            mess_menus = messUpMenuDb.readData("Select * from messup_menu_table");
            CouponDb couponDb = new CouponDb(getActivity());
            List<CouponStatus> couponStatuses = new ArrayList<>();
            couponStatuses.clear();
            couponStatuses = couponDb.readData("Select * from coupon_table");
            amt2.setText(String.valueOf(authUser.readamount2()));
            total.setText(String.valueOf(authUser.readtotal()));
            mess2s.clear();
            final String date = mess_menus.get(0).getDate();
            for(int i=0;i<couponStatuses.size();i++){
                String day = mess_menus.get(i).getWeekday() + " " + mess_menus.get(i).getDate();
                String breakfast = mess_menus.get(i).getBreakfast();
                String lunch = mess_menus.get(i).getLunch();
                String dinner = mess_menus.get(i).getDinner();
                String bs = couponStatuses.get(i).getBreakfast();
                String ls = couponStatuses.get(i).getLunch();
                String ds = couponStatuses.get(i).getDinner();
                mess2 mess2 = new mess2(day,breakfast,lunch,dinner,bs,ls,ds);
                mess2s.add(mess2);
            }
            if(!mess2s.isEmpty()){
                mess2Adapter = new Mess2Adapter(getActivity(),R.layout.menu,mess2s);
                mess2Adapter.notifyDataSetChanged();
                list.setAdapter(mess2Adapter);
                bookm2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookm2.setVisibility(View.INVISIBLE);
                        bookpgr.setVisibility(View.VISIBLE);
                        booktext.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = mess2Adapter.getJsonObject();
                        if(authUser.readdate().equals("0001-01-01T00:00:00Z")){
                            //POST

                            try {
                                jsonObject.put("userid",authUser.readuser());
                                jsonObject.put("username",authUser.readusername());
                                jsonObject.put("gender",authUser.readgender());
                                jsonObject.put("weekstartdate",date);
                                jsonObject.put("id",authUser.readcid());
                                jsonObject.put("amount1",authUser.readamount1());
                                jsonObject.put("mount2",authUser.readamount2());
                                jsonObject.put("Total",authUser.readtotal());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.root_url) + "/coupon", jsonObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try{
                                        String weekstartdate = response.getString("weekstartdate");
                                        int amount1 = response.getInt("amount1");
                                        int amount2 = response.getInt("mount2");
                                        int total = response.getInt("Total");
                                        String cid = response.getString("id");
                                        authUser.writedate(weekstartdate);
                                        authUser.writeprice(amount1,amount2,total,cid);
                                        CouponDb couponDb = new CouponDb(getActivity());
                                        JSONObject coupon = response.getJSONObject("coupon");
                                        ArrayList<String> meals = new ArrayList<>();
                                        meals.add("breakfast");
                                        meals.add("lunch");
                                        meals.add("dinner");
                                        ArrayList<String> params = new ArrayList<>();
                                        params.add("isSelected");
                                        params.add("isVeg");
                                        params.add("isMessUp");
                                        for(int i=0;i<days.size();i++) {
                                            JSONObject day = coupon.getJSONObject(days.get(i));
                                            ArrayList<StringBuilder> binaries = new ArrayList<>();
                                            binaries.add(new StringBuilder("000"));
                                            binaries.add(new StringBuilder("000"));
                                            binaries.add(new StringBuilder("000"));
                                            for (int j = 0; j < meals.size(); j++) {
                                                JSONObject meal = day.getJSONObject(meals.get(j));
                                                String food = meal.getString("food");
                                                binaries.get(j).append(food);
                                                for (int k = 0; k < params.size(); k++) {
                                                    if (meal.getBoolean(params.get(k))) {
                                                        binaries.get(j).setCharAt(k, '1');
                                                    }
                                                }
                                            }
                                            System.out.println(days.get(i) + binaries.get(0).toString() + binaries.get(1).toString() + binaries.get(2).toString());
                                            CouponStatus couponStatus = new CouponStatus(days.get(i), binaries.get(0).toString(), binaries.get(1).toString(), binaries.get(2).toString());
                                            couponDb.insertData(couponStatus);
                                            couponDb.updatenotice(couponStatus);
                                        }
                                        loaddata();
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getActivity(),"Booking successful",Toast.LENGTH_SHORT).show();
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
                            if (getActivity()!=null){
                                Mysingleton.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);
                            }

                        }
                        else{
                            //PUT
                            try {
                                jsonObject.put("userid",authUser.readuser());
                                jsonObject.put("username",authUser.readusername());
                                jsonObject.put("gender",authUser.readgender());
                                jsonObject.put("weekstartdate",authUser.readdate());
                                jsonObject.put("id",authUser.readcid());
                                jsonObject.put("amount1",authUser.readamount1());
                                jsonObject.put("mount2",authUser.readamount2());
                                jsonObject.put("Total",authUser.readtotal());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("floor menu:" + jsonObject);
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, getResources().getString(R.string.root_url) + "/coupon", jsonObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println(response);
                                    try{
                                        if(response.getString("result").equals("success")){
                                            JsonObjectRequest couponrequest = new JsonObjectRequest(Request.Method.GET, getResources().getString(R.string.root_url) + "/coupon/" + authUser.readuser() + "/" + authUser.readdate().substring(0,10), null, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try{
                                                        String weekstartdate = response.getString("weekstartdate");
                                                        int amount1 = response.getInt("amount1");
                                                        int amount2 = response.getInt("mount2");
                                                        int total = response.getInt("Total");
                                                        String cid = response.getString("id");
                                                        authUser.writedate(weekstartdate);
                                                        authUser.writeprice(amount1,amount2,total,cid);
                                                        CouponDb couponDb = new CouponDb(getActivity());
                                                        JSONObject coupon = response.getJSONObject("coupon");
                                                        ArrayList<String> meals = new ArrayList<>();
                                                        meals.add("breakfast");
                                                        meals.add("lunch");
                                                        meals.add("dinner");
                                                        ArrayList<String> params = new ArrayList<>();
                                                        params.add("isSelected");
                                                        params.add("isVeg");
                                                        params.add("isMessUp");
                                                        for(int i=0;i<days.size();i++) {
                                                            JSONObject day = coupon.getJSONObject(days.get(i));
                                                            ArrayList<StringBuilder> binaries = new ArrayList<>();
                                                            binaries.add(new StringBuilder("000"));
                                                            binaries.add(new StringBuilder("000"));
                                                            binaries.add(new StringBuilder("000"));
                                                            for (int j = 0; j < meals.size(); j++) {
                                                                JSONObject meal = day.getJSONObject(meals.get(j));
                                                                String food = meal.getString("food");
                                                                binaries.get(j).append(food);
                                                                for (int k = 0; k < params.size(); k++) {
                                                                    if (meal.getBoolean(params.get(k))) {
                                                                        binaries.get(j).setCharAt(k, '1');
                                                                    }
                                                                }
                                                            }
                                                            System.out.println(days.get(i) + binaries.get(0).toString() + binaries.get(1).toString() + binaries.get(2).toString());
                                                            CouponStatus couponStatus = new CouponStatus(days.get(i), binaries.get(0).toString(), binaries.get(1).toString(), binaries.get(2).toString());
                                                            couponDb.insertData(couponStatus);
                                                            couponDb.updatenotice(couponStatus);
                                                        }
                                                        loaddata();
                                                        bookm2.setVisibility(View.VISIBLE);
                                                        bookpgr.setVisibility(View.INVISIBLE);
                                                        booktext.setVisibility(View.INVISIBLE);
                                                        Toast.makeText(getActivity(),"Coupon Booking Successful",Toast.LENGTH_SHORT).show();
                                                    }catch (JSONException e){
                                                        e.printStackTrace();
                                                    }
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
                                            Mysingleton.getInstance(getActivity()).addToRequestqueue(couponrequest);
                                        }else{
                                            bookm2.setVisibility(View.VISIBLE);
                                            bookpgr.setVisibility(View.INVISIBLE);
                                            booktext.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getActivity(),"Coupon booking failed",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    catch(JSONException e){
                                        e.printStackTrace();
                                    }


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
                            if (getActivity()!=null){
                                Mysingleton.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);
                            }
                        }
                    }
                });
            }
        }
    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            loaddata();
        }
    }
}
