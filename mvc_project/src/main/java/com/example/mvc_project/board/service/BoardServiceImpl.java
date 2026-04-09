package com.example.mvc_project.board.service;

import com.example.mvc_project.board.dto.BoardDTO;
import com.example.mvc_project.board.mapper.BoardMapper;
import com.example.mvc_project.common.dto.PageRequestDTO;
import com.example.mvc_project.common.dto.PageResponseDTO;
import com.example.mvc_project.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final FileUtil fileUtil; // 객체 주입

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequest) {
        int totalCount = boardMapper.count(pageRequest);
        List<BoardDTO> list = boardMapper.list(pageRequest);

        return PageResponseDTO.<BoardDTO>builder()
                .page(pageRequest.getPage())
                .size(pageRequest.getSize())
                .totalCount(totalCount)
                .list(list)
                .build();
    }

    @Override
    public int insert(BoardDTO dto, MultipartFile file) {
        String savedName = fileUtil.saveFile(file);
        if (savedName != null) {
            dto.setFilenameOrg(file.getOriginalFilename());
            dto.setFilenameReal(savedName);
        }

        int result = boardMapper.insert(dto);
        if (result > 0) {
            boardMapper.updateGno(dto.getNo());
        }
        return result;
    }

    @Override
    public BoardDTO detail(int no, boolean increaseReadcnt) {
        if (increaseReadcnt) {
            boardMapper.increaseReadcnt(no);
        }
        return boardMapper.detail(no);
    }
    @Override
    public int update(BoardDTO dto, MultipartFile file, boolean fileDelete) {
        // 기존 파일 삭제 요청 시
        if (fileDelete) {
            BoardDTO existing = boardMapper.detail(dto.getNo());
            if (existing.getFilenameReal() != null) {
                fileUtil.deleteFile(existing.getFilenameReal());
                boardMapper.fileDelete(dto.getNo());
            }
        }

        // 새 파일 업로드
        String savedName = fileUtil.saveFile(file);
        if (savedName != null) {
            dto.setFilenameOrg(file.getOriginalFilename());
            dto.setFilenameReal(savedName);
        }

        return boardMapper.update(dto);
    }

    @Override
    public int delete(int no) {
        BoardDTO existing = boardMapper.detail(no);
        if (existing.getFilenameReal() != null && !existing.getFilenameReal().isEmpty()) {
            fileUtil.deleteFile(existing.getFilenameReal());
        }
        return boardMapper.delete(no);
    }

    @Override
    public int reply(BoardDTO dto, MultipartFile file) {
        String savedName = fileUtil.saveFile(file);
        if (savedName != null) {
            dto.setFilenameOrg(file.getOriginalFilename());
            dto.setFilenameReal(savedName);
        }

        // 기존 ono 업데이트 (부모와 같은 그룹에서 부모 ono보다 큰 것들 +1)
        boardMapper.updateOno(dto);

        dto.setOno(dto.getOno() + 1);
        dto.setNested(dto.getNested() + 1);

        return boardMapper.insert(dto);
    }

}
