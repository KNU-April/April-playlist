package com.example.springplaylist;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MusicDto {
    private int idx;
    private String title;
    private String image;
    private String artist;
}
