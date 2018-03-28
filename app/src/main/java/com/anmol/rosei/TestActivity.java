package com.anmol.rosei;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button submit = findViewById(R.id.submit);
        final JSONObject jsonObject = new JSONObject();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonObject.put("un","b516008");
                    jsonObject.put("pw","anmol@2805");
                    jsonObject.put("check",2);
                    jsonObject.put("monbfmt","nonveg");
                    jsonObject.put("monlunmt","nonveg");
                    jsonObject.put("mondinmt","veg");
                    jsonObject.put("tuebfmt","nonveg");
                    jsonObject.put("tuelunmt","nonveg");
                    jsonObject.put("tuedinmt","veg");
                    jsonObject.put("wedbfmt","nonveg");
                    jsonObject.put("wedlunmt","nonveg");
                    jsonObject.put("weddinmt","veg");
                    jsonObject.put("thubfmt","nonveg");
                    jsonObject.put("thulunmt","nonveg");
                    jsonObject.put("thudinmt","nonveg");
                    jsonObject.put("fribfmt","nonveg");
                    jsonObject.put("frilunmt","nonveg");
                    jsonObject.put("fridinmt","veg");
                    jsonObject.put("satbfmt","veg");
                    jsonObject.put("satlunmt","veg");
                    jsonObject.put("satdinmt","veg");
                    jsonObject.put("sunbfmt","nonveg");
                    jsonObject.put("sunlunmt","veg");
                    jsonObject.put("sundinmt","veg");

                    jsonObject.put("monbf","m0022018-04-02-Mon");
                    jsonObject.put("monlun","m0022018-04-02-Mon");
                    jsonObject.put("mondin",1);
                    jsonObject.put("tuebf","m0022018-04-03-Tue");
                    jsonObject.put("tuelun","m0022018-04-03-Tue");
                    jsonObject.put("tuedin",1);
                    jsonObject.put("wedbf","m0022018-04-04-Wed");
                    jsonObject.put("wedlun","m0022018-04-04-Wed");
                    jsonObject.put("weddin",1);
                    jsonObject.put("thubf","m0022018-04-05-Thu");
                    jsonObject.put("thulun","m0022018-04-05-Thu");
                    jsonObject.put("thudin","m0022018-04-05-Thu");
                    jsonObject.put("fribf","m0022018-04-06-Fri");
                    jsonObject.put("frilun","m0022018-04-06-Fri");
                    jsonObject.put("fridin",1);
                    jsonObject.put("satbf",1);
                    jsonObject.put("satlun",1);
                    jsonObject.put("satdin",1);
                    jsonObject.put("sunbf","m0022018-04-08-Sun");
                    jsonObject.put("sunlun",1);
                    jsonObject.put("sundin","m0022018-04-08-Sun");

                    //jsonObject.put("satbfmt","veg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://14.139.198.171/api/rosei/booking", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TestActivity.this,"Error occured",Toast.LENGTH_SHORT).show();
                    }
                });
                Mysingleton.getInstance(TestActivity.this).addToRequestqueue(jsonObjectRequest);
                
            }
        });
    }
}
