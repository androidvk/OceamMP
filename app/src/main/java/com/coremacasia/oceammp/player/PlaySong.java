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

import com.coremacasia.oceammp.MainActivity;
import com.coremacasia.oceammp.R;

import java.io.IOException;
import java.util.List;

public class PlaySong extends AppCompatActivity {
    String songName, songPath;
    private MediaPlayer mediaPlayer;
    private TextView tSongName, tStartTime, tEndTime;
    private ImageView iPlayBtn, iNextBtn, iPreviousBtn;
    private boolean isPaused;
    private SeekBar seekBar;
    private int duration;
    private Handler mHandler = new Handler();
    private int currentDuration;
    private int songPosition;
    private static final String TAG = "PlaySong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tSongName = findViewById(R.id.tSongName);
        iPlayBtn = findViewById(R.id.mediaBtn);
        seekBar = findViewById(R.id.seekbar);
        tStartTime = findViewById(R.id.tCurrentTime);
        tEndTime = findViewById(R.id.tTotalTime);
        iNextBtn = findViewById(R.id.iBtnNext);
        iPreviousBtn = findViewById(R.id.iBtnPrevious);

        songPosition = getIntent().getIntExtra("position", 0);
        Log.d(TAG, "onCreate: Position: " + songPosition);

        initializeMediaPlayer(songPosition);
        useSeekBar();

        Log.d(TAG, "onClick: Total Size " + MainActivity.getAllAudioFromDevice(getApplicationContext()).size());
        Log.d(TAG, "onClick: Current Position" + songPosition);
        final int listSize = MainActivity.getAllAudioFromDevice(this).size();
        final int pos = songPosition;

        iNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos<listSize){
                    mediaPlayer.stop();
                    initializeMediaPlayer(songPosition + 1);
                    songPosition = songPosition + 1;
                }else {
                    mediaPlayer.stop();
                    initializeMediaPlayer(0);
                    songPosition = 0;
                }

            }
        });

        iPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos>0){
                    mediaPlayer.stop();
                    initializeMediaPlayer(songPosition -1);
                    songPosition = songPosition - 1;
                }else {
                    mediaPlayer.stop();
                    initializeMediaPlayer(0);
                    songPosition = 0;
                }
            }
        });


    }

    private void initializeMediaPlayer(int POSITION) {
        mediaPlayer = new MediaPlayer();

        try {

            List<AudioModel> songList = MainActivity.getAllAudioFromDevice(this);
            songName = songList.get(POSITION).getName();
            songPath = songList.get(POSITION).getPath();
            songName = songName.substring(0, songName.length() - 4);
            Log.d(TAG, "onCreate: SongName: " + songName);

            tSongName.setText(songName);
            getSupportActionBar().setTitle(songName);

            mediaPlayer.setDataSource(songPath);
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();

            playSong();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playSong() {

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        //mediaPlayer.start();
        iPlayBtn.setImageDrawable(getResources()
                .getDrawable(R.drawable.pause_icon));


        iPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                        }
                    });
                    iPlayBtn.setImageDrawable(getResources()
                            .getDrawable(R.drawable.pause_icon));
                } else {
                    mediaPlayer.pause();
                    iPlayBtn.setImageDrawable(getResources()
                            .getDrawable(R.drawable.play_icon));
                }

            }
        });
    }

    private void useSeekBar() {
        seekBar.setMax(duration / 1000);

        //only for updating seekBar position
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    currentDuration = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(currentDuration);
                    mHandler.postDelayed(this, 100);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 1000);
                }

                if (duration / 1000 == currentDuration) {
                    currentDuration = 0;
                    seekBar.setProgress(0);
                    iPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_icon));
                }

                // current duration
                long min = currentDuration / 60;
                long sec = currentDuration % 60;
                if (sec < 10) {
                    tStartTime.setText("" + min + ":0" + sec);
                } else tStartTime.setText("" + min + ":" + sec);

                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;
                if (Ssec < 10) {
                    tEndTime.setText("" + Mmin + ":0" + Ssec);
                } else tEndTime.setText("" + Mmin + ":" + Ssec);

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
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
