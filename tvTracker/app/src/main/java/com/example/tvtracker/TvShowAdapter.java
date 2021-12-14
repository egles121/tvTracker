package com.example.tvtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>{
    private ArrayList<TvShowItem> mTvShowList;
    private ExampleAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onCheckClick(int position);
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ExampleAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView checkBox;
        public ExampleViewHolder(@NonNull View itemView, final ExampleAdapter.OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.TV_textview);
            mTextView2 = itemView.findViewById(R.id.TV_textview2);
            //checkBox = itemView.findViewById(R.id.recycler_box);

            /*favoriteStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onStarClick(position);
                        }
                    }
                }
            });*/

        }
    }

    public TvShowAdapter(ArrayList<TvShowItem> tvShowList) {
        mTvShowList = tvShowList;
    }

    @NonNull
    @Override
    public ExampleAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_show_info, parent, false);
        ExampleAdapter.ExampleViewHolder evh = new ExampleAdapter.ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleAdapter.ExampleViewHolder holder, int position) {
        //position = shows what item is on the list
        TvShowItem currentItem = mTvShowList.get(position); //we get the item at the position we pass

        holder.mTextView1.setText(currentItem.getText1()); //example viewholder
        holder.mTextView2.setText(currentItem.getText2()); //example viewholder
        //holder.favoriteStar = currentItem.getBox();
    }

    @Override
    public int getItemCount() {
        return mTvShowList.size();
    }  //how many items in our list
}
