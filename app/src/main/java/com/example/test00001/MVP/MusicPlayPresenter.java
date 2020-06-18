package com.example.test00001.MVP;

import android.view.WindowManager;

import com.lzx.starrysky.provider.SongInfo;

import java.util.List;

public class MusicPlayPresenter {
    private IMusicPlayView iMusicPlayView;
    private MusicPlayModel musicPlayModel;

    public MusicPlayPresenter(IMusicPlayView iMusicPlayView) {
        this.iMusicPlayView = iMusicPlayView;

        musicPlayModel=new MusicPlayModel();
    }

    public void getSong() {
        musicPlayModel.getSong(new MusicCallBack() {
            @Override
            public void onSuccess(List<SongInfo> songInfoList) {
                iMusicPlayView.start(songInfoList);
            }

            @Override
            public void onFailed() {

            }
        });
    }
    public void next()
    {
        iMusicPlayView.next_music();
    }
    public void pre()
    {
        iMusicPlayView.pre_music();
    }
}
