package com.example.test00001.MVP;

import com.lzx.starrysky.provider.SongInfo;

import java.util.List;

public interface IMusicPlayView {
    void play_pause();
    void next_music();
    void pre_music();
    void start(List<SongInfo> songInfoList);
}
