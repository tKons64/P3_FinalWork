package me.tretyakovv.p3_finalwork.controllers;

import me.tretyakovv.p3_finalwork.services.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final FilesService filesService;

    @Value("${name.of.file.transactions}")
    private String dataFileTransactions;

    @Value("${name.of.file.tableBalance}")
    private String dataFileTableBalance;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    public ResponseEntity<InputStreamResource> downloadDataFile(String dataFileName) throws FileNotFoundException {
        File file = filesService.getDataFile(dataFileName);

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; ")
                    .contentLength(file.length())
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<Void> uploadDataFile(MultipartFile file, String dataFileName){

        File dataFile = filesService.getDataFile(dataFileName);
        filesService.cleanDataFile(dataFileName);

        try (FileOutputStream fos = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = "/exportTransactions",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadTransactions() throws FileNotFoundException {
        return downloadDataFile(dataFileTransactions);
    }

    @GetMapping(value = "/exportTableBalance",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadTableBalance() throws FileNotFoundException {
        return downloadDataFile(dataFileTableBalance);
    }

    @PostMapping(value = "/importTransactions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadTransactions(@RequestParam MultipartFile file){
        return uploadDataFile(file, dataFileTransactions);
    }

    @PostMapping(value = "/importTableBalance", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadTableBalance(@RequestParam MultipartFile file){
        return uploadDataFile(file, dataFileTableBalance);
    }

}
