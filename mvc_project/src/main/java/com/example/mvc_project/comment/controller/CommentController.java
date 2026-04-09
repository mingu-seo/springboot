package com.example.mvc_project.comment.controller;

import com.example.mvc_project.comment.dto.CommentDTO;
import com.example.mvc_project.comment.service.CommentService;
import com.example.mvc_project.common.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /** 댓글 목록 조회 */
    @GetMapping
    public ResponseEntity<PageResponseDTO<CommentDTO>> list(
            @RequestParam int parentNo,
            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(commentService.list(parentNo, page));
    }

    /** 댓글 등록 */
    @PostMapping
    public ResponseEntity<Map<String, Object>> insert(@RequestBody CommentDTO dto) {
        int result = commentService.insert(dto);
        return ResponseEntity.ok(Map.of("result", result));
    }

    /** 댓글 삭제 */
    @DeleteMapping("/{no}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int no) {
        int result = commentService.delete(no);
        return ResponseEntity.ok(Map.of("result", result));
    }
}