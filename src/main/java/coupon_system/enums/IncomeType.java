package coupon_system.enums;

public enum IncomeType {

    CUSTOMER_PURCHASE(""),
    COMPANY_NEW_COUPON(""),
    COMPANY_UPDATE_COUPON("");

    private String description;

    IncomeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
