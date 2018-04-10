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
        if(!messStatuses.get(position).getBreakfast().contains("NotIssued")){
            if(messStatuses.get(position).getBreakfast().contains("1")){
                holder.bs.setText("G");
                if(messStatuses.get(position).getBreakfast().contains("N")){
                    holder.bs.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getBreakfast().contains("V")){
                    holder.bs.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
            else if(messStatuses.get(position).getBreakfast().contains("2")){
                holder.bs.setText("1");
                if(messStatuses.get(position).getBreakfast().contains("N")){
                    holder.bs.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getBreakfast().contains("V")){
                    holder.bs.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
        }
        else{
            holder.bs.setText("");
            holder.bs.setBackgroundColor(c.getResources().getColor(R.color.white));
        }
        if(!messStatuses.get(position).getLunch().contains("NotIssued")){
            if(messStatuses.get(position).getLunch().contains("1")){
                holder.ls.setText("G");
                if(messStatuses.get(position).getLunch().contains("N")){
                    holder.ls.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getLunch().contains("V")){
                    holder.ls.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
            else if(messStatuses.get(position).getLunch().contains("2")){
                holder.ls.setText("1");
                if(messStatuses.get(position).getLunch().contains("N")){
                    holder.ls.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getLunch().contains("V")){
                    holder.ls.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
        }
        else{
            holder.ls.setText("");
            holder.ls.setBackgroundColor(c.getResources().getColor(R.color.white));
        }
        if(!messStatuses.get(position).getDinner().contains("NotIssued")){
            if(messStatuses.get(position).getDinner().contains("1")){
                holder.ds.setText("G");
                if(messStatuses.get(position).getDinner().contains("N")){
                    holder.ds.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getDinner().contains("V")){
                    holder.ds.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
            else if(messStatuses.get(position).getDinner().contains("2")){
                holder.ds.setText("1");
                if(messStatuses.get(position).getDinner().contains("N")){
                    holder.ds.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getDinner().contains("V")){
                    holder.ds.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
        }
        else{
            holder.ds.setText("");
            holder.ds.setBackgroundColor(c.getResources().getColor(R.color.white));
        }
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

