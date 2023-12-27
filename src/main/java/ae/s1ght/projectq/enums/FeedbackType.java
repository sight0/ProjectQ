package ae.s1ght.projectq.enums;

public enum FeedbackType {
    COMPLAINT("Complaint"),
    SUGGESTION("Suggestion"),
    COMPLIMENT("Compliment"),
    S_REPORT_ISSUE("Staff Report Issue"),
    C_REPORT_ISSUE("Customer Report Issue");

    private final String displayName;

    FeedbackType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
