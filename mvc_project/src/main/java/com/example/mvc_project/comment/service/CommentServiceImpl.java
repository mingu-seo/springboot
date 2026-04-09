package com.example.mvc_project.comment.service;

import com.example.mvc_project.comment.dto.CommentDTO;
import com.example.mvc_project.comment.mapper.CommentMapper;
import com.example.mvc_project.common.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public PageResponseDTO<CommentDTO> list(int parentNo, int page) {
        int size = 10;
        int totalCount = commentMapper.count(parentNo);
        int startIdx = (page - 1) * size;
        List<CommentDTO> list = commentMapper.list(parentNo, startIdx);

        return PageResponseDTO.<CommentDTO>builder()
                .page(page)
                .size(size)
                .totalCount(totalCount)
                .list(list)
                .build();
    }

    @Override
    public int insert(CommentDTO dto) {
        return commentMapper.insert(dto);
    }

    @Override
    public int delete(int no) {
        return commentMapper.delete(no);
    }
}