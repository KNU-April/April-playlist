package com.example.springplaylist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private ArrayList<PlaylistDto> db = new ArrayList();
    @Autowired
    private MusicService musicService;
    public PlaylistServiceImpl() {
        System.out.println("playlistServiceImpl 객체 생성");
        db.add(new PlaylistDto(1, "우리가 말하지 않는것", "/download/movie1.jpg", "영화 내용 \n우리가 말하지 않는것", new ArrayList<>()));
        db.add(new PlaylistDto(2, "리미트", "/download/movie2.jpg", "영화 내용 \n리미트", new ArrayList<>()));
        db.add(new PlaylistDto(3, "시맨텍에러더무비", "/download/movie3.jpg", "영화 내용 \n시맨텍에러더무비", new ArrayList<>()));
        db.add(new PlaylistDto(4, "락다운213주", "/download/movie4.jpg", "영화 내용 \n락다운213주", new ArrayList<>()));
    }

    @Override
    public ArrayList<PlaylistDto> findAll() {
        return db;
    }


    @Override
    public PlaylistDto findById(int idx) {
        PlaylistDto find = db.stream().filter(m -> m.getIdx() == idx).findAny().get();
        return find;
    }

    @Override
    public void delete(int idx) {
        db.remove(db.stream().filter(m -> m.getIdx() == idx).findAny().get());
    }

    @Override
    public PlaylistDto save(PlaylistDto playlist) {
        if (playlist.getIdx() == -1) {
            int idx = db.get(db.size() - 1).getIdx() + 1;
            playlist.setIdx(idx);
            db.add(playlist);
        } else {
            // 기존 재생 목록 업데이트
            PlaylistDto temp = db.stream().filter(m -> m.getIdx() == playlist.getIdx()).findAny().orElse(null);
            if (temp != null) {
                temp.setTitle(playlist.getTitle());
                temp.setImage(playlist.getImage());
                temp.setContent(playlist.getContent());
            }
        }
        // 연관된 음악을 저장 또는 업데이트
        for (MusicDto music : playlist.getMusicList()) {
            musicService.save(music);
        }
        return playlist;
    }
    public void addMusic(int playlistIdx, MusicDto music) {
        PlaylistDto playlist = findById(playlistIdx);
        if (playlist != null) {
            playlist.addMusic(music);
            save(playlist);
        }
    }
}
