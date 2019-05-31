package com.emall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Harry Chou
 * @date 2019/5/29
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
