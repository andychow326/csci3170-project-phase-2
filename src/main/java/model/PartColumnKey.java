package model;

public enum PartColumnKey implements ColumnKey {
    ID("pID"),
    NAME("pName"),
    PRICE("pPrice"),
    MANUFACTURER_ID("mID"),
    CATEGORY_ID("cID"),
    WRANS_PERIOD("pWarrantyPeriod"),
    AVAILABLE_QUANTITY("pAvailableQuantity");

    private final String key;

    PartColumnKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return Part.TABLE_NAME + "." + key;
    }
}
