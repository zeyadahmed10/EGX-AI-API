package exceptions;

public class PageExceeding extends IllegalStateException {
    public PageExceeding() {
    }

    public PageExceeding(String s) {
        super(s);
    }

    public PageExceeding(String message, Throwable cause) {
        super(message, cause);
    }

    public PageExceeding(Throwable cause) {
        super(cause);
    }
}
