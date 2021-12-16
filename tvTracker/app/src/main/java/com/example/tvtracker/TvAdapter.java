package com.example.tvtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvAdapterViewHolder> {
    private ArrayList<TvShowItem> nTvShowList;
    private TvAdapter.OnItemClickListener nListener;
    private TvAdapter.OnCheckedChangeListener checkListener;


    public interface OnItemClickListener {
        void onBoxClick(int position);
    }
    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position);
    }

    public void setOnItemClickListenerTv(TvAdapter.OnItemClickListener listener){
        nListener = listener;
    }

    public void setOnItemCheckedChangeListener(TvAdapter.OnCheckedChangeListener listener){
        checkListener = listener;
    }

        public static class TvAdapterViewHolder extends RecyclerView.ViewHolder{
            public TextView nTextView1;
            public TextView nTextView2;
            public CheckBox checkBox;

            /*public TvAdapterViewHolder(@NonNull View itemView, final TvAdapter.OnItemClickListener listener) {*/
            public TvAdapterViewHolder(@NonNull View itemView, final TvAdapter.OnCheckedChangeListener listener) {
                super(itemView);
                nTextView1 = itemView.findViewById(R.id.TV_textview);
                nTextView2 = itemView.findViewById(R.id.TV_textview2);
                checkBox = itemView.findViewById(R.id.tvShowDetails_check);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //set your object's last status
                        checkBox.setSelected(isChecked);

                        if (listener != null) {
                            int position = getAdapterPosition();

                            if (position != RecyclerView.NO_POSITION) {
                                listener.onCheckedChanged(position);
                                //onClickListener.onBoxClick(checkBox, position);
                                checkBox.setChecked(!checkBox.isChecked());


                            }
                        }


                    }
                });

                /*HashMap<Integer, Boolean> episodeListMap = FileIO.deserialize2(itemView.getContext());
                for (Map.Entry<Integer, Boolean> entry : episodeListMap.entrySet()) {
                    if(entry.getValue()) {
                       checkBox.setChecked(true);
                    }
                }*/

                /*checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            int position = getAdapterPosition();

                            if (position != RecyclerView.NO_POSITION) {
                                listener.onBoxClick(position);
                                    //onClickListener.onBoxClick(checkBox, position);
                                    checkBox.setChecked(!checkBox.isChecked());


                            }
                        }
                    }
                });*/
            }
        }

    public TvAdapter(ArrayList<TvShowItem> tvShowList) {
        nTvShowList = tvShowList;
    }

    @NonNull
    @Override
    public TvAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_show_info, parent, false);
        TvAdapter.TvAdapterViewHolder tve = new TvAdapter.TvAdapterViewHolder(v, checkListener);
        return tve;
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapterViewHolder holder, int position) {
        TvShowItem currentItem = nTvShowList.get(position); //we get the item at the position we pass

        holder.nTextView1.setText(currentItem.getText1()); //example viewholder
        holder.nTextView2.setText(currentItem.getText2()); //example viewholder
        //holder.checkBox = currentItem.getBox();
        //holder.checkBox.setChecked(currentItem.getBox().isChecked());
//        currentItem.check.setChecked(true);


        //in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        holder.checkBox.setChecked(currentItem.isSelected());

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //set your object's last status
//                currentItem.setSelected(isChecked);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return nTvShowList.size();
    }
}
