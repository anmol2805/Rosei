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
                holder.bst.setText("1");
                if(messStatuses.get(position).getBreakfast().contains("N")){
                    holder.bs.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getBreakfast().contains("V")){
                    holder.bs.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
            else if(messStatuses.get(position).getBreakfast().contains("2")){
                holder.bst.setText("2");
                if(messStatuses.get(position).getBreakfast().contains("N")){
                    holder.bs.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getBreakfast().contains("V")){
                    holder.bs.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
        }
        else{
            holder.bst.setText("");
            holder.bs.setBackgroundColor(c.getResources().getColor(R.color.dull));
        }
        if(!messStatuses.get(position).getLunch().contains("NotIssued")){
            if(messStatuses.get(position).getLunch().contains("1")){
                holder.lst.setText("1");
                if(messStatuses.get(position).getLunch().contains("N")){
                    holder.ls.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getLunch().contains("V")){
                    holder.ls.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
            else if(messStatuses.get(position).getLunch().contains("2")){
                holder.lst.setText("2");
                if(messStatuses.get(position).getLunch().contains("N")){
                    holder.ls.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getLunch().contains("V")){
                    holder.ls.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
        }
        else{
            holder.lst.setText("");
            holder.ls.setBackgroundColor(c.getResources().getColor(R.color.dull));
        }
        if(!messStatuses.get(position).getDinner().contains("NotIssued")){
            if(messStatuses.get(position).getDinner().contains("1")){
                holder.dst.setText("1");
                if(messStatuses.get(position).getDinner().contains("N")){
                    holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getDinner().contains("V")){
                    holder.ds.setCardBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
            else if(messStatuses.get(position).getDinner().contains("2")){
                holder.dst.setText("2");
                if(messStatuses.get(position).getDinner().contains("N")){
                    holder.ds.setBackgroundColor(c.getResources().getColor(R.color.nonveg));
                }
                else if(messStatuses.get(position).getDinner().contains("V")){
                    holder.ds.setBackgroundColor(c.getResources().getColor(R.color.veg));
                }
            }
        }
        else{
            holder.dst.setText("");
            holder.ds.setBackgroundColor(c.getResources().getColor(R.color.dull));
        }
    }

    @Override
    public int getItemCount() {
        return messStatuses.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView bs,ls,ds;
        TextView bst,lst,dst;
        public MyViewHolder(View itemView) {
            super(itemView);
            bs = (CardView) itemView.findViewById(R.id.bs);
            ls = (CardView) itemView.findViewById(R.id.ls);
            ds = (CardView)itemView.findViewById(R.id.ds);
            bst = (TextView) itemView.findViewById(R.id.bst);
            lst = (TextView) itemView.findViewById(R.id.lst);
            dst = (TextView) itemView.findViewById(R.id.dst);
        }


    }
}

