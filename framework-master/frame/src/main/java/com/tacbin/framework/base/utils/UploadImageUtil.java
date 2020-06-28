package com.tacbin.framework.base.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-12 14:40
 **/
public class UploadImageUtil {
    /**
     * 上传单个图片到服务器
     *
     * @return 图片相对路径
     * @throws IOException
     */
    public String uploadSingleImageToServer(MultipartFile image) throws Exception {
        if (image == null || image.isEmpty()) {
            return "";
        }
        imageValidate(image);
        String filePath = "user.home";
        String imgSrc;
        // 获取根目录；linux->root ; windows->C:\Users\Administrator
        String userHome = System.getProperty(filePath);
        File folder = new File(userHome + "/app/images/");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        byte[] bytes = image.getBytes();
        // 生成路径，上传至具体的目录下例如，C:\Users\Administrator\app\images\
        String imgId = SnowFlakeUtil.generateId() + image.getOriginalFilename();
        imgSrc = userHome + "/app/images/" + imgId;
        Path path = Paths.get(imgSrc);
        Files.write(path, bytes);
        return "/images/" + imgId;
    }

    private void imageValidate(MultipartFile image) throws Exception {
        String content = image.getContentType().toLowerCase();
        String[] types = new String[]{"image/bmp", "image/jpg", "image/jpeg", "image/png", "image/gif"};
        List<String> imageTypes = Arrays.asList(types);
        if (!imageTypes.contains(content)) {
            throw new Exception("请上传图片");
        }
    }

}
