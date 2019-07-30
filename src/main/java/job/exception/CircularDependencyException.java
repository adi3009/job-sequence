package job.exception;

public class CircularDependencyException extends RuntimeException {

    public final static String MESSAGE = "jobs can’t have circular dependencies.";

    public CircularDependencyException() {
        super(MESSAGE);
    }
}
