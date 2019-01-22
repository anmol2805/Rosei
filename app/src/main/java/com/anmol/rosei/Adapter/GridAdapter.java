package com.anmol.rosei.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anmol.rosei.Model.MessStatus;
import com.anmol.rosei.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        holder.day.setText(messStatuses.get(position).getDay());
        String date = messStatuses.get(position).getcoupondate();
        String breakfastdate = date + " 09:15:00";
        String lunchdate = date + " 14:15:00";
        String dinnerdate = date + " 21:15:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date bdate = sdf.parse(breakfastdate);
            Date ldate = sdf.parse(lunchdate);
            Date ddate = sdf.parse(dinnerdate);
            Date currentdate = Calendar.getInstance().getTime();
            String breakfast = messStatuses.get(position).getBreakfast();
            String lunch = messStatuses.get(position).getLunch();
            String dinner = messStatuses.get(position).getDinner();
            if(breakfast.charAt(0) == '1'){
                if(breakfast.charAt(2) == '0'){
                    holder.bst.setText("1");
                    if(breakfast.charAt(1) == '0'){
                        if (currentdate.before(bdate))
                            holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.nonveg));
                        else
                            holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.inactivered));
                    }
                    else {
                        if (currentdate.before(bdate))
                        holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.veg));
                        else
                            holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.inactivegreen));
                    }
                }
                else{
                    holder.bst.setText("2");
                    if(breakfast.charAt(1) == '0'){
                        if (currentdate.before(bdate))
                        holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.nonveg));
                        else
                            holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.inactivered));
                    }
                    else {
                        if (currentdate.before(bdate))
                        holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.veg));
                        else
                            holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.inactivegreen));
                    }
                }
            }
            else{
                holder.bst.setText("");
                holder.bs.setCardBackgroundColor(c.getResources().getColor(R.color.dull));
            }
            if(lunch.charAt(0) == '1'){
                if(lunch.charAt(2) == '0'){
                    holder.lst.setText("1");
                    if(lunch.charAt(1) == '0'){
                        if (currentdate.before(ldate))
                        holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.nonveg));
                        else
                            holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.inactivered));

                    }
                    else {
                        if (currentdate.before(ldate))
                        holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.veg));
                        else
                            holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.inactivegreen));
                    }
                }
                else {
                    holder.lst.setText("2");
                    if(lunch.charAt(1) == '0'){
                        if (currentdate.before(ldate))
                        holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.nonveg));
                        else
                            holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.inactivered));
                    }
                    else {
                        if (currentdate.before(ldate))
                        holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.veg));
                        else
                            holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.inactivegreen));
                    }
                }
            }
            else{
                holder.lst.setText("");
                holder.ls.setCardBackgroundColor(c.getResources().getColor(R.color.dull));
            }
            if(dinner.charAt(0) == '1'){
                if(dinner.charAt(2)=='0'){
                    holder.dst.setText("1");
                    if(dinner.charAt(1)=='0'){
                        if (currentdate.before(ddate))
                        holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.nonveg));
                        else
                            holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.inactivered));
                    }
                    else {
                        if (currentdate.before(ddate))
                        holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.veg));
                        else
                            holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.inactivegreen));
                    }
                }
                else {
                    holder.dst.setText("2");
                    if(dinner.charAt(1) == '0'){
                        if (currentdate.before(ddate))
                        holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.nonveg));
                        else
                            holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.inactivered));
                    }
                    else {
                        if (currentdate.before(ddate))
                        holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.veg));
                        else
                            holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.inactivegreen));
                    }
                }
            }
            else{
                holder.dst.setText("");
                holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.dull));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return messStatuses.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView bs,ls,ds;
        TextView bst,lst,dst,day;
        public MyViewHolder(View itemView) {
            super(itemView);
            day = (TextView)itemView.findViewById(R.id.day);
            bs = (CardView) itemView.findViewById(R.id.bs);
            ls = (CardView) itemView.findViewById(R.id.ls);
            ds = (CardView)itemView.findViewById(R.id.ds);
            bst = (TextView) itemView.findViewById(R.id.bst);
            lst = (TextView) itemView.findViewById(R.id.lst);
            dst = (TextView) itemView.findViewById(R.id.dst);
        }


    }
}

