package pl.lodz.p.fileSummarizer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.fileSummarizer.exception.GeneralException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service(ExtractorType.TXT)
public class TxtContentExtractor implements ContentExtractor{
    @Override
    public String extractContent(MultipartFile multipartFile) {
        try {
            return new String(multipartFile.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new GeneralException("Unknown exception while reading TXT file");
        }
    }
}
