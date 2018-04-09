package com.anmol.rosei.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.anmol.rosei.Model.MessStatus;
import com.anmol.rosei.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
    Context c;
    List<MessStatus> messStatuses;

    public GridAdapter(Context c, List<MessStatus> messStatuses) {
        this.c = c;
        this.messStatuses = messStatuses;
    }

    @Override
    public GridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.gridlayout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GridAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messStatuses.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        Button bs,ls,ds;
        public MyViewHolder(View itemView) {
            super(itemView);
            bs = itemView.findViewById(R.id.bs);
            ls = itemView.findViewById(R.id.ls);
            ds = itemView.findViewById(R.id.ds);
        }


    }
}

