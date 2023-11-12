package pl.lodz.p.fileSummarizer.service;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.fileSummarizer.exception.GeneralException;


import java.io.IOException;
import java.io.InputStream;

@Service(ExtractorType.DOCX)
public class WordContentExtractor implements ContentExtractor {
    @Override
    public String extractContent(MultipartFile multipartFile) {
        try (InputStream is = multipartFile.getInputStream()) {
            XWPFDocument docx = new XWPFDocument(is);
            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(docx);
            return wordExtractor.getText();
        } catch (IOException e) {
            throw new GeneralException("Unknown exception while reading Word file");
        }
    }
}
