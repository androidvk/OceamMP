package com.coremacasia.oceammp.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coremacasia.oceammp.R;

import java.io.IOException;

public class Player extends AppCompatActivity {
    String songName, songPath;
    private MediaPlayer mediaPlayer;
    private TextView tSongName;
    private ImageView iPlayBtn;
    private static final String TAG = "Player";
    private boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        tSongName = findViewById(R.id.tSongName);
        iPlayBtn = findViewById(R.id.mediaBtn);

        songName = getIntent().getStringExtra("songName");
        songPath = getIntent().getStringExtra("songPath");
        Log.d(TAG, "onCreate: " + songName);
        tSongName.setText(songName);

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songPath);
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }


        iPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    Log.d(TAG, "onPrepared: " + "Playing");
                    Toast.makeText(Player.this, "Playing", Toast.LENGTH_SHORT).show();
                    iPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_icon));
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                    Log.d(TAG, "onPrepared: " + "Paused");
                    iPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_icon));

                }
            }
        });


    }
}
