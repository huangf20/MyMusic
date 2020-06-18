package com.example.test00001.MVP;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.test00001.PlayMusicView;
import com.example.test00001.R;
import com.example.test00001.TestActivity;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.OnPlayerEventListener;
import com.lzx.starrysky.provider.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;

import java.text.SimpleDateFormat;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MusicPlayActivity extends AppCompatActivity implements IMusicPlayView, OnPlayerEventListener {

    private ImageView im_bg;
    private TextView songName,songprogress,songMax;
    private PlayMusicView mPlayMusicView;
    private SeekBar mseekBar;
    private ImageView play,next,pre;
    private boolean isPlay=false,firstPlay=true;
    private MusicPlayPresenter musicPlayPresenter;
    TimerTaskManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music);
        //隐藏标题栏
        ImmersionBar.with(this).statusBarDarkFont(true,0.1f).hideBar(BarHide.FLAG_HIDE_BAR).fullScreen(true).init();
        initView();
        //初始化音乐库，监听器
        StarrySky.Companion.init(this.getApplication());
        StarrySky.Companion.with().addPlayerEventListener(this);
        musicPlayPresenter=new MusicPlayPresenter(this);
        manager = new TimerTaskManager();

        //进度条
        setpro();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayPresenter.next();
            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                musicPlayPresenter.pre();
            }
        });


    }

    private void setpro() {
        manager.setUpdateProgressTask(new Runnable() {
            @Override
            public void run() {
                long progress = StarrySky.Companion.with().getPlayingPosition();
                long max=StarrySky.Companion.with().getDuration();
                mseekBar.setMax((int)max);
                mseekBar.setProgress((int) progress);
                mseekBar.setSecondaryProgress((int) StarrySky.Companion.with().getBufferedPosition());
            }
        });
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sDateFormat = new SimpleDateFormat("mm:ss");
        mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if ( progress == 0) {
                    return;
                }
                long progress1=StarrySky.Companion.with().getPlayingPosition();
                long max=StarrySky.Companion.with().getDuration();
                songprogress.setText(sDateFormat.format(progress1));
                songMax.setText(sDateFormat.format(max));
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
    public void play_pause() {

    }

    @Override
    public void next_music() {

        if(StarrySky.Companion.with().isSkipToNextEnabled())
        {
            StarrySky.Companion.with().skipToNext();

        }
        else
        {
            Toast.makeText(MusicPlayActivity.this, "没有下一首", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void pre_music() {

        if(StarrySky.Companion.with().isSkipToPreviousEnabled())
        {
            StarrySky.Companion.with().skipToPrevious();

        }
        else
        {
            Toast.makeText(MusicPlayActivity.this, "没有上一首", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void start(List<SongInfo> songInfoList) {
        StarrySky.Companion.with().playMusic(songInfoList,0);

    }

    private void setMusicimage(String s)
    {
        //背景虚化
        Glide.with(this)
                .load(s)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,10)))
                .into(im_bg);
        //光碟旋转
        mPlayMusicView.setMusicIcon(s);
        mPlayMusicView.playmusic();
    }
    private void initView()
    {

        im_bg=findViewById(R.id.im_bg);//glide-transformation

        //光碟旋转
        mPlayMusicView=findViewById(R.id.playMusicView);
        setMusicimage("http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg");

        mseekBar=findViewById(R.id.seekBar);
        mseekBar.getThumb().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        songName=findViewById(R.id.Songname);
        next=findViewById(R.id.next_bt);
        pre=findViewById(R.id.pre_bt);
        songprogress=findViewById(R.id.songProgress);
        songMax=findViewById(R.id.songMax);
        //
        play=findViewById(R.id.play_pause);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstPlay)
                {
                    mPlayMusicView.playmusic();
                    play.setImageResource(R.drawable.play);
                    isPlay=true;
                    musicPlayPresenter.getSong();
                    firstPlay=false;

                }
                else
                {
                    if(isPlay)
                    {
                        mPlayMusicView.stopmusic();
                        play.setImageResource(R.drawable.pause);
                        isPlay=false;
                        StarrySky.Companion.with().pauseMusic();
                    }
                    else
                    {
                        mPlayMusicView.playmusic();
                        play.setImageResource(R.drawable.play);
                        isPlay=true;
                        StarrySky.Companion.with().restoreMusic();

                    }
                }

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

        songName.setText(songInfo.getSongName());
        setMusicimage(songInfo.getSongCover());
        mseekBar.setMax((int) songInfo.getDuration());

    }

    @Override
    public void onPlayCompletion(SongInfo songInfo) {

        manager.stopToUpdateProgress();
        mseekBar.setProgress(0);
    }

    @Override
    public void onPlayerPause() {

        manager.stopToUpdateProgress();

    }

    @Override
    public void onPlayerStart() {

        songName.setText(StarrySky.Companion.with().getNowPlayingSongInfo().getSongName());
        setMusicimage(StarrySky.Companion.with().getNowPlayingSongInfo().getSongCover());
        manager.startToUpdateProgress();
    }

    @Override
    public void onPlayerStop() {

        manager.stopToUpdateProgress();
    }
}
