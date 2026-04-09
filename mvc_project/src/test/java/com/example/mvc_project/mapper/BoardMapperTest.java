package com.example.mvc_project.mapper;

import com.example.mvc_project.board.dto.BoardDTO;
import com.example.mvc_project.board.mapper.BoardMapper;
import com.example.mvc_project.common.dto.PageRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BoardMapperTest {
    @Autowired
    BoardMapper mapper;

    @Test
    public void listTest() {
        PageRequestDTO dto = new PageRequestDTO();
        List<BoardDTO> list = mapper.list(dto);
        list.forEach(b-> System.out.println(b));
    }
}
