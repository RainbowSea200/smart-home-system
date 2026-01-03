package com.yaosheng.assistant.util;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class LocalFileStorageUtil {

    @Value("${app.upload.base-dir}")
    private String baseDir;
    @Value("${app.upload.allowed-types}")
    private String[] allowedTypes;
    @Value("${app.upload.max-size}")
    private long maxSize;

    /**
     * 保存图片到本地
     * @param file 上传的文件
     * @return 包含文件路径和访问URL的对象
     */
    public String saveImage(MultipartFile file) throws IOException {
        // 1. 验证文件
        validateFile(file);

        // 2. 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFilename = uuid + fileExtension;

        // 3. 创建日期目录：年/月/日
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String relativePath = "image/" + dateDir + "/" + newFilename;

        // 4. 完整文件路径
        Path fullPath = Paths.get(baseDir, relativePath);

        // 5. 创建目录（如果不存在）
        File parentDir = fullPath.getParent().toFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        // 6. 保存文件
        file.transferTo(fullPath.toFile());

        return relativePath;
    }

    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new RuntimeException("只能上传图片文件");
        }

        // 验证文件大小（这里可以读取配置）
        if (file.getSize() > maxSize * 1024 * 1024) { // 10MB
            throw new RuntimeException("文件大小不能超过10MB");
        }
    }

    /**
     * 删除文件
     */
    public boolean deleteFile(String relativePath) {
        Path fullPath = Paths.get(baseDir, relativePath);
        log.info("fullPath: " + fullPath);
        try {
            return Files.deleteIfExists(fullPath);
        } catch (IOException e) {
            throw new RuntimeException("删除文件失败: " + e.getMessage());
        }
    }

}
