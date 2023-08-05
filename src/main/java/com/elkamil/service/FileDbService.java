package com.elkamil.service;

import com.elkamil.entity.FileDB;
import com.elkamil.message.ResponseFile;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface FileDbService {

    FileDB store(MultipartFile file) throws IOException;

    FileDB getFile(String id);

    Stream<FileDB> getAllFiles();

    FileDB deleteImageById(String id);

    Page<FileDB> getPaging(Integer page, Integer size);

    List<ResponseFile> getAllDownloadUrls();

    List<ResponseFile> getAllDisplayUrls();
}
