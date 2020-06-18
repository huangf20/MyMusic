package com.example.test00001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.lang.reflect.Field;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MusicActivity extends Activity {
    private ImageView im_bg;
    private PlayMusicView mPlayMusicView;
    private SeekBar mseekBar;
    private ImageView play;
    private boolean isPlay=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }*/
        ImmersionBar.with(this).statusBarDarkFont(true,0.1f).hideBar(BarHide.FLAG_HIDE_BAR).init();
        initView();

    }

    private void initView()
    {
        //背景虚化
        im_bg=findViewById(R.id.im_bg);//glide-transformation
        Glide.with(this)
                .load("http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg")
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,10)))
                .into(im_bg);
        //光碟旋转
        mPlayMusicView=findViewById(R.id.playMusicView);
        mPlayMusicView.setMusicIcon("http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg");
        mPlayMusicView.playmusic();
        mseekBar=findViewById(R.id.seekBar);
        mseekBar.getThumb().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        //
        play=findViewById(R.id.play_pause);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPlay)
                {
                    mPlayMusicView.stopmusic();
                    play.setImageResource(R.drawable.pause);
                    isPlay=false;
                }
                else
                {
                    mPlayMusicView.playmusic();
                    play.setImageResource(R.drawable.play);
                    isPlay=true;
                }
            }
        });


    }
}
