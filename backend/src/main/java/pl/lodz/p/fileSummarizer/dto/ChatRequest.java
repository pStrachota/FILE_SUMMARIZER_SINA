package pl.lodz.p.fileSummarizer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRequest {

    @NotEmpty(message = "Model cannot be empty")
    private String model;

    @Valid
    @NotEmpty(message = "Messages cannot be empty")
    private List<Message> messages;

    @NotNull(message = "N cannot be null")
    private int n = 1;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
