package com.example.mvc_project.comment.service;

import com.example.mvc_project.comment.dto.CommentDTO;
import com.example.mvc_project.common.dto.PageResponseDTO;

public interface CommentService {

    PageResponseDTO<CommentDTO> list(int parentNo, int page);

    int insert(CommentDTO dto);

    int delete(int no);
}