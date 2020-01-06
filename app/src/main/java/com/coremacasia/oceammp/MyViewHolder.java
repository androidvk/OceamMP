package com.coremacasia.oceammp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coremacasia.oceammp.player.AudioModel;
import com.coremacasia.oceammp.player.PlaySong;
import com.coremacasia.oceammp.player.Player;

import java.util.List;

class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private MyListData[] dataList;
    private ImageView imageView;
    private MyViewHolder myViewHolder;
    private View view;
    private int i;
    private List<AudioModel> tempAudioList;
    private MediaPlayer mpintro =new MediaPlayer();

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textView=itemView.findViewById(R.id.listText);
        imageView=itemView.findViewById(R.id.listImg);
        view=itemView.findViewById(R.id.line1);
    }

    public  void configHolder(MyViewHolder myViewHolder, final int i, final List<AudioModel> tempAudioList) {
        this.myViewHolder = myViewHolder;
        this.i = i;
        this.tempAudioList = tempAudioList;

        textView.setText(tempAudioList.get(i).getName()
        +"\n"+tempAudioList.get(i).getPath());



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(itemView.getContext(), PlaySong.class);
                intent.putExtra("songName",tempAudioList.get(i).getName());
                intent.putExtra("songPath",tempAudioList.get(i).getPath());
                intent.putExtra("position",i);
                itemView.getContext().startActivity(intent);
            }
        });


    }
}
 //Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/Music/intro.mp3")