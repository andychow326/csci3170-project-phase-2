package model;

public enum TransactionColumnKey implements ColumnKey {
    ID("tID"),
    PART_ID("pID"),
    SALES_PERSON_ID("sID"),
    DATE("tDate");

    private final String key;

    TransactionColumnKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

}
