package com.example.mvc_project.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {

    private int no;
    private String title;
    private String content;
    private LocalDateTime regdate;
    private int readcnt;
    private int gno;         // 그룹번호
    private int ono;         // 그룹 내 순서
    private int nested;      // 들여쓰기 레벨
    private int writer;      // 작성자 회원번호

    private String writerName;     // 작성자 이름
    private String filenameOrg;    // 원본 파일명
    private String filenameReal;   // 저장 파일명
    private int commentCount;      // 댓글 수
}