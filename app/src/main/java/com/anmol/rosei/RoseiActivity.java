package com.anmol.rosei;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anmol.rosei.Adapter.GridAdapter;
import com.anmol.rosei.Adapter.ViewpageAdapter;
import com.anmol.rosei.Helpers.AuthUser;
import com.anmol.rosei.Helpers.CurrentCouponDb;
import com.anmol.rosei.Helpers.MessDownMenuDb;
import com.anmol.rosei.Helpers.MessUpMenuDb;
import com.anmol.rosei.Model.Coupon;
import com.anmol.rosei.Model.CouponStatus;
import com.anmol.rosei.Model.MessStatus;
import com.anmol.rosei.Model.Mess_Menu;
import com.bumptech.glide.Glide;
import com.canopydevelopers.canopyauth.AuthConfig;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.fabtransitionactivity.SheetLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoseiActivity extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener{

    Animation rotate;
    android.support.design.widget.FloatingActionButton book;
    TextView stuid;
    TextView user;
    private static long back_pressed;
    List<MessStatus> messStatuses = new ArrayList<>();
    RecyclerView gridview;
    GridAdapter gridAdapter;
    ViewPager viewPager;
    List<Coupon> coupons = new ArrayList<>();
    ViewpageAdapter viewpageAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView emptytext;
    FloatingActionMenu settings;
    private static final int REQUEST_CODE = 1;
    SheetLayout mSheetLayout;
    FloatingActionButton about,cd,logout,epi;
    String gender;
    CircleImageView stuimg;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rosei);
        rotate = AnimationUtils.loadAnimation(RoseiActivity.this,R.anim.rotate);
        stuimg = (CircleImageView)findViewById(R.id.stuimg);
        book = (android.support.design.widget.FloatingActionButton) findViewById(R.id.book);
        about = (FloatingActionButton)findViewById(R.id.pp);
        epi = (FloatingActionButton)findViewById(R.id.epi);
        cd = (FloatingActionButton)findViewById(R.id.cd);
        logout = (FloatingActionButton)findViewById(R.id.logout);
        user = (TextView)findViewById(R.id.user);
        stuid = (TextView)findViewById(R.id.stuid);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        settings = (FloatingActionMenu) findViewById(R.id.settings);
        mSheetLayout = (SheetLayout)findViewById(R.id.bottom_sheet);
//        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshdata);
        gridview = (RecyclerView)findViewById(R.id.gridrecycler);
        emptytext = (TextView)findViewById(R.id.emptytext);
        gridview.setHasFixedSize(true);
        gridview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mSheetLayout.setFab(book);
        mSheetLayout.setFabAnimationEndListener(this);
        // booking activity

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSheetLayout.expandFab();
                //startActivity(new Intent(RoseiActivity.this,BookingnewActivity.class));
            }
        });

        final AuthConfig authConfig = new AuthConfig(this);
        final AuthUser authUser = new AuthUser(this);
        String urlid = "https://hib.iiit-bh.ac.in/Hibiscus/docs/iiit/Photos/" + authUser.readuser().toUpperCase() + ".jpg";
        Glide.with(getApplicationContext()).load(urlid).into(stuimg);
        gender = authUser.readgender();
        epi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(RoseiActivity.this);
                dialog.setContentView(R.layout.epi);
                dialog.setTitle("Edit details");
                final EditText fn = (EditText)dialog.findViewById(R.id.firstname);
                final RadioButton male = (RadioButton)dialog.findViewById(R.id.male);
                final RadioButton female = (RadioButton)dialog.findViewById(R.id.female);
                Button save = (Button)dialog.findViewById(R.id.save);
                fn.setText(authUser.readusername());
                if(gender.equals("female")){
                    female.setChecked(true);
                    male.setChecked(false);
                }else{
                    male.setChecked(true);
                }
                male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        female.setChecked(false);
                        gender = "male";
                    }
                });
                female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        male.setChecked(false);
                        gender = "female";
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uname = fn.getText().toString().trim();
                        if(uname.isEmpty()){
                            Toast.makeText(RoseiActivity.this,"Please enter your name",Toast.LENGTH_SHORT).show();
                        }else{
                            if(authUser.writegender(gender) && authUser.writeusername(uname)){
                                dialog.dismiss();
                                Toast.makeText(RoseiActivity.this,"Changes saved successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialog.show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoseiActivity.this,About.class));

            }
        });
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("mailto:"+"canopydevelopers@gmail.com"));
                startActivity(viewIntent);

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


        stuid.setText(authUser.readuser().toUpperCase());
        user.setText("Hello " + authUser.readusername());

        loadDataFromDb();

