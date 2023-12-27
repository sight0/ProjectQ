package ae.s1ght.projectq.enums;

public enum PaymentMethod {
    CASH("Cash"),
    APPLE_PAY("Apple Pay"),
    SAMSUNG_PAY("Samsung Pay"),
    CREDIT_CARD("Credit Card"),
    TABBY_PAYMENT("Tabby Payment");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}