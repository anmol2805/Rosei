package com.anmol.rosei;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anmol.rosei.Services.RequestService;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoseiActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid()).child("rosei");
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid()).child("mess1");
    Animation rotate;
    Button book;
    ImageButton set;
    TextView stuid;
    CircleImageView user;
    Button logout;
    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rosei);
        rotate = AnimationUtils.loadAnimation(RoseiActivity.this,R.anim.rotate);
        set = (ImageButton)findViewById(R.id.set);
        book = (Button)findViewById(R.id.book);
        user = (CircleImageView)findViewById(R.id.user);
        stuid = (TextView)findViewById(R.id.stuid);
        logout = (Button)findViewById(R.id.logout);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoseiActivity.this,Book_Activity.class));
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set.startAnimation(rotate);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(RoseiActivity.this);
                builder1.setTitle("Logout");
                builder1.setMessage("Are you sure you want to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Logout",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(RoseiActivity.this,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_down);

                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("sid").getValue(String.class)!=null){
                    String sid = dataSnapshot.child("sid").getValue(String.class);
                    sid = sid.toUpperCase();
                    stuid.setText(sid);
                    String url = "https://hib.iiit-bh.ac.in/Hibiscus/docs/iiit/Photos/"+sid+".jpg";
                    Glide.with(RoseiActivity.this).load(url).into(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        final String cdate = dateFormat.format(cal.getTime());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    if(data.child("date").getValue(String.class)!=null){
                        String date = data.child("date").getValue(String.class);
                        if(date.contains(cdate)){
                            Toast.makeText(RoseiActivity.this,cdate,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RoseiActivity.this,"No",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        if(back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.still,R.anim.slide_out_down);
        }else {

            Toast.makeText(getBaseContext(), "Double tap to exit!", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
