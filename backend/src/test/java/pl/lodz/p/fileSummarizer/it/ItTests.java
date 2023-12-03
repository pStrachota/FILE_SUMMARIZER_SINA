package pl.lodz.p.fileSummarizer.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.util.ResourceUtils.getFile;

@Log
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Order(1)
    void shouldReturnResponseWhenParametersAreCorrectPdf() throws FileNotFoundException {
        String filePath = Paths.get("src", "test", "resources", "ZASOBY.pdf").toString();
        String language = "English";
        String fileExtension = "pdf";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 5)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Order(2)
    void shouldThrowExceptionWhenFileExtensionNotMatch() throws FileNotFoundException {
        String filePath = Paths.get("src", "test", "resources", "ZASOBY.PDF").toString();
        String language = "English";
        String fileExtension = "txt";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 5)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", org.hamcrest.Matchers.equalTo("File extension is not supported"));
    }

    @Test
    @Order(3)
    void shouldThrowExceptionWhenFileContentIsEmpty() throws FileNotFoundException {
        String filePath = Paths.get("src", "test", "resources", "blank.txt").toString();
        String language = "English";
        String fileExtension = "txt";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 5)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", org.hamcrest.Matchers.equalTo("File content is empty"));
    }

    @Test
    @Order(4)
    void shouldThrowExceptionWhenFileSizeIsTooBig() throws FileNotFoundException, InterruptedException {
        String filePath = Paths.get("src", "test", "resources", "toobig.pdf").toString();
        String language = "English";
        String fileExtension = "pdf";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 5)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", org.hamcrest.Matchers.equalTo("File is too big"));

    }


    @Test
    @Order(5)
    void shouldReturnResponseWhenParametersAreCorrectDocx() throws FileNotFoundException {
        String filePath = Paths.get("src", "test", "resources", "report.docx").toString();
        String language = "English";
        String fileExtension = "docx";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 5)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Order(6)
    void shouldThrowExceptionWhenContextLengthIsInvalid() throws FileNotFoundException {
        String filePath = Paths.get("src", "test", "resources", "report.docx").toString();
        String language = "English";
        String fileExtension = "docx";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 42)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", org.hamcrest.Matchers.equalTo("Context length is invalid"));
    }

    @Test
    @Order(7)
    void shouldThrowExceptionWhenFileContentIsTooLong() throws FileNotFoundException {
        String filePath = Paths.get("src", "test", "resources", "07.pdf").toString();
        String language = "English";
        String fileExtension = "pdf";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 5)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", org.hamcrest.Matchers.equalTo("File content is too long"));
    }

    @Test
    @Order(8)
    void shouldReturnResponseWhenParametersAreCorrectTxt() throws FileNotFoundException {
        String filePath = Paths.get("src", "test", "resources", "PBSI.txt").toString();
        String language = "English";
        String fileExtension = "txt";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 5)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Order(9)
    void shouldThrowExceptionWhenLanguageIsNotSupported() throws FileNotFoundException {
        String filePath = Paths.get("src", "test", "resources", "PBSI.txt").toString();
        String language = "Hawaiian";
        String fileExtension = "txt";

        given()
                .multiPart("file", getFile(filePath))
                .formParam("language", language)
                .formParam("fileExtension", fileExtension)
                .formParam("contextLength", 5)
                .contentType(ContentType.MULTIPART)
                .when()
                .post("/api/v1/extractor")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", org.hamcrest.Matchers.equalTo("Language is not supported"));
    }

}
