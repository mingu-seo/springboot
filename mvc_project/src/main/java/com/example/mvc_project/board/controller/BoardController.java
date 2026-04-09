package com.example.mvc_project.board.controller;

import com.example.mvc_project.board.dto.BoardDTO;
import com.example.mvc_project.board.service.BoardService;
import com.example.mvc_project.common.dto.PageRequestDTO;
import com.example.mvc_project.common.dto.PageResponseDTO;
import com.example.mvc_project.common.util.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileUtil fileUtil; // 객체 주입

    /** 게시글 목록 */
    @GetMapping("/list")
    public String list(PageRequestDTO pageRequest, Model model) {
        PageResponseDTO<BoardDTO> response = boardService.list(pageRequest);
        model.addAttribute("pageResponse", response);
        model.addAttribute("pageRequest", pageRequest);
        return "board/list";
    }

    /** 글작성 폼 */
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    /** 글작성 처리 */
    @PostMapping("/write")
    public String write(BoardDTO dto, MultipartFile file,
                        HttpSession session, RedirectAttributes rttr) {

        int result = boardService.insert(dto, file);
        if (result > 0) {
            rttr.addFlashAttribute("msg", "정상적으로 저장되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "등록 오류");
        }
        return "redirect:/board/list";
    }

    /** 게시글 상세 */
    @GetMapping("/view/{no}")
    public String view(@PathVariable int no, Model model) {
        BoardDTO board = boardService.detail(no, true);
        model.addAttribute("board", board);
        return "board/view";
    }

    /** 파일 다운로드 */
    @GetMapping("/download")
    public void download(@RequestParam String filenameOrg,
                         @RequestParam String filenameReal,
                         HttpServletResponse response) throws IOException {
        String uploadPath = fileUtil.getUploadPath();
        File file = new File(uploadPath + File.separator + filenameReal);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String encodedName = URLEncoder.encode(filenameOrg, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedName + "\"");

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
        }
    }

    /** 수정 폼 */
    @GetMapping("/edit/{no}")
    public String editForm(@PathVariable int no, Model model) {
        BoardDTO board = boardService.detail(no, false);
        model.addAttribute("board", board);
        return "board/edit";
    }

    /** 수정 처리 */
    @PostMapping("/edit/{no}")
    public String edit(@PathVariable int no, BoardDTO dto, MultipartFile file,
                       @RequestParam(defaultValue = "false") boolean fileDelete,
                       RedirectAttributes rttr) {
        dto.setNo(no);
        int result = boardService.update(dto, file, fileDelete);
        if (result > 0) {
            rttr.addFlashAttribute("msg", "정상적으로 수정되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "수정 오류");
        }
        return "redirect:/board/list";
    }

    /** 삭제 처리 */
    @PostMapping("/delete/{no}")
    public String delete(@PathVariable int no, RedirectAttributes rttr) {
        int result = boardService.delete(no);
        if (result > 0) {
            rttr.addFlashAttribute("msg", "정상적으로 삭제되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "삭제 오류");
        }
        return "redirect:/board/list";
    }

    /** 답글 폼 */
    @GetMapping("/reply/{no}")
    public String replyForm(@PathVariable int no, Model model) {
        BoardDTO board = boardService.detail(no, false);
        model.addAttribute("board", board);
        return "board/reply";
    }

    /** 답글 처리 */
    @PostMapping("/reply")
    public String reply(BoardDTO dto, MultipartFile file,
                        HttpSession session, RedirectAttributes rttr) {

        int result = boardService.reply(dto, file);
        if (result > 0) {
            rttr.addFlashAttribute("msg", "정상적으로 등록되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "등록 오류");
        }
        return "redirect:/board/list";
    }
}
