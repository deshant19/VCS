package com.app.vcs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.vcs.model.FileModel;
import com.app.vcs.repository.FileRepository;
	 

@RestController
@RequestMapping(path = "/file")
@CrossOrigin(origins="http://localhost:3000")
public class VcsController {
    @Autowired
    FileRepository vcsRepository;

	@GetMapping("/hello")
	public String hello() {
	    return "Hello, the time at the server is now " + new Date() + "\n";
	}

    @PostMapping("/upload")
    public Response uplaodFile(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Original File Byte Size - " + file.getBytes().length);
        FileModel f = new FileModel(file.getOriginalFilename(), file.getContentType(),
                																file.getBytes());
        Iterable<FileModel> retrievedFile = vcsRepository.findAllByName(file.getOriginalFilename());
        
        if(retrievedFile.iterator().hasNext()) {
        	f.setVersion(IterableUtils.size(retrievedFile) + 1);
        }else {
        	f.setVersion(1);
        }
       
        vcsRepository.save(f);
        return Response.ok().entity(f).build();
    }

    @GetMapping(path = { "/{name}" })
    public Response getFile(@PathVariable("name") String name) throws IOException {
        final Iterable<FileModel> retrievedFiles = vcsRepository.findAllByName(name);
        List<FileModel> filesList = new ArrayList<>();
        
        if(retrievedFiles.iterator().hasNext()) {
        	retrievedFiles.iterator().forEachRemaining(filesList::add);
        }
        
        return Response.ok().entity(filesList).build();
    }
    
    
    @RequestMapping(path = { "/{version}/{name}" })
    public ResponseEntity<byte[]> getRandomFile(@PathVariable("version") int version, @PathVariable("name") String name) throws IOException {
     
       FileModel fileEntity = vcsRepository.findByVersionAndName(version, name).get();
     
       return ResponseEntity.ok()
               // Content-Disposition
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;name=" + fileEntity.getName())
               // Content-Type
               .contentType(MediaType.valueOf(fileEntity.getType()))
               // Contet-Length
               .contentLength(fileEntity.getFile().length) //
               .body(fileEntity.getFile());
    }

  }