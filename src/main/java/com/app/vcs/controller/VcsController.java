package com.app.vcs.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
@RequestMapping(path = "file")
public class VcsController {
    @Autowired
    FileRepository vcsRepository;

	@GetMapping("/hello")
	public String hello() {
	    return "Hello, the time at the server is now " + new Date() + "\n";
	}

    @PostMapping("/upload")
    public BodyBuilder uplaodFile(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Original File Byte Size - " + file.getBytes().length);
        FileModel img = new FileModel(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));

        vcsRepository.save(img);
        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping(path = { "/get/{fileName}" })
    public FileModel getFile(@PathVariable("fileName") String fileName) throws IOException {
        final Optional<FileModel> retrievedFile = vcsRepository.findByName(fileName);
        FileModel img = new FileModel(retrievedFile.get().getName(), retrievedFile.get().getType(),
        decompressBytes(retrievedFile.get().getFileByte()));
        return img;
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed File Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}