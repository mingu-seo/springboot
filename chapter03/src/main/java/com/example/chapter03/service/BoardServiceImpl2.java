package com.example.chapter03.service;

import com.example.chapter03.repository.BoardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl2 implements BoardService {

    // 어노테이션으로 주입
    @Autowired
    private BoardDAO boardDAO;

    @Override
    public void write() {
        boardDAO.save();
    }
}