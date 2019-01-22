package com.anmol.rosei.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anmol.rosei.Model.Coupon;
import com.anmol.rosei.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewpageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Coupon> coupons;

    public ViewpageAdapter(Context context, List<Coupon> coupons) {
        this.context = context;
        this.coupons = coupons;
    }

    @Override
    public int getCount() {
        return coupons.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = layoutInflater.inflate(R.layout.menulayout,null);
        TextView meal = (TextView)vi.findViewById(R.id.meal);
        TextView mess = (TextView)vi.findViewById(R.id.mess);
        TextView day = (TextView)vi.findViewById(R.id.day);
        TextView date = (TextView)vi.findViewById(R.id.date);
        final TextView item = (TextView)vi.findViewById(R.id.item);
        meal.setText(coupons.get(position).getMeal());
        date.setText(coupons.get(position).getDate());
        mess.setText(coupons.get(position).getMess());
        day.setText(coupons.get(position).getDay());
        item.setText(coupons.get(position).getMenuitem());
        ViewPager viewPager = (ViewPager)container;
        viewPager.addView(vi,0);
        return vi;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager)container;
        View view = (View)object;
        viewPager.removeView(view);
    }
}
