package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.minio.MinioProperties;
import com.atguigu.lease.web.app.service.FileManagementService;
import com.atguigu.lease.web.app.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties properties;

    @Autowired
    private FileManagementService fileManagementService; // 注入文件管理服务

    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(properties.getBucketName())
                        .build());
        if (!bucketExists){
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(properties.getBucketName())
                            .build());
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(properties.getBucketName())
                    .config(createBucketPolicyConfig(properties.getBucketName()))
                    .build());
        }

        // 修改: 使用'temp/'目录存储临时文件
        String dateFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String uuid = UUID.randomUUID().toString();
        String filename = "temp/" + dateFolder + "/" + uuid + "-" + file.getOriginalFilename();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .object(filename)
                        .contentType(file.getContentType())
                        .build());

        // 构建访问URL
        String url = String.join("/", properties.getEndpoint(), properties.getBucketName(), filename);

        // 记录文件上传到文件管理表
        fileManagementService.recordFileUpload(file.getOriginalFilename(), filename, url);

        return url;
    }

    private String createBucketPolicyConfig(String bucketName) {

        return """
            {
              "Statement" : [ {
                "Action" : "s3:GetObject",
                "Effect" : "Allow",
                "Principal" : "*",
                "Resource" : "arn:aws:s3:::%s/*"
              } ],
              "Version" : "2012-10-17"
            }
            """.formatted(bucketName);
    }
}