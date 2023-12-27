package ae.s1ght.projectq.exception;

public class PumpNotReserved extends RuntimeException {
    public PumpNotReserved(String message) {
        super(message);
    }
}
