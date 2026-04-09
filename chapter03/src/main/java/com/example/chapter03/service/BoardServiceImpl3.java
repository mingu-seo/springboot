package com.example.chapter03.service;

import com.example.chapter03.repository.BoardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl3 implements BoardService {

    // setter메서드로 주입
    private BoardDAO boardDAO;

    @Autowired
    public void setBoardDAO(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    @Override
    public void write() {
        boardDAO.save();
    }
}