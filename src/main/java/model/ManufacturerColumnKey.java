package model;

public enum ManufacturerColumnKey implements ColumnKey {
    ID("mID"),
    NAME("mName"),
    ADDRESS("mAddress"),
    PHONE_NUMBER("mPhoneNumber");

    private final String key;

    ManufacturerColumnKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
