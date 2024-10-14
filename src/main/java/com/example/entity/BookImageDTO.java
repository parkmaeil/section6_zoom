package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookImageDTO {
    private Long num;
    private String title;
    private int price;
    private String author;
    private int page;

    private Long id;
    private int book_num;
    private int type;
    private String path; // 파일이름
}
