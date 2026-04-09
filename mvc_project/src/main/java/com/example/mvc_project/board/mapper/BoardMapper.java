package com.example.mvc_project.board.mapper;

import com.example.mvc_project.board.dto.BoardDTO;
import com.example.mvc_project.common.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardDTO> list(PageRequestDTO pageRequest);
    int count(PageRequestDTO pageRequest);
    int insert(BoardDTO dto);
    int updateGno(int no);
    BoardDTO detail(int no);

    int increaseReadcnt(int no);

    int update(BoardDTO dto);
    int delete(int no);
    int fileDelete(int no);
    int updateOno(BoardDTO dto);

}