package com.example.test00001.MVP;

import com.lzx.starrysky.provider.SongInfo;

import java.util.List;

public interface MusicCallBack {
    void onSuccess(List<SongInfo> songInfoList);
    void onFailed();
}
