package com.anmol.rosei.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anmol.rosei.Model.Coupon;
import com.anmol.rosei.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        TextView starttime = (TextView)vi.findViewById(R.id.starttime);
        TextView endtime = (TextView)vi.findViewById(R.id.endtime);
        final TextView item = (TextView)vi.findViewById(R.id.item);
        ProgressBar progressBar = (ProgressBar)vi.findViewById(R.id.progressBar2);
        String mealtext = coupons.get(position).getMeal();
        String mealdate = coupons.get(position).getDate();

        if(mealtext.equals("Breakfast")){
            starttime.setText("7.30 a.m.");
            endtime.setText("9.15 a.m.");
            String time1 = mealdate + " 7:30:00";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format.parse(time1);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currenttime = sdf.format(cal.getTime());
                Date current = format.parse(currenttime);
                if(current.after(date1)){
                    long difference = current.getTime() - date1.getTime();
                    progressBar.setProgress((int) ((difference*100)/(1000*105*60)));
                }
                else {
                    progressBar.setProgress(1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(mealtext.equals("Lunch")){
            starttime.setText("12:00 p.m.");
            endtime.setText("2.00 p.m.");
            String time1 = mealdate + " 12:00:00";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format.parse(time1);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currenttime = sdf.format(cal.getTime());
                Date current = format.parse(currenttime);
                if(current.after(date1)){
                    long difference = current.getTime() - date1.getTime();
                    progressBar.setProgress((int) ((difference*100)/(1000*120*60)));
                }
                else {
                    progressBar.setProgress(1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(mealtext.equals("Dinner")){
            starttime.setText("7.00 p.m.");
            endtime.setText("9.30 p.m.");
            String time1 = mealdate + " 19:30:00";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format.parse(time1);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currenttime = sdf.format(cal.getTime());
                Date current = format.parse(currenttime);
                if(current.after(date1)){
                    long difference = current.getTime() - date1.getTime();
                    progressBar.setProgress((int) ((difference*100)/(1000*120*60)));
                }
                else {
                    progressBar.setProgress(1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        meal.setText(mealtext);
        date.setText(coupons.get(position).getDate());
        mess.setText(coupons.get(position).getMess());
        day.setText(coupons.get(position).getDay());
        String food = coupons.get(position).getMenuitem();
        if(food.charAt(1) == '1'){
            item.setTextColor(context.getResources().getColor(R.color.veg));
        }else{
            item.setTextColor(context.getResources().getColor(R.color.nonveg));
        }
        item.setText(food.substring(3));

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
