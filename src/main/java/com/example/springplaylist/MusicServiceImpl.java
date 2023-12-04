package com.example.springplaylist;

import java.util.ArrayList;

public class MusicServiceImpl implements MusicService {
    private ArrayList<MusicDto> db = new ArrayList();

    public MusicServiceImpl() {
        System.out.println("MusicServiceImpl 객체 생성");
        db.add(new MusicDto(1, "우리가 말하지 않는것", "/download/movie1.jpg", "영화 내용 \n우리가 말하지 않는것"));
        db.add(new MusicDto(2, "리미트", "/download/movie2.jpg", "영화 내용 \n리미트"));
        db.add(new MusicDto(3,  "시맨텍에러더무비", "/download/movie3.jpg", "영화 내용 \n시맨텍에러더무비"));
        db.add(new MusicDto(4, "락다운213주", "/download/movie4.jpg", "영화 내용 \n락다운213주"));    }

    @Override
    public ArrayList<MusicDto> findAll() {
        return db;
    }

    @Override
    public MusicDto findById(int idx) {
        MusicDto find = db.stream().filter(m -> m.getIdx() == idx).findAny().get();
        return find;
    }

    @Override
    public void delete(int idx) {
        db.remove(db.stream().filter(m -> m.getIdx() == idx).findAny().get());
    }

    @Override
    public MusicDto save(MusicDto playlist) {
        if ( playlist.getIdx() == -1) {
            int idx = db.get(db.size()-1).getIdx() + 1;
            playlist.setIdx(idx);
            db.add(playlist);
            return playlist;
        } else {
            MusicDto temp = db.stream().filter(m -> m.getIdx() == playlist.getIdx()).findAny().get();
            temp.setTitle(playlist.getTitle());
            temp.setImage((playlist.getImage()));
            temp.setArtist(playlist.getArtist());
            return temp;
        }
    }
}
