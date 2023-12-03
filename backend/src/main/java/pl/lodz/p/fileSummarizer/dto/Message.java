package pl.lodz.p.fileSummarizer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @NotBlank(message = "Role cannot be blank")
    private String role;

    @NotBlank(message = "Content cannot be blank")
    private String content;

}
