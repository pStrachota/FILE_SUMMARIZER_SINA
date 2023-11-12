package pl.lodz.p.fileSummarizer.service;

import org.springframework.web.multipart.MultipartFile;

public interface ContentExtractor {

    String extractContent(final MultipartFile multipartFile);

}
