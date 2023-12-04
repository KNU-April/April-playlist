package com.example.springplaylist;

import java.util.ArrayList;

public interface MovieService {
    public ArrayList<MovieDto> findAll();
    public MovieDto findById(int id);
    public void delete(int idx);
    public MovieDto save(MovieDto movie);
}
