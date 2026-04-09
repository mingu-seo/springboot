package com.example.mvc_project.common.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponseDTO<T> {

    private int page;        // 현재 페이지
    private int size;        // 페이지당 개수
    private int totalCount;  // 전체 게시글 수
    private int totalPage;   // 전체 페이지 수
    private int startPage;   // 시작 페이지
    private int endPage;     // 끝 페이지
    private boolean prev;    // 이전 페이지 존재 여부
    private boolean next;    // 다음 페이지 존재 여부
    private List<T> list;    // 목록 데이터

    @Builder
    public PageResponseDTO(int page, int size, int totalCount, List<T> list) {
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;
        this.list = list;

        // 전체 페이지 수 계산
        this.totalPage = totalCount / size;
        if (totalCount % size > 0) this.totalPage++;

        // 페이징 블록 계산 (10개 단위)
        this.endPage = (int) (Math.ceil(page / 10.0) * 10);
        this.startPage = endPage - 9;
        if (this.endPage > this.totalPage) this.endPage = this.totalPage;

        this.prev = startPage > 1;
        this.next = endPage < totalPage;
    }
}
