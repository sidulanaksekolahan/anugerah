package com.elkamil.service;

import com.elkamil.entity.Product;
import com.elkamil.exception.ImageNotFoundException;
import com.elkamil.message.ResponseFile;
import com.elkamil.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository fileDBRepository;

    @Override
    public Product store(MultipartFile file) throws IOException {

        // Get original file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Get type
        String type = StringUtils.cleanPath(file.getContentType());

        // Get bytes
        byte[] data = file.getBytes();

        // Construct FileDB
        Product fileDB = new Product(fileName, type, data);

        // Save the first FileDB
        Product fileDBFirstSaved = fileDBRepository.save(fileDB);

        // Create fileDisplayImageUri
        String  fileDisplayImageUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/images/display/")
                .path(fileDBFirstSaved.getId())
                .toUriString();

        // Create fileDownloadImageUri
        String fileDownloadImageUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/images/filesDownload/")
                .path(fileDBFirstSaved.getId())
                .toUriString();

        // Set downloadImageUri
        fileDBFirstSaved.setDisplayImageUri(fileDisplayImageUri);

        // Set downloadImageUri
        fileDBFirstSaved.setDownloadImageUri(fileDownloadImageUri);

        // Save the updated FileDB
        Product fileDBFinalSaved = fileDBRepository.save(fileDBFirstSaved);

        // Return the result
        return fileDBFinalSaved;
    }

    @Override
    public Product getFile(String id) {

        Optional<Product> theFileDb = fileDBRepository.findById(id);

        if (!theFileDb.isPresent()) {
            throw new ImageNotFoundException("No image with specified id");
        }

        return theFileDb.get();
    }

    @Override
    public Stream<Product> getAllFiles() {

        return fileDBRepository.findAll().stream();
    }

    @Override
    public Product deleteImageById(String id) {

        Product fileDB = getFile(id);

        fileDBRepository.deleteById(fileDB.getId());

        return fileDB;
    }

    @Override
    public Page<Product> getPaging(Integer page, Integer size) {

        Pageable pageable = null;
        if ((page <= 0) || (size <= 0)) {
            pageable = PageRequest.of(0, 2);
            return fileDBRepository.findAll(pageable);
        }

        pageable = PageRequest.of(page - 1, size);

        return fileDBRepository.findAll(pageable);
    }

    @Override
    public List<ResponseFile> getAllDownloadUrls() {

        return fileDBRepository.findAll()
                .stream()
                .map(fileDB -> {
                    return new ResponseFile(
                            fileDB.getProductName(),
                            fileDB.getDownloadImageUri(),
                            fileDB.getImageType(),
                            fileDB.getData().length);
                }).collect(Collectors.toList());
    }

    @Override
    public List<ResponseFile> getAllDisplayUrls() {

        return fileDBRepository.findAll()
                .stream()
                .map(fileDB -> {
                    System.out.println("fileDB.getDisplayImageUri(): " + fileDB.getDisplayImageUri());
                    return new ResponseFile(
                            fileDB.getProductName(),
                            fileDB.getDisplayImageUri(),
                            fileDB.getImageType(),
                            fileDB.getData().length);
                }).collect(Collectors.toList());
    }
}
