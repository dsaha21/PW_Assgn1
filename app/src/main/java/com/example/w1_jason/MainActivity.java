package com.example.w1_jason;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   public TextView head;
   public RequestQueue mq; Button b,next_b;
   ListView listView;
    ArrayList<String> arrayList=new ArrayList<String>();
    ArrayAdapter aa;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //mtextview=(TextView) findViewById(R.id.textid);
        Spinner spinner;
        b=(Button) findViewById(R.id.bid);
        head=(TextView)findViewById(R.id.textView);
        listView=(ListView)findViewById(R.id.listid);
        next_b=(Button)findViewById(R.id.nextbuttonid);

        mq= Volley.newRequestQueue(getApplicationContext());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                          b.setVisibility(View.GONE);
                        String url="https://my-json-server.typicode.com/ricky1550/pariksha/db";

                        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try
                                {

                                    JSONArray jsonArray=response.getJSONArray("facilities");
                                    //for( int k=0; k<jsonArray.length(); k++){
                                   // Toast.makeText(MainActivity.this, "valuee"+jsonArray.length(), Toast.LENGTH_LONG).show();
                                    int i = 0;
                                    int j = 0;
                                    while(i<jsonArray.length()) {
                                         //if(i==1)
                                         //    break;

                                        JSONObject jolu = jsonArray.getJSONObject(i);
                                        //======================

                                            j=0;
                                        JSONArray ja1 = jolu.getJSONArray("options");

                                        //  for(int h=0; h<ja1.length(); h++) {
                                        while (j < ja1.length()) {
                                            JSONObject facilityitem = ja1.getJSONObject(j);

                                            String firstname = facilityitem.getString("name");
                                            String icon = facilityitem.getString("icon");
                                            int id = facilityitem.getInt("id");
//
                                          //
                                         //  mtextview.append(firstname + "," + icon + "," + Integer.toString(id)+","+ "\n");
                                            arrayList.add(firstname + "," + icon + "," + Integer.toString(id));
                                            aa=new ArrayAdapter(getApplicationContext(),R.layout.item_list,R.id.itemviewid,arrayList);
                                            listView.setAdapter(aa);

                                            j++;
                                        }
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
                        mq.add(request);

                    }catch (Exception ex){
                        ex.printStackTrace();
                      //jsonparse(i++,0);
                      //  i++; j=0; jsonparse(i,j);
                    }

            }

        });

           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   Toast.makeText(MainActivity.this, "You have selected..\n"+arrayList.get(i), Toast.LENGTH_SHORT).show();
                   //i=new Intent(this,)
               }
           });

           next_b.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   i=new Intent(MainActivity.this,Activity2.class);
                   i.putExtra(Activity2.extramsg,"welcome to 2");
                   startActivity(i);
               }
           });
       //listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       //    @Override
       //    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           //}
      // });


    //private  ex=new Beerexpert();
       /* spinner=(Spinner)findViewById(R.id.spinid);
        String item=String.valueOf(spinner.getSelectedItem());


        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, View view, int i, long l) {
                String selecteditem=spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "The item.."+selecteditem, Toast.LENGTH_LONG).show();
                Log.i("The answer","The item"+selecteditem);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {

            }
        });*/





    }
}