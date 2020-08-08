package com.example.dtvproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoList extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {

    public static final String EXTRA_VIDEOID="videoId";
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mRecyclerView=findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList=new ArrayList<>();

        mRequestQueue= Volley.newRequestQueue(this);
        pareseSON();
    }

    private void pareseSON(){

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
                            mExampleAdapter =new ExampleAdapter(VideoList.this,mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);

                            mExampleAdapter.setOnItemClickListener(VideoList.this);

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

    @Override
    public void onItemClick(int position) {
        Intent in=new Intent( this,Video_player.class);
        ExampleItem clickedItem=mExampleList.get(position);
        in.putExtra(EXTRA_VIDEOID,clickedItem.getVideoId());
        startActivity(in);
    }
}
