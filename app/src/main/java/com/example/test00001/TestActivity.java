package com.example.test00001;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.OnPlayerEventListener;
import com.lzx.starrysky.control.RepeatMode;
import com.lzx.starrysky.playback.player.Playback;
import com.lzx.starrysky.provider.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements OnPlayerEventListener {
    Button play_mode, btn_speed,play,pause,resume,pre,next,close_server,reset;
    TextView curr_info;
    TextView now_max;
    SeekBar mSeekBar;
    TimerTaskManager manager;
    float speed = 1;
    List<String> strings = new ArrayList<>();
    final List<SongInfo> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        StarrySky.Companion.init(this.getApplication());
        manager = new TimerTaskManager();
        initV();
        initSong();
        StarrySky.Companion.with().addPlayerEventListener(this);
        //播放音乐
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_info.setText(getCurrInfo("play"));
                StarrySky.Companion.with().playMusic(list,0);
            }
        });
        //暂停
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_info.setText(getCurrInfo("resume"));
                StarrySky.Companion.with().pauseMusic();
            }
        });
        //继续播放
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_info.setText(getCurrInfo("resume"));
                StarrySky.Companion.with().restoreMusic();
            }
        });

        //上一首
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StarrySky.Companion.with().isSkipToPreviousEnabled())
                {
                    StarrySky.Companion.with().skipToPrevious();
                }
                else
                {
                    Toast.makeText(TestActivity.this, "没有上一首", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //下一首
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StarrySky.Companion.with().isSkipToNextEnabled())
                {
                    StarrySky.Companion.with().skipToNext();
                }
                else
                {
                    Toast.makeText(TestActivity.this, "没有下一首", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //播放模式
        play_mode.setText(getMode());
        play_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = StarrySky.Companion.with().getRepeatMode().getRepeatMode();
                if (mode == RepeatMode.REPEAT_MODE_NONE) {
                    StarrySky.Companion.with().setRepeatMode(RepeatMode.REPEAT_MODE_ONE,true);
                } else if (mode == RepeatMode.REPEAT_MODE_ONE) {
                    StarrySky.Companion.with().setRepeatMode(RepeatMode.REPEAT_MODE_SHUFFLE,true);
                } else if (mode == RepeatMode.REPEAT_MODE_SHUFFLE) {
                    StarrySky.Companion.with().setRepeatMode(RepeatMode.REPEAT_MODE_REVERSE,true);
                } else if (mode == RepeatMode.REPEAT_MODE_REVERSE) {
                    StarrySky.Companion.with().setRepeatMode(RepeatMode.REPEAT_MODE_NONE,true);
                }
                play_mode.setText(getMode());
                curr_info.setText(getCurrInfo("play_mode"));
            }
        });

        //初始化
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        curr_info.setText(getCurrInfo("curr_info"));
        //  进度条
        manager.setUpdateProgressTask(new Runnable() {
            @Override
            public void run() {
                long progress = StarrySky.Companion.with().getPlayingPosition();
                long max=StarrySky.Companion.with().getDuration();
                mSeekBar.setMax((int)max);
                mSeekBar.setProgress((int) progress);
                mSeekBar.setSecondaryProgress((int) StarrySky.Companion.with().getBufferedPosition());
            }
        });
        //未知
        /*mCurrProgress.post(() -> {
            viewWidth = mCurrProgress.getWidth();
            seekBarWidth = mSeekBar.getWidth();
        });*/

        //播放速度
        btn_speed.setOnClickListener(v -> {
            speed += 0.5;
            if (speed >= 3) {
                speed = 1;
            }
            StarrySky.Companion.with().onDerailleur(false,speed);
            btn_speed.setText("变速 当前" + speed + "倍");
        });

        //  拖动进度条
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sDateFormat = new SimpleDateFormat("mm:ss");
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if ( progress == 0) {
                    return;
                }
                long progress1=StarrySky.Companion.with().getPlayingPosition();
                long max=StarrySky.Companion.with().getDuration();
                now_max.setText(sDateFormat.format(progress1) + "/" + sDateFormat.format(max));
                //int translationX = (seekBarWidth - viewWidth) * progress / max;
                //mCurrProgress.setTranslationX(translationX);
                //mCurrProgress.setText(sDateFormat.format(progress) + "/" + sDateFormat.format(max));
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

    private void initSong() {
        strings.add("http://m7.music.126.net/20200615201311/ab94a453fdafb466c3385d855f653cf0/ymusic/c288/d85f/2a9a/b92673879918b3d583cad67e0c591a8d.mp3");
        strings.add("http://m8.music.126.net/20200615201400/bdaf78caa82c5abd6614e65523e0df52/ymusic/953f/ff44/31a5/59e8f69272032b5b29c801e596a357ce.mp3");
        strings.add("http://m7.music.126.net/20200615201431/038ec372f87faaf3f2a18f8ad147aeec/ymusic/ca28/0179/4b2e/0b0097eb58d6494d60afceabbafaba74.mp3");

        for (int i = 0; i < strings.size(); i++) {
            SongInfo songInfo = new SongInfo();
            songInfo.setSongId(String.valueOf(i));
            songInfo.setSongUrl(strings.get(i));
            if (i == 0) {
                songInfo.setSongName("你，好不好");
            } else if (i == 1) {
                songInfo.setSongName("陪我长大");
            } else if (i == 2) {
                songInfo.setSongName("虹之间");
            }
            list.add(songInfo);
        }
    }

    private void initV() {
        play_mode = findViewById(R.id.play_mode);
        curr_info = findViewById(R.id.curr_info);
        mSeekBar = findViewById(R.id.seekBar111);
        mSeekBar.setMax(100);
        btn_speed = findViewById(R.id.btn_speed);
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        resume=findViewById(R.id.resume);
        pre=findViewById(R.id.pre);
        next=findViewById(R.id.next);
        close_server=findViewById(R.id.close_server);
        reset=findViewById(R.id.reset);
        now_max=findViewById(R.id.now_max);
    }

    private String getCurrInfo(String from) {

        StringBuilder builder = new StringBuilder();
        List<SongInfo> songInfos = StarrySky.Companion.with().getPlayList();
        if (songInfos == null) {
            return "";
        }
        for (int i = 0; i < songInfos.size(); i++) {
            builder
                    .append(String.valueOf(i))
                    .append(" ")
                    .append(songInfos.get(i).getSongName())
                    .append("\n");
        }
        SongInfo currInfo = StarrySky.Companion.with().getNowPlayingSongInfo();
        String name = currInfo == null ? "没有" : currInfo.getSongName();
        return " 当前播放：" + name + "\n 播放状态：" + getStatus() + "\n 下标：" + StarrySky.Companion.with().getNowPlayingSongId() + " \n\n" +
                "当前播放列表：\n\n" + builder.toString();
    }

    private String getStatus() {
        int status = StarrySky.Companion.with().getState();
        if (status == Playback.STATE_IDLE) {
            return "空闲";
        } else if (status == Playback.STATE_PLAYING) {
            return "播放中";
        } else if (status == Playback.STATE_PAUSED) {
            return "暂停";
        } else if (status == Playback.STATE_ERROR) {
            return "错误";
        } else if (status == Playback.STATE_STOPPED) {
            return "停止";
        } else if (status == Playback.STATE_BUFFERING) {
            return "加载中";
        }
        return "其他";
    }

    private String getMode() {
        int mode = StarrySky.Companion.with().getRepeatMode().getRepeatMode();

        if (mode == RepeatMode.REPEAT_MODE_NONE) {
            return "顺序播放";
        } else if (mode == RepeatMode.REPEAT_MODE_ONE) {
            return "单曲循环";
        } else if (mode == RepeatMode.REPEAT_MODE_SHUFFLE) {
            return "随机播放";
        } else if (mode == RepeatMode.REPEAT_MODE_REVERSE) {
            return "倒序循环";
        }
        return "不知道什么模式";
    }
    @Override
    public void onBuffering() {
        curr_info.setText(getCurrInfo("onBuffering"));
        curr_info.setText(getCurrInfo("onError"));
        manager.stopToUpdateProgress();

    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        curr_info.setText(getCurrInfo("onMusicSwitch"));
        manager.startToUpdateProgress();
        mSeekBar.setMax((int) songInfo.getDuration());
    }

    @Override
    public void onPlayCompletion(SongInfo songInfo) {

        curr_info.setText(getCurrInfo("onPlayCompletion"));
        manager.stopToUpdateProgress();
        mSeekBar.setProgress(0);
    }

    @Override
    public void onPlayerPause() {

        curr_info.setText(getCurrInfo("onPlayerPause"));
        manager.stopToUpdateProgress();
    }

    @Override
    public void onPlayerStart() {

        curr_info.setText(getCurrInfo("onPlayerStart"));
        manager.startToUpdateProgress();
    }

    @Override
    public void onPlayerStop() {

        curr_info.setText(getCurrInfo("onPlayerStop"));
        manager.stopToUpdateProgress();
    }
}
