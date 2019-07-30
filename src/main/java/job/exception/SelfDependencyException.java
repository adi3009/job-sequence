package job.exception;

public class SelfDependencyException extends RuntimeException {
    public final static String MESSAGE = "jobs can’t depend on themselves.";

    public SelfDependencyException() {
        super(MESSAGE);
    }
}
