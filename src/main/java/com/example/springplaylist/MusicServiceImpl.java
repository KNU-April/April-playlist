package com.example.springplaylist;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl implements MusicService {
    private ArrayList<MusicDto> db = new ArrayList();

    public MusicServiceImpl() {
        System.out.println("MusicServiceImpl 객체 생성");
        db.add(new MusicDto(0, "이럴거면그러지말지", "/music1.jpg", "백아연"));
}

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
