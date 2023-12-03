package pl.lodz.p.fileSummarizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.fileSummarizer.dto.ContentResponseDto;
import pl.lodz.p.fileSummarizer.service.ContentExtractorControl;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/extractor")
@RequiredArgsConstructor
@Validated
public class ContentExtractorBoundary {

    private final ContentExtractorControl contentExtractorControl;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity<ContentResponseDto> classify(@RequestParam("file") final MultipartFile pdfFile,
                                                       @RequestParam("language") final String language,
                                                       @RequestParam("contextLength") final String contextLength ,
                                                       @RequestParam("fileExtension") final String fileExtension) {
        return ResponseEntity.ok().body(ContentResponseDto.builder().content(this.contentExtractorControl.extractContent(pdfFile, language, contextLength , fileExtension)).build());
    }


}