package ae.s1ght.projectq.exception;

public class AlreadyAssignedPump extends RuntimeException {
    public AlreadyAssignedPump(String message) {
        super(message);
    }
}
