package com.example.tvtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvAdapterViewHolder> {
    private ArrayList<TvShowItem> nTvShowList;
    private TvAdapter.OnItemClickListener nListener;

    public interface OnItemClickListener {
        void onBoxClick(int position);
    }

    public void setOnItemClickListenerTv(TvAdapter.OnItemClickListener listener){
        nListener = listener;
    }

        public static class TvAdapterViewHolder extends RecyclerView.ViewHolder{
            public TextView nTextView1;
            public TextView nTextView2;
            public CheckBox checkBox;

            public TvAdapterViewHolder(@NonNull View itemView, final TvAdapter.OnItemClickListener listener) {
                super(itemView);
                nTextView1 = itemView.findViewById(R.id.TV_textview);
                nTextView2 = itemView.findViewById(R.id.TV_textview2);
                checkBox = itemView.findViewById(R.id.tvShowDetails_check);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            int position = getAdapterPosition();

                            if (position != RecyclerView.NO_POSITION) {
                                listener.onBoxClick(position);

                                    checkBox.setChecked(!checkBox.isChecked());

                            }
                        }
                    }
                });
            }
        }

    public TvAdapter(ArrayList<TvShowItem> tvShowList) {
        nTvShowList = tvShowList;
    }

    @NonNull
    @Override
    public TvAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_show_info, parent, false);
        TvAdapter.TvAdapterViewHolder tve = new TvAdapter.TvAdapterViewHolder(v, nListener);
        return tve;
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapterViewHolder holder, int position) {
        TvShowItem currentItem = nTvShowList.get(position); //we get the item at the position we pass

        holder.nTextView1.setText(currentItem.getText1()); //example viewholder
        holder.nTextView2.setText(currentItem.getText2()); //example viewholder
        holder.checkBox = currentItem.getBox();
    }

    @Override
    public int getItemCount() {
        return nTvShowList.size();
    }
}
