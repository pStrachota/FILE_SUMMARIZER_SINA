package pl.lodz.p.fileSummarizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.fileSummarizer.dto.ChatRequest;
import pl.lodz.p.fileSummarizer.dto.ChatResponse;
import pl.lodz.p.fileSummarizer.exception.OpenAiApiException;

@RestController
public class ChatController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        ChatRequest request = new ChatRequest(model, prompt);

        ChatResponse response;
        try {
            response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
        } catch(RestClientException e) {
            throw new OpenAiApiException(e.getMessage());
        }

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}