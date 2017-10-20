package com.anmol.rosei.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.List;

/**
 * Created by anmol on 10/19/2017.
 */

public class Mess2Adapter extends ArrayAdapter<mess2> {
    private Activity context;
    private int resource;
    private List<mess2> mess2s;


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
            foodb.setText(mess2s.get(position).getBrkfast());
            foodl.setText(mess2s.get(position).getLnch());
            foodd.setText(mess2s.get(position).getDinnr());
            day.setText(mess2s.get(position).getDay());
            CheckBox b = (CheckBox)v.findViewById(R.id.b);
            CheckBox l = (CheckBox)v.findViewById(R.id.l);
            CheckBox d = (CheckBox)v.findViewById(R.id.d);
            final RadioButton bnv = (RadioButton)v.findViewById(R.id.bnv);
            final RadioButton bv = (RadioButton)v.findViewById(R.id.bv);
            final RadioButton lnv = (RadioButton)v.findViewById(R.id.lnv);
            final RadioButton lv = (RadioButton)v.findViewById(R.id.lv);
            final RadioButton dnv = (RadioButton)v.findViewById(R.id.dnv);
            final RadioButton dv = (RadioButton)v.findViewById(R.id.dv);
            b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        bnv.setVisibility(View.VISIBLE);
                        bv.setVisibility(View.VISIBLE);
                    }
                    else if(!compoundButton.isChecked()){
                        bnv.setVisibility(View.INVISIBLE);
                        bv.setVisibility(View.INVISIBLE);
                    }
                }
            });
            l.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        lnv.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.VISIBLE);
                    }
                    else if(!compoundButton.isChecked()){
                        lnv.setVisibility(View.INVISIBLE);
                        lv.setVisibility(View.INVISIBLE);
                    }
                }
            });
            d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        dnv.setVisibility(View.VISIBLE);
                        dv.setVisibility(View.VISIBLE);
                    }
                    else if(!compoundButton.isChecked()){
                        dnv.setVisibility(View.INVISIBLE);
                        dv.setVisibility(View.INVISIBLE);
                    }
                }
            });

            return v;
        }

    }
}
