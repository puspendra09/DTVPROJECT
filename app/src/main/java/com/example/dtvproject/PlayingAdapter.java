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

public class PlayingAdapter extends RecyclerView.Adapter<PlayingAdapter.PlayingViewHolder> {
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public  PlayingAdapter(Context context,ArrayList<ExampleItem> exampleList){
        mContext=context;
        mExampleList=exampleList;

    }

    @NonNull
    @Override
    public PlayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.videoplyainglist,parent,false);
        return  new PlayingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayingViewHolder holder, int position) {
        ExampleItem currentItem=mExampleList.get(position);
        String imageUrl=currentItem.getmImageUrl();
        String videoId=currentItem.getVideoId();
        String tilte=currentItem.getTitle();
        holder.title.setText(tilte);
        Picasso.get().load(imageUrl).fit().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class PlayingViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView title;

        public PlayingViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView=itemView.findViewById(R.id.thumbnail);
            title=itemView.findViewById(R.id.mytitle);

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
