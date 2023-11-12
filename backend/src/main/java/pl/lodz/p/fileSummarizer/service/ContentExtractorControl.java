package pl.lodz.p.fileSummarizer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.fileSummarizer.dto.ChatRequest;
import pl.lodz.p.fileSummarizer.dto.ChatResponse;
import pl.lodz.p.fileSummarizer.exception.CannotReadTextException;
import pl.lodz.p.fileSummarizer.exception.OpenAiApiException;

@Service
@Slf4j
public class ContentExtractorControl {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExtractorFactory extractorFactory;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private static final String PROMPT_TEMPLATE = """
            I'm developing an application for summarizing various type of files.
            Write me a summary of what is in this file.
            Limit your response to %s00 characters.
            Write in language: %s
            Here is the content of the file that needs to be summarized:       \s
            %s
            """;

    public String extractContent(final MultipartFile multipartFile, final String language, final String contextLength, final String fileExtension) {

        String text = extractorFactory.executeExtractor(fileExtension, multipartFile);

        if (text == null || text.isEmpty()) {
            throw new CannotReadTextException("Cannot read text from PDF file");
        } else {
            String prompt = String.format(PROMPT_TEMPLATE, contextLength, language, text);
            ChatRequest request = new ChatRequest(model, prompt);

            ChatResponse response;
            try {
                response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
            } catch(RestClientException e) {
                throw new OpenAiApiException(e.getMessage());
            }

            if (response != null) {
                text = response.getChoices().get(0).getMessage().getContent();
            }

        }

        return text;
    }

}