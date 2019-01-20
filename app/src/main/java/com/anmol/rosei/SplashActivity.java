package com.anmol.rosei;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.anmol.rosei.Services.MessStatusService;
import com.anmol.rosei.Services.NotifyService;
import com.bumptech.glide.Glide;
import com.canopydevelopers.canopyauth.AuthConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.Calendar;

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
        if(!authConfig.readloginstatus()){
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        //go to roseiactivity or dashboard
        else{
            img = (ImageView)findViewById(R.id.imageView2);
            progressBar = (ProgressBar)findViewById(R.id.load);
            progressBar.setVisibility(View.VISIBLE);
            //menu request
            JsonObjectRequest menurequest = new JsonObjectRequest(Request.Method.POST, "", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject menuresponse) {
                    //coupon request
                    JsonObjectRequest couponrequest = new JsonObjectRequest(Request.Method.POST, "", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject couponresponse) {
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
