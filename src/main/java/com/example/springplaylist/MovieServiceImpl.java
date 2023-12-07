package com.example.springplaylist;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService  {

    private ArrayList<MovieDto> db = new ArrayList();

    public MovieServiceImpl() {
        System.out.println("MovieServiceImpl 객체 생성");
        db.add(new MovieDto(1, "겨울 플레이리스트", "/download/winter.jpeg", "겨울 플레이리스트"));
        db.add(new MovieDto(2, "봄 플레이리스트", "/download/spring.jpeg", "영화 내용 \n리미트"));
        db.add(new MovieDto(3,  "여름 플레이리스트", "/download/summer.jpeg", "영화 내용 \n시맨텍에러더무비"));
        db.add(new MovieDto(4, "가을 플레이리스트", "/download/fall.jpeg", "영화 내용 \n락다운213주"));    }

    @Override
    public ArrayList<MovieDto> findAll() {
        return db;
    }

    @Override
    public MovieDto findById(int idx) {
        MovieDto find = db.stream().filter(m -> m.getIdx() == idx).findAny().get();
        return find;
    }

    @Override
    public void delete(int idx) {
        db.remove(db.stream().filter(m -> m.getIdx() == idx).findAny().get());
    }

    @Override
    public MovieDto save(MovieDto movie) {
        if ( movie.getIdx() == -1) {
            int idx = db.get(db.size()-1).getIdx() + 1;
            movie.setIdx(idx);
            db.add(movie);
            return movie;
        } else {
            MovieDto temp = db.stream().filter(m -> m.getIdx() == movie.getIdx()).findAny().get();
            temp.setTitle(movie.getTitle());
            temp.setImage((movie.getImage()));
            temp.setContent(movie.getContent());
            return temp;
        }
    }
}
