package com.example.chapter03.service;

import com.example.chapter03.repository.BoardDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@Primary
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardDAO boardDAO;

//    // 생성자로 주입
//    public BoardServiceImpl(BoardDAO boardDAO) {
//        this.boardDAO = boardDAO;
//    }

    @Override
    public void write() {
        boardDAO.save();
    }
}