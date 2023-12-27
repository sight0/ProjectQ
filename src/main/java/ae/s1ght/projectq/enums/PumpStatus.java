package ae.s1ght.projectq.enums;

public enum PumpStatus {
    IDLE("Idle"),
    RESERVED("Reserved"),
    DISABLED("Disabled"),
    DISPENSING("Dispensing"),
    COMPLETE("Complete");

    private final String displayName;

    PumpStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}