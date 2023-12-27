package ae.s1ght.projectq.enums;

public enum CameraLocation {
    ENTRANCE("Entrance"),
    EXIT("Exit"),
    FUELING_AREA("Fueling Area"),
    OTHER("Other");

    private final String displayName;

    CameraLocation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
