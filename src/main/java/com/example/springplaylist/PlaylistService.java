package com.example.springplaylist;

import java.util.ArrayList;

public interface PlaylistService {
    public ArrayList<PlaylistDto> findAll();
    public PlaylistDto findById(int id);
    public void delete(int idx);
    public PlaylistDto save(PlaylistDto playlist);
    public void addMusic(int playlistIdx, MusicDto music);
}
