package model;

public enum SalespersonColumnKey implements ColumnKey {
    ID("sID"),
    NAME("sName"),
    ADDRESS("sAddress"),
    PHONE_NUMBER("sPhoneNumber"),
    EXPERIENCE("sExperience");

    private final String key;

    SalespersonColumnKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return Salesperson.TABLE_NAME + "." + key;
    }
}
