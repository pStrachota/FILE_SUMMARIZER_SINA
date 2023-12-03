package pl.lodz.p.fileSummarizer.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentResponseDto {

    @NotEmpty(message = "Content cannot be empty")
    private String content;

}