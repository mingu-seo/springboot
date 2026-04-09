package com.example.mvc_project.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 파일 업로드 처리
     * @return 저장된 파일명 (UUID + 확장자), 파일이 없으면 null
     */
    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String orgName = file.getOriginalFilename();
        String ext = orgName.substring(orgName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString() + ext;

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(dir.getAbsolutePath() + File.separator + savedName));
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }

        return savedName;
    }

    /**
     * 파일 삭제 처리
     */
    public void deleteFile(String savedName) {
        if (savedName == null || savedName.isEmpty()) {
            return;
        }
        File file = new File(uploadDir + File.separator + savedName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 업로드 디렉토리의 절대 경로 반환
     */
    public String getUploadPath() {
        return new File(uploadDir).getAbsolutePath();
    }
}