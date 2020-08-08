package com.example.dtvproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Seach_all extends AppCompatActivity  {

    Intent in;
    EditText et1;
    public static final String EXTRA_VIDEOID="videoId";
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC0Z9ctv9aPXFNBbcB6RUZVg&maxResults=50&pageToken=&key=AIzaSyCPQKEg5rNSCb0WnuMISJVHTpjAhujhqT0";
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_all);

        mRecyclerView=findViewById(R.id.search_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList=new ArrayList<>();

        mRequestQueue= Volley.newRequestQueue(this);
        paresesearchSON();

    }

    private void paresesearchSON() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        String url="https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC0Z9ctv9aPXFNBbcB6RUZVg&maxResults=50&pageToken=&key=AIzaSyCPQKEg5rNSCb0WnuMISJVHTpjAhujhqT0";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray=response.getJSONArray("items");

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject hit=jsonArray.getJSONObject(i);
                                JSONObject jsonVideoID=hit.getJSONObject("id");
                                JSONObject jsonObjectsnippet=hit.getJSONObject("snippet");
                                JSONObject jsonObjectDefault=jsonObjectsnippet.getJSONObject("thumbnails").getJSONObject("medium");


                                String video_id=jsonVideoID.getString("videoId");
                                ExampleItem vd=new ExampleItem();
                                vd.setVideoId(video_id);
                                vd.setTitle(jsonObjectsnippet.getString("title"));
                                vd.setmImageUrl(jsonObjectDefault.getString("url"));
                                mExampleList.add(vd);

                            }
                            mExampleAdapter =new ExampleAdapter(Seach_all.this,mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);


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

        mRequestQueue.add(request);
    }

}