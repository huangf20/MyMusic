package com.example.test00001;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;

public class PlayMusicView extends FrameLayout {
    private Context mcontext;
    private View myView;
    private FrameLayout mFlameLayout;
    private ImageView mImageView;
    private Animation mPlayMusicAnim;

    public PlayMusicView(Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context)
    {
        mcontext=context;

        myView=LayoutInflater.from(mcontext).inflate(R.layout.playmusic,this,false);
        mImageView=myView.findViewById(R.id.iv_iron);
        mFlameLayout=myView.findViewById(R.id.fl_playmusic);
        /**     1.定义所需要执行的动画
         *      2.startAnimation
         */
        mPlayMusicAnim=AnimationUtils.loadAnimation(mcontext,R.anim.play_music_anim);
        addView(myView);
    }
    //播放音乐动画
    public void playmusic()
    {
        mFlameLayout.startAnimation(mPlayMusicAnim);
    }

    //停止音乐动画
    public void stopmusic()
    {
        mFlameLayout.clearAnimation();
    }
    //设置光盘中显示的音乐图片
    public void setMusicIcon(String icon)
    {
        Glide.with(mcontext)
                .load(icon)
                .into(mImageView);
    }
}
