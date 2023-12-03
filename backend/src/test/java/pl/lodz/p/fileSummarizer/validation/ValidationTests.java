package pl.lodz.p.fileSummarizer.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.fileSummarizer.dto.ChatRequest;
import pl.lodz.p.fileSummarizer.dto.ChatResponse;
import pl.lodz.p.fileSummarizer.dto.Message;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationTests {

    private Validator validator;

    @BeforeEach
    public void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void givenInvalidChatRequestModel_whenValidating_thenValidationError() {
        ChatRequest chatRequest = new ChatRequest("", "prompt");
        Set<ConstraintViolation<ChatRequest>> violations = validator.validate(chatRequest);

        assertEquals(1, violations.size());
        assertEquals("Model cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    void givenInvalidChatResponseChoices_whenValidating_thenValidationErrors() {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChoices(Collections.singletonList(new ChatResponse.Choice(1, new Message("", "content"))));

        Set<ConstraintViolation<ChatResponse>> violations = validator.validate(chatResponse);

        assertEquals(1, violations.size());
    }

    @Test
    void givenInvalidMessageContentAndRole_whenValidating_thenValidationErrors() {
        Message message = new Message("", "");
        Set<ConstraintViolation<Message>> violations = validator.validate(message);

        assertEquals(2, violations.size());
    }

    @Test
    void givenValidChatRequest_whenValidating_thenNoErrors() {
        ChatRequest chatRequest = new ChatRequest("model", "prompt");
        Set<ConstraintViolation<ChatRequest>> violations = validator.validate(chatRequest);

        assertEquals(0, violations.size());
    }

    @Test
    void givenValidChatResponse_whenValidating_thenNoErrors() {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChoices(Collections.singletonList(new ChatResponse.Choice(1, new Message("user", "content"))));

        Set<ConstraintViolation<ChatResponse>> violations = validator.validate(chatResponse);

        assertEquals(0, violations.size());
    }

    @Test
    void givenValidMessage_whenValidating_thenNoErrors() {
        Message message = new Message("user", "content");
        Set<ConstraintViolation<Message>> violations = validator.validate(message);

        assertEquals(0, violations.size());
    }

}
