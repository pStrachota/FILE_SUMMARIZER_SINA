package pl.lodz.p.fileSummarizer.exception;

public class FileTooBigException extends RuntimeException {

    public FileTooBigException(String message) {
        super(message);
    }

}