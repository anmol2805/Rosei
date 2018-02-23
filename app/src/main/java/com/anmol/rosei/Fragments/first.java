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

import com.anmol.rosei.Adapter.Mess2Adapter;
import com.anmol.rosei.Book_Activity;
import com.anmol.rosei.Model.mess2;
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

public class first extends Fragment {
    Button load;
    ListView list;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid());
    List<mess2>mess2s = new ArrayList<>();
    Mess2Adapter mess2Adapter;
    TextView amt2,total;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first,container,false);
        load = (Button)v.findViewById(R.id.load);
        list = (ListView)v.findViewById(R.id.menu);
        amt2 = (TextView)v.findViewById(R.id.amt2);
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
                if(dataSnapshot.child("amount2").getValue(String.class)!=null){
                    amt2.setText(dataSnapshot.child("amount2").getValue(String.class));
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
        db.child("messStatus").child("mess2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mess2s.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String day = data.child("date").getValue().toString();
                    String brkfast = data.child("brkfast").getValue().toString();
                    String lnch = data.child("lnch").getValue().toString();
                    String dinnr = data.child("dinnr").getValue().toString();
                    String bs = data.child("bs").getValue().toString();
                    String ls = data.child("ls").getValue().toString();
                    String ds = data.child("ds").getValue().toString();
                    mess2 mess2 = new mess2(day,brkfast,lnch,dinnr,bs,ls,ds);
                    mess2s.add(mess2);
                }
                if(getActivity()!=null){
                    mess2Adapter = new Mess2Adapter(getActivity(),R.layout.menu,mess2s);
                    mess2Adapter.notifyDataSetChanged();

                    if(!mess2Adapter.isEmpty()){
                        load.setVisibility(View.GONE);
                        list.setAdapter(mess2Adapter);
                    }
                    else{
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
