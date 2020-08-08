package com.example.dtvproject;

public class ExampleItem {
    private  String mImageUrl;
    private  String videoId;
    private  String title;

    public ExampleItem( String videoId, String title, String url){
        mImageUrl=url;
        videoId=videoId;
        title=title;

    }

    public ExampleItem() {

    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
