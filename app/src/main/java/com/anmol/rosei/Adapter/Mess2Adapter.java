package com.anmol.rosei.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.anmol.rosei.Model.mess2;
import com.anmol.rosei.R;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by anmol on 10/19/2017.
 */

public class Mess2Adapter extends ArrayAdapter<mess2> {
    private Activity context;
    private int resource;
    private List<mess2> mess2s;
    JSONObject jsonObject2 = new JSONObject();
    JSONObject coupon = new JSONObject();
    JSONObject breakfast = new JSONObject();
    JSONObject lunch = new JSONObject();
    JSONObject dinner = new JSONObject();
    JSONObject meal = new JSONObject();


    public Mess2Adapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<mess2> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        mess2s = objects;
    }
    public int getViewTypeCount() {
        return getCount();
    }

    public int getItemViewType(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView!=null){
            return convertView;
        }
        else {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(resource,null);
            TextView foodb = (TextView)v.findViewById(R.id.foodb);
            TextView foodl = (TextView)v.findViewById(R.id.foodl);
            TextView foodd = (TextView)v.findViewById(R.id.foodd);
            TextView day = (TextView)v.findViewById(R.id.day);
            TextView bstatus = (TextView)v.findViewById(R.id.bstatus);
            TextView lstatus = (TextView)v.findViewById(R.id.lstatus);
            TextView dstatus = (TextView)v.findViewById(R.id.dstatus);
            foodb.setText(mess2s.get(position).getBrkfast());
            foodl.setText(mess2s.get(position).getLnch());
            foodd.setText(mess2s.get(position).getDinnr());
            final String weekday = mess2s.get(position).getDay();
            String dayobject = weekday.substring(0,3);
            day.setText(weekday);
            final CheckBox b = (CheckBox)v.findViewById(R.id.b);
            final CheckBox l = (CheckBox)v.findViewById(R.id.l);
            final CheckBox d = (CheckBox)v.findViewById(R.id.d);
            final RadioButton bnv = (RadioButton)v.findViewById(R.id.bnv);
            final RadioButton bv = (RadioButton)v.findViewById(R.id.bv);
            final RadioButton lnv = (RadioButton)v.findViewById(R.id.lnv);
            final RadioButton lv = (RadioButton)v.findViewById(R.id.lv);
            final RadioButton dnv = (RadioButton)v.findViewById(R.id.dnv);
            final RadioButton dv = (RadioButton)v.findViewById(R.id.dv);
            bv.setChecked(true);
            lv.setChecked(true);
            dv.setChecked(true);
            try{
                breakfast.put("isMessUp",true);
                lunch.put("isMessUp",true);
                dinner.put("isMessUp",true);
                breakfast.put("isVeg",true);
                lunch.put("isVeg",true);
                dinner.put("isVeg",true);
                breakfast.put("isSelected",false);
                lunch.put("isSelected",false);
                dinner.put("isSelected",false);
            }

            catch(JSONException e){
                e.printStackTrace();
            }

            bnv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        bv.setChecked(false);
                        try {
                            breakfast.put("isVeg",false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            bv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        bnv.setChecked(false);
                        try {
                            breakfast.put("isVeg",true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            lnv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        lv.setChecked(false);
                        try {
                            lunch.put("isVeg",false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            lv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        lnv.setChecked(false);
                        try {
                            lunch.put("isVeg",true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            dnv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        dv.setChecked(false);
                        try {
                            dinner.put("isVeg",false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            dv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        dnv.setChecked(false);
                        try {
                            dinner.put("isVeg",true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        bnv.setVisibility(View.VISIBLE);
                        bv.setVisibility(View.VISIBLE);
                        try {
                            breakfast.put("isSelected",true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(!compoundButton.isChecked()){
                        bnv.setVisibility(View.INVISIBLE);
                        bv.setVisibility(View.INVISIBLE);
                        try {
                            breakfast.put("isSelected",false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            l.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        lnv.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.VISIBLE);
                        try {
                            lunch.put("isSelected",true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(!compoundButton.isChecked()){
                        lnv.setVisibility(View.INVISIBLE);
                        lv.setVisibility(View.INVISIBLE);
                        try {
                            lunch.put("isSelected",false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        dnv.setVisibility(View.VISIBLE);
                        dv.setVisibility(View.VISIBLE);
                        try {
                            dinner.put("isSelected",true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(!compoundButton.isChecked()){
                        dnv.setVisibility(View.INVISIBLE);
                        dv.setVisibility(View.INVISIBLE);
                        try {
                            dinner.put("isSelected",false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            CardView brk = (CardView)v.findViewById(R.id.brk);
            CardView lnch = (CardView)v.findViewById(R.id.lnch);
            CardView dnnr = (CardView)v.findViewById(R.id.dnnr);
            String brkstat = mess2s.get(position).getBs();
            String lnchstat = mess2s.get(position).getLs();
            String dnrstat = mess2s.get(position).getDs();
            if(brkstat.charAt(0) == '1'){
                b.setChecked(false);
                if(brkstat.charAt(2) == '0'){
                    bstatus.setText("Ground Floor");
                    if(brkstat.charAt(1)=='0'){
                        bstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
                    }
                    else {
                        bstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
                    }
                }
                else {
                    bstatus.setText("First Floor");
                    if(brkstat.charAt(1)=='0'){
                        bstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
                    }
                    else {
                        bstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
                    }
                }
                bstatus.setVisibility(View.VISIBLE);
            }
            else {
                bstatus.setVisibility(View.INVISIBLE);
                b.setVisibility(View.VISIBLE);
                brk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(b.isChecked()){
                            b.setChecked(false);
                        }
                        else if(!b.isChecked()){
                            b.setChecked(true);
                        }
                    }
                });
            }
            if(lnchstat.charAt(0)=='1'){
                l.setChecked(false);

                if(lnchstat.charAt(2)=='0'){
                    lstatus.setText("Ground Floor");
                    if(lnchstat.charAt(1)=='0'){
                        lstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
                    }
                    else {
                        lstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
                    }
                }
                else{
                    lstatus.setText("First Floor");
                    if(lnchstat.charAt(1)=='0'){
                        lstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
                    }
                    else {
                        lstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
                    }
                }
                lstatus.setVisibility(View.VISIBLE);
            }
            else {
                lstatus.setVisibility(View.INVISIBLE);
                l.setVisibility(View.VISIBLE);
                lnch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(l.isChecked()){
                            l.setChecked(false);
                        }
                        else if(!l.isChecked()){
                            l.setChecked(true);
                        }
                    }
                });

            }
            if(dnrstat.charAt(0)=='1'){
                d.setChecked(false);
                if(dnrstat.charAt(2)=='0'){
                    dstatus.setText("Ground Floor");
                    if(dnrstat.charAt(1)=='0'){
                        dstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
                    }
                    else {
                        dstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
                    }
                }
                else{
                    dstatus.setText("First Floor");
                    if(dnrstat.charAt(1)=='0'){
                        dstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
                    }
                    else {
                        dstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
                    }
                }
                dstatus.setVisibility(View.VISIBLE);
            }
            else {
                dstatus.setVisibility(View.INVISIBLE);
                d.setVisibility(View.VISIBLE);
                dnnr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(d.isChecked()){
                            d.setChecked(false);
                        }
                        else if(!d.isChecked()){
                            d.setChecked(true);
                        }
                    }
                });
            }
            try {
                jsonObject2.put("coupon",coupon);
                coupon.put(dayobject,meal);
                meal.put("breakfast",breakfast);
                meal.put("lunch",lunch);
                meal.put("dinner",dinner);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return v;
        }

    }
    public JSONObject getJsonObject() {
        return jsonObject2;
    }
}
