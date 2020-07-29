package com.app.vcs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.vcs.model.FileModel;


public interface FileRepository extends JpaRepository<FileModel, Long> {
    Optional<FileModel> findByName(String name);
}