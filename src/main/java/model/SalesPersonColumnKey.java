package model;

public enum SalesPersonColumnKey implements ColumnKey {
    ID("sID"),
    NAME("sName"),
    ADDRESS("sAddress"),
    PHONE_NUMBER("sPhoneNumber"),
    EXPERIENCE("sExperience");

    private final String key;

    SalesPersonColumnKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
