package com.example.springplaylist;


import lombok.*;

@Getter @Setter  @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private int idx;
    private String title;
    private String image;
    private String content;
}
