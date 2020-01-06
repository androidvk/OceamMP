package com.coremacasia.oceammp.player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coremacasia.oceammp.MainActivity;
import com.coremacasia.oceammp.R;

import java.io.IOException;

public class Player extends AppCompatActivity {
    String songName, songPath;
    private MediaPlayer mediaPlayer;
    private TextView tSongName,tStartTime,tEndTime;
    private ImageView iPlayBtn;
    private static final String TAG = "Player";
    private boolean isPaused;
    private SeekBar seekBar;
    private int maxDuration;
    private Handler mHandler = new Handler();
    private int currentPosition;
    private int songNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        tSongName = findViewById(R.id.tSongName);
        iPlayBtn = findViewById(R.id.mediaBtn);
        seekBar = findViewById(R.id.seekbar);
        tStartTime=findViewById(R.id.tCurrentTime);
        tEndTime=findViewById(R.id.tTotalTime);

        //songName = getIntent().getStringExtra("songName");
        //songPath = getIntent().getStringExtra("songPath");
        songNumber=getIntent().getIntExtra("position",0);
        Log.d(TAG, "onCreate: " + songName);


        mediaPlayer = new MediaPlayer();
        initializePlayer();
        useSeekBar();


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

    private void initializePlayer() {


        songPath= MainActivity.getAllAudioFromDevice
                (getApplicationContext()).get(songNumber).getPath();
        songName=MainActivity.getAllAudioFromDevice(this).get(songNumber)
                .getName();
        songName=songName.substring(0,songName.length()-4);
        tSongName.setText(songName);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songPath);
            mediaPlayer.prepare();
            maxDuration = mediaPlayer.getDuration();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void useSeekBar() {
        seekBar.setMax(maxDuration/1000);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                     currentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(currentPosition);


                }
                mHandler.postDelayed(this, 1000);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 1000);
                }
                if(maxDuration/1000==currentPosition){
                    currentPosition=0;
                    seekBar.setProgress(0);


                    iPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_icon));
                }
                long min=currentPosition/60;
                long sec=currentPosition%60;
                tStartTime.setText(""+min+":"+sec);
                tEndTime.setText((maxDuration/1000)/60+":"+(maxDuration/1000)%60);


                Log.d(TAG, "onProgressChanged: "+seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mediaPlayer.stop();
        //mediaPlayer = null;
        //seekBar.setProgress(0);
    }

}
