package com.example.springplaylist;

import java.util.ArrayList;
import java.util.List;

public interface PlaylistService {
    public ArrayList<PlaylistDto> findAll();
    public PlaylistDto findById(int id);
    List<PlaylistDto> findByTitleContaining(String query);
    public void delete(int idx);
    public PlaylistDto save(PlaylistDto playlist);
    public void addMusic(int playlistIdx, MusicDto music);
}
