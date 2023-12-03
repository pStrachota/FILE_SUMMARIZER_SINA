package pl.lodz.p.fileSummarizer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ChatResponse {

    @Valid
    @NotEmpty(message = "Choices cannot be empty")
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;

        @Valid
        private Message message;
    }
}
