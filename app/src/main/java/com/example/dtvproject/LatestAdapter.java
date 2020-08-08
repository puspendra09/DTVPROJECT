package com.example.dtvproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.LatestViewHolder> {
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public  LatestAdapter(Context context,ArrayList<ExampleItem> exampleList){
        mContext=context;
        mExampleList=exampleList;

    }

    @NonNull
    @Override
    public LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.latestvideo,parent,false);
        return  new LatestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestViewHolder holder, int position) {
        ExampleItem currentItem=mExampleList.get(position);
        String imageUrl=currentItem.getmImageUrl();
        String videoId=currentItem.getVideoId();
        Picasso.get().load(imageUrl).fit().into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class LatestViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;

        public LatestViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView=itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
