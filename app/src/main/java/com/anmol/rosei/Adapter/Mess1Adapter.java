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
import android.widget.TextView;

import com.anmol.rosei.Model.mess1;
import com.anmol.rosei.R;

import java.util.List;

/**
 * Created by anmol on 10/19/2017.
 */

public class Mess1Adapter extends ArrayAdapter<mess1> {
    private Activity context;
    private int resource;
    private List<mess1> mess1s;


    public Mess1Adapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<mess1> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        mess1s = objects;
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
        foodb.setText(mess1s.get(position).getBrkfast());
        foodl.setText(mess1s.get(position).getLnch());
        foodd.setText(mess1s.get(position).getDinnr());
        day.setText(mess1s.get(position).getDay());
        return v;
    }
}
