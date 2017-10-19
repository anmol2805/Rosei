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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
        RadioButton bnv = (RadioButton)v.findViewById(R.id.bnv);
        RadioButton bv = (RadioButton)v.findViewById(R.id.bv);
        RadioButton lnv = (RadioButton)v.findViewById(R.id.lnv);
        RadioButton lv = (RadioButton)v.findViewById(R.id.lv);
        RadioButton dnv = (RadioButton)v.findViewById(R.id.dnv);
        RadioButton dv = (RadioButton)v.findViewById(R.id.dv);
        return v;
    }
}
