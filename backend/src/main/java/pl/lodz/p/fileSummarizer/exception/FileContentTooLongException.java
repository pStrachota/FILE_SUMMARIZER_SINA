package pl.lodz.p.fileSummarizer.exception;

public class FileContentTooLongException extends RuntimeException {

    public FileContentTooLongException(String message) {
        super(message);
    }

}