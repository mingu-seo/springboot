package com.example.mvc_project.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private int no;
    private int parentNo;       // 게시글 번호
    private String content;
    private LocalDateTime regdate;
    private int writer;          // 작성자 회원번호
    private String writerName;   // 작성자 이름
}