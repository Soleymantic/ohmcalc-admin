package at.spengergasse.ohmcalculator.controller;

import at.spengergasse.ohmcalculator.model.Document;
import at.spengergasse.ohmcalculator.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("rest/documents")
//accept only request from localhost:8081 (client side)
@CrossOrigin(origins = "https://ohmcalc.herokuapp.com/")
public class DocumentRestController
{
    @Autowired
    private DocumentService documentService;

    @GetMapping
    public ResponseEntity<List<Document>> getFiles()
    {
        return ResponseEntity.ok(documentService.getFiles());
    }

    @PostMapping("/uploadFiles")
    public ResponseEntity<Boolean> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws IOException
    {
        for(MultipartFile file: files)
        {
            documentService.store(file);
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<Resource> getFile(@PathVariable("documentId") Long documentId)
    {
        Optional<Document> file = documentService.getFile(documentId);
        if(file.isPresent())
        {
            Document document = file.get();
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                .body(new ByteArrayResource(document.getData()));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteFile(@PathVariable("documentId") Long documentId)
    {
        documentService.deleteFile(documentId);
        return ResponseEntity.ok().build();
    }
}
