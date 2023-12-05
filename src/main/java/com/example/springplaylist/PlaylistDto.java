package com.example.springplaylist;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PlaylistDto {
    private int idx;
    private String title;
    private String image;
    private String content;
    private List<MusicDto> musicList;

    public void addMusic(MusicDto music) {
        if (musicList == null) {
            musicList = new ArrayList<>();
        }
        musicList.add(music);
    }
    public void removeMusic(MusicDto music) {
        this.musicList.remove(music);
    }

    public List<MusicDto> getMusicList() {
        return this.musicList;
    }
}
