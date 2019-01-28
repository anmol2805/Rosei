package com.anmol.rosei;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anmol.rosei.Helpers.AuthUser;
import com.anmol.rosei.Helpers.CouponDb;
import com.anmol.rosei.Helpers.CurrentCouponDb;
import com.anmol.rosei.Helpers.MessDownMenuDb;
import com.anmol.rosei.Helpers.MessUpMenuDb;
import com.anmol.rosei.Model.CouponStatus;
import com.anmol.rosei.Model.Mess_Menu;
import com.bumptech.glide.Glide;
import com.canopydevelopers.canopyauth.AuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    CircleImageView sanmol,sankit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sanmol = (CircleImageView)findViewById(R.id.sanmol);
        sankit = (CircleImageView)findViewById(R.id.sankit);
        Glide.with(this).load(R.drawable.anmol).into(sanmol);
        Glide.with(this).load(R.drawable.ankit).into(sankit);
        AuthConfig authConfig = new AuthConfig(this);
        //condition to go to loginactivity
        if(!authConfig.readloginstatus()){
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        //go to roseiactivity or dashboard
        else{
            progressBar = (ProgressBar)findViewById(R.id.load);
            progressBar.setVisibility(View.VISIBLE);
            //menu request
            final AuthUser authUser = new AuthUser(this);
            JsonArrayRequest menurequest = new JsonArrayRequest(Request.Method.GET, getResources().getString(R.string.root_url) + "/menu",null,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray menuresponse) {
                    System.out.println(menuresponse);
                    final ArrayList<String> days = new ArrayList<>();
                    days.add("mon");
                    days.add("tue");
                    days.add("wed");
                    days.add("thr");
                    days.add("fri");
                    days.add("sat");
                    days.add("sun");
                    try {
                        MessUpMenuDb messUpMenuDb = new MessUpMenuDb(SplashActivity.this);
                        MessDownMenuDb messDownMenuDb = new MessDownMenuDb(SplashActivity.this);
                        JSONObject messup = menuresponse.getJSONObject(0).getJSONObject("messUP");
                        final String monday = messup.getJSONObject("mon").getString("date").substring(0,10);
                        for(int i=0;i<days.size();i++) {
                            String breakfast = messup.getJSONObject(days.get(i)).getString("breakfast");
                            String lunch = messup.getJSONObject(days.get(i)).getString("lunch");
                            String dinner = messup.getJSONObject(days.get(i)).getString("dinner");
                            String date = messup.getJSONObject(days.get(i)).getString("date");
                            Mess_Menu mess_menu = new Mess_Menu(days.get(i), breakfast, lunch, dinner, date.substring(0,10));
                                messUpMenuDb.insertData(mess_menu);
                                messUpMenuDb.updatenotice(mess_menu);
                        }

                            JSONObject messdown = menuresponse.getJSONObject(0).getJSONObject("messDown");
                            for(int i=0;i<days.size();i++) {
                                String breakfast = messdown.getJSONObject(days.get(i)).getString("breakfast");
                                String lunch = messdown.getJSONObject(days.get(i)).getString("lunch");
                                String dinner = messdown.getJSONObject(days.get(i)).getString("dinner");
                                String date = messdown.getJSONObject(days.get(i)).getString("date");
                                Mess_Menu mess_menu = new Mess_Menu(days.get(i), breakfast, lunch, dinner, date.substring(0,10));

                                    messDownMenuDb.insertData(mess_menu);
                                    messDownMenuDb.updatenotice(mess_menu);


                            }

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String lastmonday = null;
                        try {
                            Date changedate = simpleDateFormat.parse(monday);
                            System.out.println("coupondate:" + changedate);
                            Date onedaybefore = new Date(changedate.getTime() - 8);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            lastmonday = sdf.format(onedaybefore);
                            System.out.println("onedaybefore:" + onedaybefore);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //coupon request

                        JsonObjectRequest currentcouponrequest = new JsonObjectRequest(Request.Method.GET, getResources().getString(R.string.root_url) + "/coupon/" + authUser.readuser() + "/" + lastmonday, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject couponresponse) {
                                try {
                                    System.out.println(couponresponse);
                                    CurrentCouponDb currentcouponDb = new CurrentCouponDb(SplashActivity.this);
                                    JSONObject coupon = couponresponse.getJSONObject("coupon");
                                    ArrayList<String> meals = new ArrayList<>();
                                    meals.add("breakfast");
                                    meals.add("lunch");
                                    meals.add("dinner");
                                    ArrayList<String> params = new ArrayList<>();
                                    params.add("isSelected");
                                    params.add("isVeg");
                                    params.add("isMessUp");
                                    for(int i=0;i<days.size();i++){
                                        JSONObject day = coupon.getJSONObject(days.get(i));
                                        ArrayList<StringBuilder> binaries = new ArrayList<>();
                                        binaries.add(new StringBuilder("000"));
                                        binaries.add(new StringBuilder("000"));
                                        binaries.add(new StringBuilder("000"));
                                        for(int j=0;j<meals.size();j++){
                                            JSONObject meal = day.getJSONObject(meals.get(j));
                                            String food = meal.getString("food");
                                            binaries.get(j).append(food);
                                            for(int k=0;k<params.size();k++){
                                                if(meal.getBoolean(params.get(k))){
                                                    binaries.get(j).setCharAt(k,'1');
                                                }
                                            }
                                        }
                                        System.out.println(days.get(i)+binaries.get(0).toString()+binaries.get(1).toString()+binaries.get(2).toString());
                                        CouponStatus couponStatus = new CouponStatus(days.get(i),binaries.get(0).toString(),binaries.get(1).toString(),binaries.get(2).toString());
                                        currentcouponDb.insertData(couponStatus);
                                        currentcouponDb.updatenotice(couponStatus);
                                    }
                                    JsonObjectRequest upcomingcouponrequest = new JsonObjectRequest(Request.Method.GET, getResources().getString(R.string.root_url) + "/coupon/" + authUser.readuser() + "/" + monday, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject couponresponse) {
                                            try {
                                                System.out.println(couponresponse);
                                                String weekstartdate = couponresponse.getString("weekstartdate");
                                                int amount1 = couponresponse.getInt("amount1");
                                                int amount2 = couponresponse.getInt("mount2");
                                                int total = couponresponse.getInt("Total");
                                                String cid = couponresponse.getString("id");
                                                System.out.println(weekstartdate + " " + amount1  + " " + amount2 + " " + total + " " + cid);
                                                if(authUser.writedate(weekstartdate)){
                                                    System.out.println(authUser.readdate());
                                                }else {
                                                    System.out.println("sharedpreferences failed");
                                                }
                                                if(authUser.writeprice(amount2,amount1,total,cid)){
                                                    System.out.println("amount1" + authUser.readamount1());
                                                    System.out.println("amount2" + authUser.readamount2());
                                                    System.out.println("total" + authUser.readtotal());
                                                    System.out.println("cid" + authUser.readcid());
                                                }else {
                                                    System.out.println("sharedpreferences failed");
                                                }
                                                CouponDb couponDb = new CouponDb(SplashActivity.this);
                                                JSONObject coupon = couponresponse.getJSONObject("coupon");
                                                ArrayList<String> meals = new ArrayList<>();
                                                meals.add("breakfast");
                                                meals.add("lunch");
                                                meals.add("dinner");
                                                ArrayList<String> params = new ArrayList<>();
                                                params.add("isSelected");
                                                params.add("isVeg");
                                                params.add("isMessUp");
                                                for(int i=0;i<days.size();i++){
                                                    JSONObject day = coupon.getJSONObject(days.get(i));
                                                    ArrayList<StringBuilder> binaries = new ArrayList<>();
                                                    binaries.add(new StringBuilder("000"));
                                                    binaries.add(new StringBuilder("000"));
                                                    binaries.add(new StringBuilder("000"));
                                                    for(int j=0;j<meals.size();j++){
                                                        JSONObject meal = day.getJSONObject(meals.get(j));
                                                        String food = meal.getString("food");
                                                        binaries.get(j).append(food);
                                                        for(int k=0;k<params.size();k++){
                                                            if(meal.getBoolean(params.get(k))){
                                                                binaries.get(j).setCharAt(k,'1');
                                                            }
                                                        }
                                                    }
                                                    System.out.println(days.get(i)+binaries.get(0).toString()+binaries.get(1).toString()+binaries.get(2).toString());
                                                    CouponStatus couponStatus = new CouponStatus(days.get(i),binaries.get(0).toString(),binaries.get(1).toString(),binaries.get(2).toString());
                                                    couponDb.insertData(couponStatus);
                                                    couponDb.updatenotice(couponStatus);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            Intent intent = new Intent(SplashActivity.this, RoseiActivity.class);
                                            startActivity(intent);
                                            finish();
                                            overridePendingTransition(R.anim.still,R.anim.slide_in_up);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            System.out.println("upcoming loading error" + error);
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(SplashActivity.this,"Unable to load Coupons",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SplashActivity.this, RoseiActivity.class);
                                            startActivity(intent);
                                            finish();
                                            overridePendingTransition(R.anim.still,R.anim.slide_in_up);
                                        }
                                    });
                                    Mysingleton.getInstance(SplashActivity.this).addToRequestqueue(upcomingcouponrequest);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("currrent loading error" + error);
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(SplashActivity.this,"Unable to load Coupons",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SplashActivity.this, RoseiActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.still,R.anim.slide_in_up);
                            }
                        });
                        Mysingleton.getInstance(SplashActivity.this).addToRequestqueue(currentcouponrequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error" + error);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SplashActivity.this,"Unable to load Menu",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashActivity.this, RoseiActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.still,R.anim.slide_in_up);
                }
            });
            Mysingleton.getInstance(this).addToRequestqueue(menurequest);





            // animation on splashscreen


//            Intent intent = new Intent(SplashActivity.this, NotifyService.class);
//            startService(intent);
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, 10);
//            calendar.set(Calendar.MINUTE, 59);
//            calendar.set(Calendar.SECOND, 0);
//            Intent intent1 = new Intent(this, AlarmReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);




        }

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
        overridePendingTransition(R.anim.still,R.anim.slide_in_up);
    }

}
