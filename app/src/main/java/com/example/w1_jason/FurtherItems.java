package com.example.w1_jason;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FurtherItems extends AppCompatActivity {

    public static String extramsg2 = "";
    String finaltext, S3;
    public RequestQueue mq3;
    Intent i3_fromactivity2;
    HorizontalScrollView hs;
    Button exclusionb, submitb;
    HorizontalScrollView hs2;
    ArrayList<String> exclusionarraylist = new ArrayList<String>();
    ArrayList<String> finalarraylist = new ArrayList<>();
    public ArrayAdapter exadap, finaladap;
    ListView exlistview, finallistview;


    //3ArrayList<>
    int i = 0, j = 0;   int k=0;   int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_further_items);

        FurtherItems ob = new FurtherItems();
        i3_fromactivity2 = getIntent();
        S3 = i3_fromactivity2.getStringExtra(extramsg2);

        hs = (HorizontalScrollView) findViewById(R.id.hsid);
        exclusionb = (Button) findViewById(R.id.exlusionbid);
        hs2 = (HorizontalScrollView) findViewById(R.id.hsid2);
        submitb = (Button) findViewById(R.id.selectfinalid);

        exlistview = (ListView) findViewById(R.id.exlistviewid);
        finallistview = (ListView) findViewById(R.id.finallistviewid);
        mq3 = Volley.newRequestQueue(getApplicationContext());


        exclusionb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ob.getexclusionlist();
                try {
                    String url = "https://my-json-server.typicode.com/ricky1550/pariksha/db";
                    exclusionb.setVisibility(View.GONE);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("exclusions");
                                JSONArray facilitiesArray = response.getJSONArray("facilities");
                                //for( int k=0; k<jsonArray.length(); k++){
                                // Toast.makeText(MainActivity.this, "valuee"+jsonArray.length(), Toast.LENGTH_LONG).show();
                                int i = 0;
                                while (i < jsonArray.length()) {
                                    int j = 0;
                                    JSONArray job_exitem = jsonArray.getJSONArray(i);
                                    //======================
                                    //JSONArray ja1 = job_exitem.getJSONArray(0);
                                    //Toast.makeText(FurtherItems.this, job_exitem.length(), Toast.LENGTH_SHORT).show();
                                    JSONObject obj1 = job_exitem.getJSONObject(0);
                                    JSONObject obj2 = job_exitem.getJSONObject(1);
                                    int fac_id1 = obj1.getInt("facility_id");
                                    int op_id1 = obj1.getInt("options_id");
                                    int fac_id2 = obj2.getInt("facility_id");
                                    int op_id2 = obj2.getInt("options_id");
                                    String key = fac_id1 + "-" + op_id1;
                                    String value = fac_id2 + "-" + op_id2;
                                    String key_id = key.split("-")[0];
                                    String option_id = key.split("-")[1];

                                    String value_id = value.split("-")[0];
                                    String value_option_id = value.split("-")[1];
//                                    Toast.makeText(FurtherItems.this, "the key.."+key_id+"--"+option_id, Toast.LENGTH_SHORT).show();
                                    String first = "";
                                    String second = "";
                                    for (int k = 0; k < facilitiesArray.length(); k++) {
                                        JSONObject obj = facilitiesArray.getJSONObject(k);
                                        String facility_id = obj.getString("facility_id");

//                                        Toast.makeText(FurtherItems.this, "facility id"+facility_id, Toast.LENGTH_SHORT).show();
                                        //=================================================
                                        if (key_id.equalsIgnoreCase(facility_id)) {
                                            JSONArray optionArray = obj.getJSONArray("options");
//                                            Toast.makeText(FurtherItems.this, "options len"+optionArray.length(), Toast.LENGTH_SHORT).show();

                                            for (int p = 0; p < optionArray.length(); p++) {
                                                JSONObject optionObj = optionArray.getJSONObject(p);
//                                                Toast.makeText(FurtherItems.this, "json obj"+optionObj, Toast.LENGTH_LONG).show();
                                                if (optionObj.getString("id").equals(option_id)) {
//                                                    Toast.makeText(FurtherItems.this, "the text.."+optionObj.getString("name"), Toast.LENGTH_LONG).show();
//                                                    exclusionarraylist.add(optionObj.getString("name"));
                                                    first = optionObj.getString("name");

                                                }
                                            }

                                        } else if (value_id.equalsIgnoreCase(facility_id)) {
                                            JSONArray optionArray = obj.getJSONArray("options");
                                            //Toast.makeText(FurtherItems.this, "options len"+optionArray.length(), Toast.LENGTH_SHORT).show();

                                            for (int p = 0; p < optionArray.length(); p++) {
                                                JSONObject optionObj = optionArray.getJSONObject(p);
                                                //Toast.makeText(FurtherItems.this, "json obj"+optionObj, Toast.LENGTH_LONG).show();
                                                if (optionObj.getString("id").equals(value_option_id)) {
                                                    // Toast.makeText(FurtherItems.this, "the text.."+optionObj.getString("name"), Toast.LENGTH_LONG).show();
//                                                    exclusionarraylist.add(optionObj.getString("name"));
                                                    second = optionObj.getString("name");
                                                }
                                            }

                                        }

                                    }
                                    exclusionarraylist.add(first + " - " + second);
                                    exadap = new ArrayAdapter(getApplicationContext(), R.layout.item_list, R.id.itemviewid, exclusionarraylist);
                                    exlistview.setAdapter(exadap);
                                    i++;
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                // jsonparse(i++,0);
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    mq3.add(request);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    //jsonparse(i++,0);
                    //  i++; j=0; jsonparse(i,j);
                }

            }

        });

        submitb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ob.finallist();
                try {
                    submitb.setVisibility(View.GONE);
                    String url = "https://my-json-server.typicode.com/ricky1550/pariksha/db";

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("facilities");
                                //for( int k=0; k<jsonArray.length(); k++){
                                // Toast.makeText(MainActivity.this, "valuee"+jsonArray.length(), Toast.LENGTH_LONG).show();
                                int i = 1;
                                while (i < jsonArray.length()) {
                                    j = 0;
                                    JSONObject job_exitem = jsonArray.getJSONObject(i);
                                    //======================
                                    JSONArray ja1 = job_exitem.getJSONArray("options");
                                    //Toast.makeText(FurtherItems.this, job_exitem.length(), Toast.LENGTH_SHORT).show();

                                    while (j < ja1.length()) {
                                        JSONObject exitem = ja1.getJSONObject(j);
                                        String firstname = exitem.getString("name");
                                        String icon = exitem.getString("icon");
                                        int id = exitem.getInt("id");
//
                                        //
                                        //  mtextview.append(firstname + "," + icon + "," + Integer.toString(id)+","+ "\n");
                                        //Toast.makeText(getApplicationContext(), "The value.." + firstname+icon+id, Toast.LENGTH_LONG).show();
                                        finalarraylist.add(firstname );
                                        finaladap = new ArrayAdapter(getApplicationContext(), R.layout.item_list, R.id.itemviewid, finalarraylist);
                                        finallistview.setAdapter(finaladap);

                                        j++;
                                    }
                                    i++;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    mq3.add(request);

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }

        });
        // ob.finaldisplay();

        finallistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                finaltext = S3 + " - " +(String) finalarraylist.get(i);
                //Toast.makeText(getApplicationContext(), "the word\n"+exclusionarraylist.get(1)+"\n"+finaltext, Toast.LENGTH_LONG).show();
                if(exclusionarraylist.size()!=0) {
                     try{
                             while(k < exclusionarraylist.size())
                             {
                                 if (exclusionarraylist.contains(finaltext)) {
                                   //Toast.makeText(FurtherItems.this, "Please avoid..", Toast.LENGTH_LONG).show();
                                 c++;
                                  break;
                               }
                            k++;
                           }
                       }catch (Exception exception){
                            exception.printStackTrace();
                        }


                   //else toast
                    if(c==0){Toast.makeText(FurtherItems.this, "The final list..\n" + finaltext, Toast.LENGTH_LONG).show();}
                    else{
                        Toast.makeText(FurtherItems.this, "Avoid...Choose again...", Toast.LENGTH_LONG).show();
                         c=0;}
                   // Toast.makeText(getApplicationContext(), "the word\n"+exclusionarraylist.get(k)+"\n"+finaltext, Toast.LENGTH_LONG).show()

                  }else
                        Toast.makeText(getApplicationContext(), "Please select the upper button first ", Toast.LENGTH_SHORT).show();

            }
                //Toast.makeText(getApplicationContext(), "Please avoid the exclusive pairings..", Toast.LENGTH_SHORT).show();



        });
}


    private void finaldisplay() {
    }
    private void getexclusionlist () {
    }
}





/*   exclusionarraylist.add(key);
//                                    exclusionarraylist.add(value);
                                    //Toast.makeText(FurtherItems.this, "the map"+exclusionMap, Toast.LENGTH_LONG).show();


//                                    while (j < job_exitem.length()) {
//                                        JSONObject exitem = job_exitem.getJSONObject(j);
//
//                                        int fac_id = exitem.getInt("facility_id");
//                                        int op_id = exitem.getInt("options_id");
////
//                                        //
//                                        //  mtextview.append(firstname + "," + icon + "," + Integer.toString(id)+","+ "\n");
//                                        //Toast.makeText(getApplicationContext(), "The value.." + fac_id + "," + op_id, Toast.LENGTH_LONG).show();
//                                        exclusionarraylist.add(fac_id + "-" + op_id);
//
//                                        exadap = new ArrayAdapter(getApplicationContext(), R.layout.item_list, R.id.itemviewid, exclusionarraylist);
//                                        exlistview.setAdapter(exadap);
//
//                                        j++;
//                                    }*/