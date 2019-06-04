package com.pato.audioplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlay, btnBack, btnForward;
    private SeekBar seekbar;
    private MediaPlayer mediaPlayer ;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intializie the ui.
        btnPlay = findViewById(R.id.btnPlay);
        btnForward = findViewById(R.id.btnForward);
        btnBack = findViewById(R.id.btnBack);

        //set onclick listeners.
        btnPlay.setOnClickListener(this);
        btnForward.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        seekbar = findViewById(R.id.seekbar);
        handler = new Handler();

        //create the mediaplayer.
        mediaPlayer = MediaPlayer.create(this,R.raw.musicfile);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekbar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                changeSeekBar();
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean bbool) {
                if(bbool){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //method change seekBar.
    private void changeSeekBar(){

        seekbar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()){
            runnable = new Runnable(){
                @Override
                public void run(){
                    changeSeekBar();
                }
            };

            handler.postDelayed(runnable,1000);
        }
    }

    @Override
    public void onClick(View v) {
       switch(v.getId()){

           case R.id.btnPlay:
               if(mediaPlayer.isPlaying()){
                   mediaPlayer.pause();
                   btnPlay.setText(">");
               }else{
                   mediaPlayer.start();
                   btnPlay.setText("||");
                   changeSeekBar();
               }
               break;
           case R.id.btnForward:
               mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
               break;
           case R.id.btnBack:
               mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
               break;
       }
    }
}
