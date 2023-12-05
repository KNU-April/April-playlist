package com.example.springplaylist;

import java.util.ArrayList;

public interface MusicService {
    public ArrayList<MusicDto> findAll();
    public MusicDto findById(int id);
    public void delete(int idx);
    public MusicDto save(MusicDto music);
//    ArrayList<MusicDto> findAllByPlaylistIdx(int playlistIdx);
}
