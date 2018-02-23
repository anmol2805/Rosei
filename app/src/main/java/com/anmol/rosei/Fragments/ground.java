package com.anmol.rosei.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.anmol.rosei.Adapter.Mess1Adapter;
import com.anmol.rosei.Adapter.Mess2Adapter;
import com.anmol.rosei.Book_Activity;
import com.anmol.rosei.Model.mess1;
import com.anmol.rosei.R;
import com.anmol.rosei.Services.RequestService;
import com.anmol.rosei.Services.RequestServiceStatus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anmol on 10/20/2017.
 */

public class ground extends Fragment {
    Button load;
    ListView list;
    Mess1Adapter mess1Adapter;
    List<mess1>mess1s = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView amt1,total;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid());
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ground,container,false);
        load = (Button)v.findViewById(R.id.load);
        list = (ListView)v.findViewById(R.id.menu);
        amt1 = (TextView)v.findViewById(R.id.amt1);
        total = (TextView)v.findViewById(R.id.total);
        Intent intent = new Intent(getActivity(), RequestService.class);
        getActivity().startService(intent);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(getActivity(), RequestServiceStatus.class);
                getActivity().startService(intent1);
            }
        },1000);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("amount1").getValue(String.class)!=null){
                    amt1.setText(dataSnapshot.child("amount1").getValue(String.class));
                }
                if(dataSnapshot.child("total").getValue(String.class)!=null){
                    total.setText(dataSnapshot.child("total").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), RequestService.class);
                getActivity().startService(intent);
            }
        });
        db.child("messStatus").child("mess1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mess1s.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String day = data.child("date").getValue().toString();
                    String brkfast = data.child("brkfast").getValue().toString();
                    String lnch = data.child("lnch").getValue().toString();
                    String dinnr = data.child("dinnr").getValue().toString();
                    String bs = data.child("bs").getValue().toString();
                    String ls = data.child("ls").getValue().toString();
                    String ds = data.child("ds").getValue().toString();
                    mess1 mess1 = new mess1(day,brkfast,lnch,dinnr,bs,ls,ds);
                    mess1s.add(mess1);
                }
                if(getActivity()!=null){
                    mess1Adapter = new Mess1Adapter(getActivity(),R.layout.menu,mess1s);
                    mess1Adapter.notifyDataSetChanged();
                    if(!mess1Adapter.isEmpty()){
                        load.setVisibility(View.GONE);
                        list.setAdapter(mess1Adapter);
                    }
                    else {
                        load.setVisibility(View.VISIBLE);
                    }
                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
}