//        swipeRefreshLayout.setColorSchemeColors(
//                getResources().getColor(R.color.colorAccent)
//        );
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
//                //menu request
//                JsonArrayRequest menurequest = new JsonArrayRequest(Request.Method.GET, getResources().getString(R.string.root_url) + "/menu",null,new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray menuresponse) {
//                        System.out.println(menuresponse);
//                        final ArrayList<String> days = new ArrayList<>();
//                        days.add("mon");
//                        days.add("tue");
//                        days.add("wed");
//                        days.add("thr");
//                        days.add("fri");
//                        days.add("sat");
//                        days.add("sun");
//                        try {
//                            MessUpMenuDb messUpMenuDb = new MessUpMenuDb(RoseiActivity.this);
//                            MessDownMenuDb messDownMenuDb = new MessDownMenuDb(RoseiActivity.this);
//                            JSONObject messup = menuresponse.getJSONObject(0).getJSONObject("messUP");
//                            final String monday = messup.getJSONObject("mon").getString("date").substring(0,10);
//                            for(int i=0;i<days.size();i++) {
//                                String breakfast = messup.getJSONObject(days.get(i)).getString("breakfast");
//                                String lunch = messup.getJSONObject(days.get(i)).getString("lunch");
//                                String dinner = messup.getJSONObject(days.get(i)).getString("dinner");
//                                String date = messup.getJSONObject(days.get(i)).getString("date");
//                                Mess_Menu mess_menu = new Mess_Menu(days.get(i), breakfast, lunch, dinner, date.substring(0,10));
//                                messUpMenuDb.insertData(mess_menu);
//                                messUpMenuDb.updatenotice(mess_menu);
//                            }
//
//                            JSONObject messdown = menuresponse.getJSONObject(0).getJSONObject("messDown");
//                            for(int i=0;i<days.size();i++) {
//                                String breakfast = messdown.getJSONObject(days.get(i)).getString("breakfast");
//                                String lunch = messdown.getJSONObject(days.get(i)).getString("lunch");
//                                String dinner = messdown.getJSONObject(days.get(i)).getString("dinner");
//                                String date = messdown.getJSONObject(days.get(i)).getString("date");
//                                Mess_Menu mess_menu = new Mess_Menu(days.get(i), breakfast, lunch, dinner, date.substring(0,10));
//
//                                messDownMenuDb.insertData(mess_menu);
//                                messDownMenuDb.updatenotice(mess_menu);
//
//
//                            }
//
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                            String lastmonday = null;
//                            try {
//                                Date changedate = simpleDateFormat.parse(monday);
//                                System.out.println("coupondate:" + changedate);
//                                Date onedaybefore = new Date(changedate.getTime() - 8);
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                                lastmonday = sdf.format(onedaybefore);
//                                System.out.println("onedaybefore:" + onedaybefore);
//
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            //coupon request
//
//                            JsonObjectRequest currentcouponrequest = new JsonObjectRequest(Request.Method.GET, getResources().getString(R.string.root_url) + "/coupon/" + authUser.readuser() + "/" + lastmonday, null, new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject couponresponse) {
//                                    try {
//                                        System.out.println(couponresponse);
//                                        CurrentCouponDb currentcouponDb = new CurrentCouponDb(RoseiActivity.this);
//                                        JSONObject coupon = couponresponse.getJSONObject("coupon");
//                                        ArrayList<String> meals = new ArrayList<>();
//                                        meals.add("breakfast");
//                                        meals.add("lunch");
//                                        meals.add("dinner");
//                                        ArrayList<String> params = new ArrayList<>();
//                                        params.add("isSelected");
//                                        params.add("isVeg");
//                                        params.add("isMessUp");
//                                        for(int i=0;i<days.size();i++){
//                                            JSONObject day = coupon.getJSONObject(days.get(i));
//                                            ArrayList<StringBuilder> binaries = new ArrayList<>();
//                                            binaries.add(new StringBuilder("000"));
//                                            binaries.add(new StringBuilder("000"));
//                                            binaries.add(new StringBuilder("000"));
//                                            for(int j=0;j<meals.size();j++){
//                                                JSONObject meal = day.getJSONObject(meals.get(j));
//                                                String food = meal.getString("food");
//                                                binaries.get(j).append(food);
//                                                for(int k=0;k<params.size();k++){
//                                                    if(meal.getBoolean(params.get(k))){
//                                                        binaries.get(j).setCharAt(k,'1');
//                                                    }
//                                                }
//                                            }
//                                            System.out.println(days.get(i)+binaries.get(0).toString()+binaries.get(1).toString()+binaries.get(2).toString());
//                                            CouponStatus couponStatus = new CouponStatus(days.get(i),binaries.get(0).toString(),binaries.get(1).toString(),binaries.get(2).toString());
//                                            currentcouponDb.insertData(couponStatus);
//                                            currentcouponDb.updatenotice(couponStatus);
//                                            swipeRefreshLayout.setRefreshing(false);
//                                            loadDataFromDb();
//
//
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    swipeRefreshLayout.setRefreshing(false);
//                                    Toast.makeText(RoseiActivity.this,"Unable to load Coupons",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            Mysingleton.getInstance(RoseiActivity.this).addToRequestqueue(currentcouponrequest);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println("Error" + error);
//                        swipeRefreshLayout.setRefreshing(false);
//                        Toast.makeText(RoseiActivity.this,"Unable to load Menu",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                Mysingleton.getInstance(RoseiActivity.this).addToRequestqueue(menurequest);
//
//            }
//        });

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
            String daydate = messupmenu.get(i).getDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String lastmonday = null;
            try {
                Date changedate = simpleDateFormat.parse(daydate);
                System.out.println("coupondate:" + changedate);
                Date onedaybefore = new Date(changedate.getTime() - 604800000L);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                lastmonday = sdf.format(onedaybefore);
                System.out.println("onedaybefore:" + lastmonday);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            MessStatus messStatus = new MessStatus(couponStatuses.get(i).getBreakfast(),couponStatuses.get(i).getLunch(),couponStatuses.get(i).getDinner(),lastmonday,couponStatuses.get(i).getWeekday());
            messStatuses.add(messStatus);

            String day = couponStatuses.get(i).getWeekday();
            String bs = couponStatuses.get(i).getBreakfast();
            String ls = couponStatuses.get(i).getLunch();
            String ds = couponStatuses.get(i).getDinner();
            System.out.println("floor:" + bs + " " + ls + " " + ds);
            String breakfastdate = lastmonday + " 09:15:00";
            String lunchdate = lastmonday + " 14:15:00";
            String dinnerdate = lastmonday + " 21:15:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date bfdate = sdf.parse(breakfastdate);
                Date lnchdate = sdf.parse(lunchdate);
                Date dindate = sdf.parse(dinnerdate);
                Date todaydate = Calendar.getInstance().getTime();
                String bmess = "";
                String lmess = "";
                String dmess = "";

                String bmenu = bs.substring(3);
                String lmenu = ls.substring(3);
                String dmenu = ds.substring(3);
                if(bs.charAt(0)=='1'){
                    if(bs.charAt(2) == '0'){
                        bmess = "Ground floor Mess";
                    }
                    else if(bs.charAt(2) == '1'){
                        bmess = "First floor Mess";
                    }
                }
                if(ls.charAt(0)=='1'){
                    if(ls.charAt(2) == '0'){
                        lmess = "Ground floor Mess";
                    }
                    else if(ls.charAt(2) == '1'){
                        lmess = "First floor Mess";
                    }
                }
                if(ds.charAt(0)=='1'){
                    if(ds.charAt(2) == '0'){
                        dmess = "Ground floor Mess";
                    }
                    else if(ds.charAt(2) == '1'){
                        dmess = "First floor Mess";
                    }
                }
                Coupon bfcoupon = new Coupon("Breakfast",bmess,day,lastmonday,bmenu);
                Coupon lnccoupon = new Coupon("Lunch",lmess,day,lastmonday,lmenu);
                Coupon dincoupon = new Coupon("Dinner",dmess,day,lastmonday,dmenu);
                if(todaydate.before(bfdate)){
                    if(!bmess.isEmpty()){
                        coupons.add(bfcoupon);
                    }
                    if(!lmess.isEmpty()){
                        coupons.add(lnccoupon);
                    }
                    if(!dmess.isEmpty()){
                        coupons.add(dincoupon);
                    }

                }
                else if(todaydate.after(bfdate) && todaydate.before(lnchdate)){
                    if(!lmess.isEmpty()){
                        coupons.add(lnccoupon);
                    }
                    if(!dmess.isEmpty()){
                        coupons.add(dincoupon);
                    }


                }
                else if(todaydate.after(lnchdate) && todaydate.before(dindate)){
                    if(!dmess.isEmpty()){
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
            emptytext.setVisibility(View.INVISIBLE);
            viewpageAdapter = new ViewpageAdapter(RoseiActivity.this,coupons);
            viewpageAdapter.notifyDataSetChanged();
            viewPager.setAdapter(viewpageAdapter);
        }
        else{
            emptytext.setVisibility(View.VISIBLE);
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

    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, BookingnewActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            mSheetLayout.contractFab();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

}
