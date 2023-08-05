package com.elkamil.repository;

import com.elkamil.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
