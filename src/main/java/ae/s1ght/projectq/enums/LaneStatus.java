package ae.s1ght.projectq.enums;

public enum LaneStatus {
    IDLE("Idle"),
    DISABLED("Disabled"),
    BUSY("Busy"),
    CONGESTED("Congested");

    private final String displayName;

    LaneStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
