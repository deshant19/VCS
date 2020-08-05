package com.app.vcs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.vcs.model.FileModel;


public interface FileRepository extends CrudRepository<FileModel, Long> {
    Optional<FileModel> findByName(String name);
    
    Optional<FileModel> findByVersionAndName(int version, String name);
    
    List<FileModel> findAllByName(String name);
}