package com.example.test00001;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.OnPlayerEventListener;
import com.lzx.starrysky.provider.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;

import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity implements OnPlayerEventListener {

    private TextView tv1;
    private SeekBar seekBar;
    private Button bt;
    TimerTaskManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StarrySky.Companion.init(this.getApplication());
        manager = new TimerTaskManager();
        StarrySky.Companion.with().addPlayerEventListener(this);

        seekBar=findViewById(R.id.seekBar111);
        tv1=findViewById(R.id.textView1111);
        bt=findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongInfo info = new SongInfo();
                info.setSongId("424264505");
                info.setSongUrl("http://m8.music.126.net/20200610225648/ac9b0b9f6e1d86e815d4bcbf8b07e7ac/ymusic/c288/d85f/2a9a/b92673879918b3d583cad67e0c591a8d.mp3");
                StarrySky.Companion.with().playMusicByInfo(info);
            }
        });

        manager.setUpdateProgressTask(() -> {
            long progress = StarrySky.Companion.with().getPlayingPosition();
            long max=StarrySky.Companion.with().getDuration();
            seekBar.setMax((int)max);
            seekBar.setProgress((int) progress);
            seekBar.setSecondaryProgress((int) StarrySky.Companion.with().getBufferedPosition());
        });

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sDateFormat = new SimpleDateFormat("mm:ss");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv1.setText(sDateFormat.format(progress) + "/" + sDateFormat.format(seekBar.getMax()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                StarrySky.Companion.with().seekTo(seekBar.getProgress());
            }
        });





    }


    @Override
    public void onBuffering() {

        manager.stopToUpdateProgress();
    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onMusicSwitch(SongInfo songInfo) {

        manager.startToUpdateProgress();
        seekBar.setMax((int) songInfo.getDuration());
        Toast.makeText(MainActivity.this,songInfo.getDuration()+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayCompletion(SongInfo songInfo) {


    }

    @Override
    public void onPlayerPause() {

    }

    @Override
    public void onPlayerStart() {

        manager.startToUpdateProgress();
    }

    @Override
    public void onPlayerStop() {

        manager.stopToUpdateProgress();

    }
}
