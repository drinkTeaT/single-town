package com.tacbin.framework.controller;

import com.tacbin.framework.base.utils.UploadImageUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description :工具类控制器
 * @Author : Administrator
 * @Date : 2020-03-06 8:54
 **/
@RestController
@RequestMapping("/tool")
public class ToolController {
    @Autowired
    private UploadImageUtil uploadImageUtil;

    @RequestMapping(method = RequestMethod.POST, value = "/uploadFile")
    @ApiOperation(value = "文件", notes = "上传文件")
    public String singleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        return uploadImageUtil.uploadSingleImageToServer(file);
    }
}
