package pl.lodz.p.fileSummarizer.ut;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.fileSummarizer.dto.ChatRequest;
import pl.lodz.p.fileSummarizer.dto.ChatResponse;
import pl.lodz.p.fileSummarizer.dto.Message;
import pl.lodz.p.fileSummarizer.exception.*;
import pl.lodz.p.fileSummarizer.service.ContentExtractorControl;
import pl.lodz.p.fileSummarizer.service.ExtractorFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitTests {


    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ExtractorFactory extractorFactory;

    @InjectMocks
    private ContentExtractorControl contentExtractorControl;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenFileIsTooBig() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getSize()).thenReturn(2_000_001L);

        assertThrows(FileTooBigException.class, () -> contentExtractorControl.extractContent(file, "english", "300", "pdf"));

        verify(extractorFactory, never()).executeExtractor(any(), any());
        verify(restTemplate, never()).postForObject(anyString(), any(ChatRequest.class), eq(ChatResponse.class));
    }


    @Test
    void shouldThrowExceptionWhenLanguageIsNotSupported() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getSize()).thenReturn(1_000_000L);

        assertThrows(UnsupportedLanguageException.class, () ->
                contentExtractorControl.extractContent(multipartFile, "Spanish", "5", "txt"));
    }


    @Test
    void shouldThrowExceptionWhenFileExtensionNotMatch() {
        MultipartFile multipartFile = new MockMultipartFile(
                "testFile.txt",
                "testFile.txt",
                "text/plain",
                "This is a test".getBytes()
        );

        assertThrows(InvalidContextLengthException.class, () -> contentExtractorControl.extractContent(multipartFile, "English", "300", "pdf"));

        verify(restTemplate, never()).postForObject(anyString(), any(ChatRequest.class), eq(ChatResponse.class));
    }

    @Test
    void shouldThrowExceptionWhenFileContentContainsTooManyCharacters() {
        MultipartFile multipartFile = new MockMultipartFile(
                "testFile.txt",
                "testFile.txt".repeat(100_000),
                "text/plain",
                "This is a test".getBytes()
        );

        when(extractorFactory.executeExtractor(any(), any())).thenReturn("Sample content".repeat(100_000));

        assertThrows(FileContentTooLongException.class, () -> contentExtractorControl.extractContent(multipartFile, "English", "5", "txt"));

        verify(restTemplate, never()).postForObject(anyString(), any(ChatRequest.class), eq(ChatResponse.class));
    }


    @Test
    void shouldThrowExceptionWhenFileContentIsEmpty() {
        MultipartFile multipartFile = new MockMultipartFile(
                "testFile.txt",
                "testFile.txt",
                "text/plain",
                "This is a test".getBytes()
        );
        when(extractorFactory.executeExtractor(any(), any())).thenReturn("");

        assertThrows(EmptyFileContentException.class, () -> contentExtractorControl.extractContent(multipartFile, "English", "5", "txt"));

        verify(restTemplate, never()).postForObject(anyString(), any(ChatRequest.class), eq(ChatResponse.class));
    }

    @Test
    void shouldThrowExceptionWhenContextLengthIsNotSupported() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getSize()).thenReturn(1_000_000L);

        assertThrows(InvalidContextLengthException.class, () ->
                contentExtractorControl.extractContent(multipartFile, "English", "11", "txt"));
    }

    @Test
    void shouldReturnResponseWhenParametersAreCorrect() {
        ReflectionTestUtils.setField(contentExtractorControl, "apiUrl", "https://api.openai.com/v1/chat/completions");
        MultipartFile file = new MockMultipartFile(
                "testFile.txt",
                "testFile.txt",
                "text/plain",
                "This is a test".getBytes()
        );
        when(extractorFactory.executeExtractor(any(), any())).thenReturn("Sample content");
        Message message = new Message("user", "Hello, this is a message");
        ChatResponse.Choice choice = new ChatResponse.Choice(1, message);
        List<ChatResponse.Choice> choices = List.of(choice);
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChoices(choices);

        when(restTemplate.postForObject(anyString(), any(ChatRequest.class), eq(ChatResponse.class))).thenReturn(chatResponse);
        String result = contentExtractorControl.extractContent(file, "English", "5", "txt");

        assertEquals("Hello, this is a message", result);

    }

}


