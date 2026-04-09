package com.example.chapter03.controller;

import com.example.chapter03.service.BoardService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class BoardController {


    private final BoardService boardService;

    public BoardController(@Qualifier("boardServiceImpl3") BoardService boardService) {
        this.boardService = boardService;
    }

    public void write() {
        boardService.write();
    }
}