package com.example.mvc_project.comment.mapper;

import com.example.mvc_project.comment.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    int insert(CommentDTO dto);

    int count(@Param("parentNo") int parentNo);

    List<CommentDTO> list(@Param("parentNo") int parentNo,
                          @Param("startIdx") int startIdx);

    int delete(int no);
}
