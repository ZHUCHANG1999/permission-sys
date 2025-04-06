package com.permission.authority.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.permission.authority.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
public class FileServiceImpl implements FileService {

//    @Resource
//    OSS ossClient ;
//
//    @Value("${spring.cloud.alicloud.oss.endpoint}")
//    private String endpoint ;
//
//    @Value("${spring.cloud.alicloud.oss.bucket}")
//    private String bucket ;

    /**
     * 文件上传
     *
     * @param file   文件上传对象
     * @param module 文件夹名称
     * @return
     */
//    @Override
//    public String upload(MultipartFile file, String module) {
//        try {
//            //创建OSSClient实例
//            //            OSS ossClient = new OSSClientBuilder().build(endPoint, keyId,
//            //                    keySecret);
//            //上传文件流
//            InputStream inputStream = file.getInputStream();
//            //获取旧名称
//            String originalFilename = file.getOriginalFilename();
//            //获取文件后缀名
//            String extension = FilenameUtils.getExtension(originalFilename);
//            //将文件名重命名
//            String newFileName = UUID.randomUUID().toString()
//                    .replace("-", "") + "." + extension;
//            //使用当前日期进行分类管理
//            String datePath = new DateTime().toString("yyyy/MM/dd");
//            //构建文件名
//            newFileName = module + "/" + datePath + "/" + newFileName;
//            //调用OSS文件上传的方法
//            ossClient.putObject(bucket, newFileName, inputStream);
//            //关闭OSSClient
//            ossClient.shutdown();
//            //返回文件地址
//            return "https://" + bucket + "." + endpoint + "/" + newFileName;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    @Override
//    public void deleteFile(String url) {
//        try {
//            //组装文件地址
//            String host = "https://" + bucket + "." + endpoint + "/";
//            //获取文件名称
//            String objectName = url.substring(host.length());
//            //删除文件
//            ossClient.deleteObject(bucket, objectName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}