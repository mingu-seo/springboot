package com.example.mvc_project.board.service;

import com.example.mvc_project.board.dto.BoardDTO;
import com.example.mvc_project.common.dto.PageRequestDTO;
import com.example.mvc_project.common.dto.PageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequest);
    int insert(BoardDTO dto, MultipartFile file);
    BoardDTO detail(int no, boolean increaseReadcnt);
    int update(BoardDTO dto, MultipartFile file, boolean fileDelete);
    int delete(int no);
    int reply(BoardDTO dto, MultipartFile file);
}
