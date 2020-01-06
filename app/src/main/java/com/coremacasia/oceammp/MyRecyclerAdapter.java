package com.coremacasia.oceammp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coremacasia.oceammp.player.AudioModel;

import java.util.List;

class MyRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<AudioModel> tempAudioList;


    public MyRecyclerAdapter(Context context, List<AudioModel> tempAudioList) {
        this.context = context;

        this.tempAudioList = tempAudioList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View userView = inflater.inflate(R.layout.list_item, viewGroup, false);
        holder = new MyViewHolder(userView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.configHolder(myViewHolder, i, tempAudioList);

    }

    /*@Override
    public int getItemViewType(int position) {
        return 0;
    }*/

    @Override
    public int getItemCount() {
        return tempAudioList.size();
    }
}
