package com.anmol.rosei;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.anmol.rosei.Adapter.Mess1Adapter;
import com.anmol.rosei.Adapter.Mess2Adapter;
import com.anmol.rosei.Model.mess1;
import com.anmol.rosei.Model.mess2;
import com.anmol.rosei.Services.RequestService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Book_Activity extends AppCompatActivity {
    Button m1,m2;
    ListView list;
    List<mess1> mess1s = new ArrayList<>();
    List<mess2> mess2s = new ArrayList<>();
    Mess1Adapter mess1Adapter;
    Mess2Adapter mess2Adapter;
    Button load;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("students").child(auth.getCurrentUser().getUid());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        m1 = (Button)findViewById(R.id.m1);
        m2 = (Button)findViewById(R.id.m2);
        load = (Button)findViewById(R.id.load);
        list = (ListView)findViewById(R.id.menu);
        Intent intent = new Intent(Book_Activity.this, RequestService.class);
        startService(intent);
        db.child("mess1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mess1s.clear();
                mess2s.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    String day = data.child("day").getValue().toString();
                    String brkfast = data.child("brkfast").getValue().toString();
                    String lnch = data.child("lnch").getValue().toString();
                    String dinnr = data.child("dinnr").getValue().toString();
                    mess1 mess1 = new mess1(day,brkfast,lnch,dinnr);
                    mess1s.add(mess1);
                }
                mess1Adapter = new Mess1Adapter(Book_Activity.this,R.layout.menu,mess1s);
                mess1Adapter.notifyDataSetChanged();
                if(!mess1Adapter.isEmpty()){
                    load.setVisibility(View.GONE);
                    list.setAdapter(mess1Adapter);
                }
                else{
                    load.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(Book_Activity.this, RequestService.class);
                startService(intent);
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mess1s.clear();
                mess2s.clear();
                db.child("mess1").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mess1s.clear();
                        mess2s.clear();
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            String day = data.child("day").getValue().toString();
                            String brkfast = data.child("brkfast").getValue().toString();
                            String lnch = data.child("lnch").getValue().toString();
                            String dinnr = data.child("dinnr").getValue().toString();
                            mess1 mess1 = new mess1(day,brkfast,lnch,dinnr);
                            mess1s.add(mess1);
                        }
                        mess1Adapter = new Mess1Adapter(Book_Activity.this,R.layout.menu,mess1s);
                        mess1Adapter.notifyDataSetChanged();
                        if(!mess1Adapter.isEmpty()){
                            load.setVisibility(View.GONE);
                            list.setAdapter(mess1Adapter);
                        }
                        else {
                            load.setVisibility(View.VISIBLE);
                        }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mess1s.clear();
                mess2s.clear();
                db.child("mess2").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mess1s.clear();
                        mess2s.clear();
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            String day = data.child("day").getValue().toString();
                            String brkfast = data.child("brkfast").getValue().toString();
                            String lnch = data.child("lnch").getValue().toString();
                            String dinnr = data.child("dinnr").getValue().toString();
                            mess2 mess2 = new mess2(day,brkfast,lnch,dinnr);
                            mess2s.add(mess2);
                        }
                        mess2Adapter = new Mess2Adapter(Book_Activity.this,R.layout.menu,mess2s);
                        mess2Adapter.notifyDataSetChanged();

                        if(!mess2Adapter.isEmpty()){
                            load.setVisibility(View.GONE);
                            list.setAdapter(mess2Adapter);
                        }
                        else{
                            load.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
