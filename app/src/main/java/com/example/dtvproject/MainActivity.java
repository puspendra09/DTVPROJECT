package com.example.dtvproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LatestAdapter.OnItemClickListener {
    ViewFlipper v_flipper;
    ImageButton b_video,b_audio,b_calendar;
    Intent in;

    public static final String EXTRA_VIDEOID="videoId";
    private RecyclerView mRecyclerView,topRecyclerView;
    private LatestAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_bottom_view);

        b_video=findViewById(R.id.b_video);
        b_audio=findViewById(R.id.b_audio);
        b_calendar=findViewById(R.id.b_calendar);


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()){

                    case R.id.navigation_home:
                        break;
                    case R.id.nav_bottom_search:

                    in=new Intent(MainActivity.this,Seach_all.class);
                    startActivity(in);
                        break;
                    case R.id.nav_bottom_profile:
                        in=new Intent(MainActivity.this,Profile.class);
                        startActivity(in);
                        break;
                }
                return false;
            }
        });


        mRecyclerView=findViewById(R.id.latest_video);
        topRecyclerView=findViewById(R.id.Top_Rating_video);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        topRecyclerView.setHasFixedSize(true);
        topRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mExampleList=new ArrayList<>();

        mRequestQueue= Volley.newRequestQueue(this);
        latestvideo();
        toprating();


        v_flipper=findViewById(R.id.v_flipper);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        int images[]={R.drawable.poster,R.drawable.poster1,R.drawable.poster2};
        for(int image:images){
            flipperImages(image);
        }
    }

    private void toprating() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        String url="https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC0Z9ctv9aPXFNBbcB6RUZVg&maxResults=10&key=AIzaSyCPQKEg5rNSCb0WnuMISJVHTpjAhujhqT0";
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
                            mExampleAdapter =new LatestAdapter(MainActivity.this,mExampleList);
                            topRecyclerView.setAdapter(mExampleAdapter);

                            mExampleAdapter.setOnItemClickListener(MainActivity.this);

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

    private void latestvideo() {

        String url="https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC0Z9ctv9aPXFNBbcB6RUZVg&maxResults=10&key=AIzaSyCPQKEg5rNSCb0WnuMISJVHTpjAhujhqT0";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                            mExampleAdapter =new LatestAdapter(MainActivity.this,mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);

                            mExampleAdapter.setOnItemClickListener(MainActivity.this);

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

    private void flipperImages(int image) {

        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);

        b_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in=new Intent(MainActivity.this,VideoList.class);
                startActivity(in);
            }
        });

        b_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in=new Intent(MainActivity.this,Audio.class);
                startActivity(in);
            }
        });
        b_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in=new Intent(MainActivity.this,Calendar.class);
                startActivity(in);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            in=new Intent(MainActivity.this,Profile.class);
            startActivity(in);

        } else if (id == R.id.nav_settings) {

            in = new Intent(MainActivity.this,Settings.class);
            startActivity(in);

        } else if (id == R.id.nav_share) {

           Intent myIntent=new Intent(Intent.ACTION_SEND);
           myIntent.setType("text/plan");
           String shareBody="your body name";
           String shareSub = "Your Subjrct here";
           myIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
           myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
           startActivity(Intent.createChooser(myIntent,"Share using"));


        } else if (id == R.id.nav_signout) {
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
