package pl.lodz.p.fileSummarizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExtractorFactory {

    private final Map<String, ContentExtractor> extractors;

    public String executeExtractor(final String extension, final MultipartFile file) {
        return extractors.get(extension).extractContent(file);
    }

}
