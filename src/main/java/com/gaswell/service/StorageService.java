package com.gaswell.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author Lei Wang
 * @Date: 2021/12/18/ 15:09
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 文件上传与存储
public interface StorageService {
    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
