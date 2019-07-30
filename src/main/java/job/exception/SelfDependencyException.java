package job.exception;

public class SelfDependencyException extends RuntimeException {
    public SelfDependencyException(String message) {
        super(message);
    }
}
