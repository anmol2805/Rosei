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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    Boolean edit;
    public Mess2Adapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<mess2> objects, boolean edit) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        mess2s = objects;
        this.edit = edit;
    }
    public int getViewTypeCount() {
        return getCount();
    }

    public int getItemViewType(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView!=null){
            return convertView;
        }
        else {
            final JSONObject breakfast = new JSONObject();
            final JSONObject lunch = new JSONObject();
            final JSONObject dinner = new JSONObject();
            JSONObject meal = new JSONObject();

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
                            breakfast.put("isMessUp",true);
                            breakfast.put("isVeg",true);
                            breakfast.put("food",mess2s.get(position).getBrkfast());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(!compoundButton.isChecked()){
                        bnv.setVisibility(View.INVISIBLE);
                        bv.setVisibility(View.INVISIBLE);
                        try {
                            breakfast.put("isSelected",false);
                            breakfast.put("food",mess2s.get(position).getBrkfastdown());
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
                            lunch.put("isMessUp",true);
                            lunch.put("isVeg",true);
                            lunch.put("food",mess2s.get(position).getLnch());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(!compoundButton.isChecked()){
                        lnv.setVisibility(View.INVISIBLE);
                        lv.setVisibility(View.INVISIBLE);
                        try {
                            lunch.put("isSelected",false);
                            lunch.put("food",mess2s.get(position).getLnchdown());
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
                            dinner.put("isMessUp",true);
                            dinner.put("isVeg",true);
                            dinner.put("food",mess2s.get(position).getDinnr());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(!compoundButton.isChecked()){
                        dnv.setVisibility(View.INVISIBLE);
                        dv.setVisibility(View.INVISIBLE);
                        try {
                            dinner.put("isSelected",false);
                            dinner.put("food",mess2s.get(position).getDinnrdown());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            ArrayList<String> foodsdown = new ArrayList<>();
            foodsdown.add(mess2s.get(position).getBrkfastdown());
            foodsdown.add(mess2s.get(position).getLnchdown());
            foodsdown.add(mess2s.get(position).getDinnrdown());


            ArrayList<String> foodsup = new ArrayList<>();
            foodsup.add(mess2s.get(position).getBrkfast());
            foodsup.add(mess2s.get(position).getLnch());
            foodsup.add(mess2s.get(position).getDinnr());

            CardView brk = (CardView)v.findViewById(R.id.brk);
            CardView lnch = (CardView)v.findViewById(R.id.lnch);
            CardView dnnr = (CardView)v.findViewById(R.id.dnnr);
            String brkstat = mess2s.get(position).getBs();
            String lnchstat = mess2s.get(position).getLs();
            String dnrstat = mess2s.get(position).getDs();
            ArrayList<String> mealstatuses = new ArrayList<>();
            mealstatuses.add(brkstat);
            mealstatuses.add(lnchstat);
            mealstatuses.add(dnrstat);
            ArrayList<JSONObject> meals = new ArrayList<>();
            meals.add(breakfast);
            meals.add(lunch);
            meals.add(dinner);
            final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
            checkBoxes.add(b);
            checkBoxes.add(l);
            checkBoxes.add(d);
            final ArrayList<TextView> textViews = new ArrayList<>();
            textViews.add(bstatus);
            textViews.add(lstatus);
            textViews.add(dstatus);
            ArrayList<CardView> cardViews = new ArrayList<>();
            cardViews.add(brk);
            cardViews.add(lnch);
            cardViews.add(dnnr);
            ArrayList<RadioButton> nonvegradios = new ArrayList<>();
            nonvegradios.add(bnv);
            nonvegradios.add(lnv);
            nonvegradios.add(dnv);
            ArrayList<RadioButton> vegradios = new ArrayList<>();
            vegradios.add(bv);
            vegradios.add(lv);
            vegradios.add(dv);
            int i=0;
            while (i<mealstatuses.size()){
                try{
                    if(mealstatuses.get(i).charAt(0) == '1'){
                        checkBoxes.get(i).setVisibility(View.INVISIBLE);
                        meals.get(i).put("isSelected",true);
                        //checkBoxes.get(i).setChecked(false);
                        if(mealstatuses.get(i).charAt(2) == '0'){
                            meals.get(i).put("food",foodsdown.get(i));
                            meals.get(i).put("isMessUp",false);
                            textViews.get(i).setText("Ground Floor");
                            if(mealstatuses.get(i).charAt(1)=='0'){
                                nonvegradios.get(i).setChecked(true);
                                meals.get(i).put("isVeg",false);
                                textViews.get(i).setTextColor(getContext().getResources().getColor(R.color.nonveg));
                            }
                            else {
                                vegradios.get(i).setChecked(true);
                                meals.get(i).put("isVeg",true);
                                textViews.get(i).setTextColor(getContext().getResources().getColor(R.color.veg));
                            }
                        }
                        else {
                            if(edit){
                                checkBoxes.get(i).setVisibility(View.VISIBLE);
                                checkBoxes.get(i).setChecked(true);
                            }
                            meals.get(i).put("food",foodsup.get(i));
                            meals.get(i).put("isMessUp",true);
                            textViews.get(i).setText("First Floor");
                            if(mealstatuses.get(i).charAt(1)=='0'){
                                nonvegradios.get(i).setChecked(true);
                                meals.get(i).put("isVeg",false);
                                textViews.get(i).setTextColor(getContext().getResources().getColor(R.color.nonveg));
                            }
                            else {
                                vegradios.get(i).setChecked(true);
                                meals.get(i).put("isVeg",true);
                                textViews.get(i).setTextColor(getContext().getResources().getColor(R.color.veg));
                            }
                        }
                        textViews.get(i).setVisibility(View.VISIBLE);
                    }
                    else {
                        meals.get(i).put("isSelected",false);
                        meals.get(i).put("isVeg",false);
                        meals.get(i).put("isMessUp",false);
                        meals.get(i).put("food",foodsdown.get(i));
                        textViews.get(i).setVisibility(View.INVISIBLE);
                        checkBoxes.get(i).setVisibility(View.VISIBLE);
                        final int finalI = i;
                        cardViews.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(checkBoxes.get(finalI).isChecked()){
                                    checkBoxes.get(finalI).setChecked(false);
                                }
                                else if(!checkBoxes.get(finalI).isChecked()){
                                    checkBoxes.get(finalI).setChecked(true);
                                }
                            }
                        });
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                i++;
            }
            try{
//                if(brkstat.charAt(0) == '1'){
//                    breakfast.put("isSelected",true);
//                    b.setVisibility(View.INVISIBLE);
//                    if(brkstat.charAt(2) == '0'){
//
//                        breakfast.put("isMessUp",false);
//                        bstatus.setText("Ground Floor");
//                        if(brkstat.charAt(1)=='0'){
//                            bnv.setChecked(true);
//                            breakfast.put("isVeg",false);
//                            bstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
//                        }
//                        else {
//                            bv.setChecked(true);
//                            breakfast.put("isVeg",true);
//                            bstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
//                        }
//                    }
//                    else {
//                        b.setVisibility(View.VISIBLE);
//                        b.setChecked(true);
//                        breakfast.put("isMessUp",true);
//                        bstatus.setText("First Floor");
//                        if(brkstat.charAt(1)=='0'){
//                            bnv.setChecked(true);
//                            breakfast.put("isVeg",false);
//                            bstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
//                        }
//                        else {
//                            bv.setChecked(true);
//                            breakfast.put("isVeg",true);
//                            bstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
//                        }
//                    }
//                    bstatus.setVisibility(View.VISIBLE);
//                }
//                else {
//                    breakfast.put("isSelected",false);
//                    breakfast.put("isVeg",false);
//                    breakfast.put("isMessUp",false);
//                    bstatus.setVisibility(View.INVISIBLE);
//                    b.setVisibility(View.VISIBLE);
//                    brk.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if(b.isChecked()){
//                                b.setChecked(false);
//                            }
//                            else if(!b.isChecked()){
//                                b.setChecked(true);
//                            }
//                        }
//                    });
//                }
//                if(lnchstat.charAt(0)=='1'){
//                    l.setVisibility(View.INVISIBLE);
//                    lunch.put("isSelected",true);
//                    if(lnchstat.charAt(2)=='0'){
//
//                        lunch.put("isMessUp",false);
//                        lstatus.setText("Ground Floor");
//                        if(lnchstat.charAt(1)=='0'){
//                            lnv.setChecked(true);
//                            lunch.put("isVeg",false);
//                            lstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
//                        }
//                        else {
//                            lv.setChecked(true);
//                            lunch.put("isVeg",true);
//                            lstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
//                        }
//                    }
//                    else{
//                        l.setVisibility(View.VISIBLE);
//                        l.setChecked(true);
//                        lunch.put("isMessUp",true);
//                        lstatus.setText("First Floor");
//                        if(lnchstat.charAt(1)=='0'){
//                            lnv.setChecked(true);
//                            lunch.put("isVeg",false);
//                            lstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
//                        }
//                        else {
//                            lv.setChecked(true);
//                            lunch.put("isVeg",true);
//                            lstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
//                        }
//                    }
//                    lstatus.setVisibility(View.VISIBLE);
//                }
//                else {
//                    lunch.put("isSelected",false);
//                    lunch.put("isVeg",false);
//                    lunch.put("isMessUp",false);
//                    lstatus.setVisibility(View.INVISIBLE);
//                    l.setVisibility(View.VISIBLE);
//                    lnch.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if(l.isChecked()){
//                                l.setChecked(false);
//                            }
//                            else if(!l.isChecked()){
//                                l.setChecked(true);
//                            }
//                        }
//                    });
//
//                }
//                if(dnrstat.charAt(0)=='1'){
//                    d.setVisibility(View.INVISIBLE);
//                    dinner.put("isSelected",true);
//                    if(dnrstat.charAt(2)=='0'){
//
//                        dinner.put("isMessUp",false);
//                        dstatus.setText("Ground Floor");
//                        if(dnrstat.charAt(1)=='0'){
//                            dnv.setChecked(true);
//                            dinner.put("isVeg",false);
//                            dstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
//                        }
//                        else {
//                            dv.setChecked(true);
//                            dinner.put("isVeg",true);
//                            dstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
//                        }
//                    }
//                    else{
//                        d.setVisibility(View.VISIBLE);
//                        d.setChecked(true);
//                        dinner.put("isMessUp",true);
//                        dstatus.setText("First Floor");
//                        if(dnrstat.charAt(1)=='0'){
//                            dnv.setChecked(true);
//                            dinner.put("isVeg",false);
//                            dstatus.setTextColor(getContext().getResources().getColor(R.color.nonveg));
//                        }
//                        else {
//                            dv.setChecked(true);
//                            dinner.put("isVeg",true);
//                            dstatus.setTextColor(getContext().getResources().getColor(R.color.veg));
//                        }
//                    }
//                    dstatus.setVisibility(View.VISIBLE);
//                }
//                else {
//                    dinner.put("isSelected",false);
//                    dinner.put("isVeg",false);
//                    dinner.put("isMessUp",false);
//                    dstatus.setVisibility(View.INVISIBLE);
//                    d.setVisibility(View.VISIBLE);
//                    dnnr.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if(d.isChecked()){
//                                d.setChecked(false);
//                            }
//                            else if(!d.isChecked()){
//                                d.setChecked(true);
//                            }
//                        }
//                    });
//                }
                System.out.println("floor:" + breakfast + " " + lunch + " " + dinner);


                meal.put("breakfast",breakfast);
                meal.put("lunch",lunch);
                meal.put("dinner",dinner);

                coupon.put(dayobject,meal);
                jsonObject2.put("coupon",coupon);
                System.out.println("floor:" + coupon);
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
