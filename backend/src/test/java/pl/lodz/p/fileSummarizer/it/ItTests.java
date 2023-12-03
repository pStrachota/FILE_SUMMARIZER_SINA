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



}
