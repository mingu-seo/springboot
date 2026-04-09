package com.example.mvc_project.common.dto;

import lombok.Data;

@Data
public class PageRequestDTO {

    private int page;         // 현재 페이지 번호
    private int size;         // 페이지당 게시글 수
    private String searchType; // 검색 유형 (all, title, content)
    private String searchWord; // 검색어

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public int getStartIdx() {
        return (page - 1) * size;
    }
}
