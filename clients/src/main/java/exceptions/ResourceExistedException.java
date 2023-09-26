package exceptions;

public class ResourceExistedException extends RuntimeException {
    public ResourceExistedException(String message, Throwable cause) {
        super(message, cause);
    }
    public ResourceExistedException() {
        super();
    }
    public ResourceExistedException(String message) {
        super(message);
    }
    public ResourceExistedException(Throwable cause) {
        super(cause);
    }
}
