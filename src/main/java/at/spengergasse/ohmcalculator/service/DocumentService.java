package at.spengergasse.ohmcalculator.service;

import antlr.StringUtils;
import at.spengergasse.ohmcalculator.model.Document;
import at.spengergasse.ohmcalculator.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService
{
    @Autowired
    private DocumentRepository documentRepository;

    public Document store(MultipartFile file) throws IOException{
        String docname = file.getOriginalFilename();
        Document document = new Document();
        document.setName(docname);
        document.setType(file.getContentType());
        document.setData(file.getBytes());
        return documentRepository.save(document);
    }

    public Optional<Document> getFile(Long id){
        return documentRepository.findById(id);
    }

    public List<Document> getFiles()
    {
        return documentRepository.findAll();
    }

    public void deleteFile(Long id) {
        documentRepository.deleteById(id);
    }
}
