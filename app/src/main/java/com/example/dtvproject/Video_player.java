package com.example.dtvproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Video_player extends YouTubeBaseActivity implements PlayingAdapter.OnItemClickListener, YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST=1;
    YouTubePlayerView youTubePlayerView;

public static final String EXTRA_VIDEOID="videoId";
private RecyclerView mRecyclerView;
private PlayingAdapter mExampleAdapter;
private ArrayList<ExampleItem> mExampleList;
private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(YoutubeConfig.API_KEY, this);

        mRecyclerView=findViewById(R.id.lowerVidioList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList=new ArrayList<>();

        mRequestQueue= Volley.newRequestQueue(this);
        sublist();

    }

    private void sublist() {

        String url="https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC0Z9ctv9aPXFNBbcB6RUZVg&maxResults=50&key=AIzaSyCPQKEg5rNSCb0WnuMISJVHTpjAhujhqT0";
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
                            mExampleAdapter =new PlayingAdapter(Video_player.this,mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);

                            mExampleAdapter.setOnItemClickListener(Video_player.this);

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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
     if(!b){
         Intent in=getIntent();
         youTubePlayer.cueVideo(in.getStringExtra("videoId"));
     }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason ) {

        if (errorReason.isUserRecoverableError()){
            errorReason.getErrorDialog(this,RECOVERY_DIALOG_REQUEST).show();
        }else{
            String errorMessage =String.format(getString(R.string.player_error),errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requesCode, int resultCode, Intent data){
        if(requesCode==RECOVERY_DIALOG_REQUEST){
            getYouTubePlayerProvider().initialize(YoutubeConfig.API_KEY,this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return youTubePlayerView;
    }
}
