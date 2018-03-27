package com.anmol.rosei;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.anmol.rosei.Services.RequestService;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {
    Animation animFadein,zoomin;
    ImageView img;

    ProgressBar progressBar;

    int key;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String uid,pwd,urlid,uidu;
    DatabaseReference databaseReference;
    CircleImageView sanmol,sankit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sanmol = (CircleImageView)findViewById(R.id.sanmol);
        sankit = (CircleImageView)findViewById(R.id.sankit);
        Glide.with(this).load(R.drawable.anmol).into(sanmol);
        Glide.with(this).load(R.drawable.ankit).into(sankit);
        auth = FirebaseAuth.getInstance();
        //condition to go to loginactivity
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        //go to roseiactivity or dashboard
        else{

            databaseReference = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid());
            progressBar = (ProgressBar)findViewById(R.id.load);
            progressBar.setVisibility(View.VISIBLE);

            img = (ImageView)findViewById(R.id.imageView2);
            // animation on splashscreen
            animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade_in);
            zoomin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
            img.startAnimation(zoomin);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // service to receive weekly menu
                    Intent i = new Intent(SplashActivity.this, RequestService.class);
                    startService(i);
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(SplashActivity.this, RoseiActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.still,R.anim.slide_in_up);
                }
            },3000);



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
