package com.atguigu.lease.web.app.controller.blog;

import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.web.app.service.FileService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Tag(name = "文件管理")
@RequestMapping("/app/file")
@RestController
public class FileUploadController {
    @Autowired
    private FileService service;
    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        System.out.println("开始上传文件: " + file.getOriginalFilename() + ", 大小: " + file.getSize());
        String url = service.upload(file);
        return Result.ok(url);
    }
}
