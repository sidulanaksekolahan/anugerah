package com.elkamil.service;

import com.elkamil.entity.Product;
import com.elkamil.message.ResponseFile;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface ProductService {

    Product store(MultipartFile file) throws IOException;

    Product getFile(String id);

    Stream<Product> getAllFiles();

    Product deleteImageById(String id);

    Page<Product> getPaging(Integer page, Integer size);

    List<ResponseFile> getAllDownloadUrls();

    List<ResponseFile> getAllDisplayUrls();
}
