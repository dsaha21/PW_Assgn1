package com.example.w1_jason;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {
    //private final static String extramsg="";
    public static String extramsg="";
    Intent i2_frommain,i3_tofurtheritems;
    TextView heading2;
    Button b;
    ArrayList arrayList2=new ArrayList();
    ArrayAdapter aa2;
    ListView listview2;     public RequestQueue mq2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        b=(Button)findViewById(R.id.getlistidbutton);
        i2_frommain=getIntent();
        String head2=i2_frommain.getStringExtra(extramsg);
        heading2=(TextView)findViewById(R.id.head2id);
        heading2.setText("Welcome to Activity 2\n Select any 1 property type");

        listview2=(ListView)findViewById(R.id.listid2);
        mq2= Volley.newRequestQueue(getApplicationContext());
         b.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 try {

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
                                         arrayList2.add(firstname );
                                         aa2=new ArrayAdapter(getApplicationContext(),R.layout.item_list,R.id.itemviewid,arrayList2);
                                         listview2.setAdapter(aa2);

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
                     mq2.add(request);

                 }catch (Exception ex){
                     ex.printStackTrace();
                     //jsonparse(i++,0);
                     //  i++; j=0; jsonparse(i,j);
                 }

             }
         });

        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Activity2.this, "You have selected..\n"+arrayList.get(i), Toast.LENGTH_SHORT).show();
                //i=new Intent(this,)
                i3_tofurtheritems=new Intent(Activity2.this,FurtherItems.class);
               // String s=arrayList2.get(i);
                i3_tofurtheritems.putExtra(FurtherItems.extramsg2,(String)arrayList2.get(i));
                startActivity(i3_tofurtheritems);
            }
        });


    }
}