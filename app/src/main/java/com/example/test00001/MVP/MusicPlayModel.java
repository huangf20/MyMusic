package com.example.test00001.MVP;

import com.lzx.starrysky.provider.SongInfo;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayModel {
    List<String> strings = new ArrayList<>();
    List<String> stringPic = new ArrayList<>();
    final List<SongInfo> list = new ArrayList<>();
    public void getSong(MusicCallBack musicCallBack)
    {
        strings.add("http://m8.music.126.net/20200615231532/5be341591f4f6a7f389ce55f1f744f00/ymusic/c288/d85f/2a9a/b92673879918b3d583cad67e0c591a8d.mp3");
        strings.add("http://m8.music.126.net/20200615231618/cc32832e4142b97602407ad21aa4bc13/ymusic/953f/ff44/31a5/59e8f69272032b5b29c801e596a357ce.mp3");
        strings.add("http://m7.music.126.net/20200615231645/db7ed337f42e35ec28c366cbe6fd06ee/ymusic/ca28/0179/4b2e/0b0097eb58d6494d60afceabbafaba74.mp3");

        stringPic.add("https://p1.music.126.net/LQcahhupk87rR6ObxQP90A==/3252355408653836.jpg");
        stringPic.add("https://p1.music.126.net/YxUbVoHngGMY8gZMjbEJkg==/109951163446151536.jpg");
        stringPic.add("https://p1.music.126.net/4K7h0kwxj75VbSMYsZXLrQ==/6012129580679384.jpg");
        for (int i = 0; i < strings.size(); i++) {
            SongInfo songInfo = new SongInfo();
            songInfo.setSongId(String.valueOf(i));
            songInfo.setSongUrl(strings.get(i));
            songInfo.setSongCover(stringPic.get(i));
            if (i == 0) {
                songInfo.setSongName("你，好不好");
            } else if (i == 1) {
                songInfo.setSongName("陪我长大");
            } else if (i == 2) {
                songInfo.setSongName("虹之间");
            }
            list.add(songInfo);
            boolean isSuccess=false;
            if(list.size()!=0)
            {
                isSuccess=true;
            }
            if(isSuccess)
            {
                musicCallBack.onSuccess(list);
            }
            else
            {
                musicCallBack.onFailed();
            }
        }
    }
}
