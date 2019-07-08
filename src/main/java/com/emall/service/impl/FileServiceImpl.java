package com.emall.service.impl;

import com.emall.service.IFileService;
import com.emall.util.FTPUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Harry Chou
 * @date 2019/5/29
 */
@Slf4j
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    @Override
    public String upload(MultipartFile file,String path){
        String fileName=file.getOriginalFilename();
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName= UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件，上传文件的文件名：{}，上传的路径：{}，新文件名：{}",fileName,path,uploadFileName);

        File fileDir=new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile=new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            // 将文件上传至FTP服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 删除 upload 目录下的文件
            targetFile.delete();
        } catch (IOException e) {
            log.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();
    }
}
