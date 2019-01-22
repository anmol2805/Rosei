package com.anmol.rosei;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.SystemClock;
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
import com.anmol.rosei.Helpers.CouponDb;
import com.anmol.rosei.Helpers.MessDownMenuDb;
import com.anmol.rosei.Helpers.MessUpMenuDb;
import com.anmol.rosei.Model.CouponStatus;
import com.anmol.rosei.Model.Mess_Menu;
import com.anmol.rosei.Services.MessStatusService;
import com.anmol.rosei.Services.NotifyService;
import com.bumptech.glide.Glide;
import com.canopydevelopers.canopyauth.AuthConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {
    Animation animFadein,zoomin;
    ImageView img;
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
        if(authConfig.readloginstatus()){
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        //go to roseiactivity or dashboard
        else{
            img = (ImageView)findViewById(R.id.imageView2);
            progressBar = (ProgressBar)findViewById(R.id.load);
            progressBar.setVisibility(View.VISIBLE);
            //menu request
            JsonArrayRequest menurequest = new JsonArrayRequest(Request.Method.GET, getResources().getString(R.string.root_url) + "/menu",null,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray menuresponse) {
                    System.out.println(menuresponse);
                    String monday = null;
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
                        monday = messup.getJSONObject("mon").getString("date").substring(0,10);
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


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date changedate = simpleDateFormat.parse(monday);
                        System.out.println("coupondate:" + changedate);
                        Date onedaybefore = new Date(changedate.getTime() - 8);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String lastmonday = sdf.format(onedaybefore);
                        System.out.println("onedaybefore:" + onedaybefore);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //coupon request
                    JsonObjectRequest couponrequest = new JsonObjectRequest(Request.Method.GET, getResources().getString(R.string.root_url) + "/coupon/b216008/" + monday, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject couponresponse) {
                            try {
                                System.out.println(couponresponse);
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
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(SplashActivity.this, RoseiActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.still,R.anim.slide_in_up);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SplashActivity.this,"Unable to load Coupons",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Mysingleton.getInstance(SplashActivity.this).addToRequestqueue(couponrequest);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error" + error);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SplashActivity.this,"Unable to load Menu",Toast.LENGTH_SHORT).show();
                }
            });
            Mysingleton.getInstance(this).addToRequestqueue(menurequest);





            // animation on splashscreen
            animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade_in);
            zoomin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
            img.startAnimation(zoomin);
//            Intent intent = new Intent(SplashActivity.this, NotifyService.class);
//            startService(intent);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 0);
            Intent intent1 = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);




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
