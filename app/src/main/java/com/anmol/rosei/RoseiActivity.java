package com.anmol.rosei;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anmol.rosei.Adapter.GridAdapter;
import com.anmol.rosei.Adapter.ViewpageAdapter;
import com.anmol.rosei.Helpers.CurrentCouponDb;
import com.anmol.rosei.Helpers.MessDownMenuDb;
import com.anmol.rosei.Helpers.MessUpMenuDb;
import com.anmol.rosei.Model.Coupon;
import com.anmol.rosei.Model.CouponStatus;
import com.anmol.rosei.Model.MessStatus;
import com.anmol.rosei.Model.Mess_Menu;
import com.anmol.rosei.Services.MessStatusService;
import com.anmol.rosei.Services.MessStatusService2;
import com.bumptech.glide.Glide;
import com.canopydevelopers.canopyauth.AuthConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoseiActivity extends AppCompatActivity {

    Animation rotate;
    Button book;
    ImageButton set;
    TextView stuid;
    CircleImageView user;
    Button logout;
    private static long back_pressed;
    List<MessStatus> messStatuses = new ArrayList<>();
    RecyclerView gridview;
    GridAdapter gridAdapter;
    ViewPager viewPager;
    List<Coupon> coupons = new ArrayList<>();
    ViewpageAdapter viewpageAdapter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rosei);
        rotate = AnimationUtils.loadAnimation(RoseiActivity.this,R.anim.rotate);
        set = (ImageButton)findViewById(R.id.set);
        book = (Button)findViewById(R.id.book);
        user = (CircleImageView)findViewById(R.id.user);
        stuid = (TextView)findViewById(R.id.stuid);
        logout = (Button)findViewById(R.id.logout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        gridview = (RecyclerView)findViewById(R.id.gridrecycler);
        gridview.setHasFixedSize(true);
        gridview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        // booking activity
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoseiActivity.this,BookingnewActivity.class));
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set.startAnimation(rotate);
            }
        });
        final AuthConfig authConfig = new AuthConfig(this);
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
                                authConfig.writeloginstatus(false);
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

        //user image here
//        String url = "https://hib.iiit-bh.ac.in/Hibiscus/docs/iiit/Photos/"+sid+".jpg";
//        Glide.with(RoseiActivity.this).load(url).into(user);

        loadDataFromDb();


    }

    private void loadDataFromDb() {
        CurrentCouponDb currentCouponDb = new CurrentCouponDb(this);
        MessUpMenuDb messUpMenuDb = new MessUpMenuDb(this);
        MessDownMenuDb messDownMenuDb = new MessDownMenuDb(this);

        List<CouponStatus> couponStatuses = new ArrayList<>();
        couponStatuses.clear();
        couponStatuses = currentCouponDb.readData("Select * from current_coupon_table");
        List<Mess_Menu> messupmenu = new ArrayList<>();
        messupmenu.clear();
        messupmenu = messUpMenuDb.readData("Select * from messup_menu_table");
        List<Mess_Menu> messdownmenu = new ArrayList<>();
        messdownmenu.clear();
        messdownmenu = messDownMenuDb.readData("Select * from messdown_menu_table");
        messStatuses.clear();
        coupons.clear();
        for(int i=0;i<couponStatuses.size();i++){
            MessStatus messStatus = new MessStatus(couponStatuses.get(i).getBreakfast(),couponStatuses.get(i).getLunch(),couponStatuses.get(i).getDinner(),messupmenu.get(i).getDate(),couponStatuses.get(i).getWeekday());
            messStatuses.add(messStatus);
            String daydate = messupmenu.get(i).getDate();
            String day = couponStatuses.get(i).getWeekday();
            String bs = couponStatuses.get(i).getBreakfast();
            String ls = couponStatuses.get(i).getLunch();
            String ds = couponStatuses.get(i).getDinner();
            String breakfastdate = daydate + " 09:15:00";
            String lunchdate = daydate + " 14:15:00";
            String dinnerdate = daydate + " 21:15:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date bfdate = sdf.parse(breakfastdate);
                Date lnchdate = sdf.parse(lunchdate);
                Date dindate = sdf.parse(dinnerdate);
                Date todaydate = Calendar.getInstance().getTime();
                String bmess = "notissued";
                String lmess = "notissued";
                String dmess = "notissued";
                String bmenu = "";
                String lmenu = "";
                String dmenu = "";
                if(bs.charAt(2) == '0'){
                    bmess = "Ground floor Mess";
                    bmenu = messdownmenu.get(i).getBreakfast();
                }
                else if(bs.charAt(2) == '1'){
                    bmess = "First floor Mess";
                    bmenu = messupmenu.get(i).getBreakfast();
                }
                if(ls.charAt(2) == '0'){
                    lmess = "Ground floor Mess";
                    lmenu = messdownmenu.get(i).getLunch();
                }
                else if(ls.charAt(2) == '1'){
                    lmess = "First floor Mess";
                    lmenu = messupmenu.get(i).getLunch();
                }
                if(ds.charAt(2) == '0'){
                    dmess = "Ground floor Mess";
                    dmenu = messdownmenu.get(i).getDinner();
                }
                else if(ds.charAt(2) == '1'){
                    dmess = "First floor Mess";
                    dmenu = messupmenu.get(i).getDinner();
                }
                Coupon bfcoupon = new Coupon("Breakfast",bmess,day,daydate,bmenu);
                Coupon lnccoupon = new Coupon("Lunch",lmess,day,daydate,lmenu);
                Coupon dincoupon = new Coupon("Dinner",dmess,day,daydate,dmenu);
                if(todaydate.before(bfdate)){
                    if(!bmess.contains("notissued")){
                        coupons.add(bfcoupon);
                    }
                    if(!lmess.contains("notissued")){
                        coupons.add(lnccoupon);
                    }
                    if(!dmess.contains("notissued")){
                        coupons.add(dincoupon);
                    }

                }
                else if(todaydate.after(bfdate) && todaydate.before(lnchdate)){
                    if(!lmess.contains("notissued")){
                        coupons.add(lnccoupon);
                    }
                    if(!dmess.contains("notissued")){
                        coupons.add(dincoupon);
                    }


                }
                else if(todaydate.after(lnchdate) && todaydate.before(dindate)){
                    if(!dmess.contains("notissued")){
                        coupons.add(dincoupon);
                    }

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!messStatuses.isEmpty()){
            gridAdapter = new GridAdapter(this,messStatuses);
            gridview.setAdapter(gridAdapter);
        }
        if(!coupons.isEmpty()){
            viewpageAdapter = new ViewpageAdapter(RoseiActivity.this,coupons);
            viewpageAdapter.notifyDataSetChanged();
            viewPager.setAdapter(viewpageAdapter);
        }

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
