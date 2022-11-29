package model;

public enum CategoryColumnKey implements ColumnKey {
    ID("cID"),
    NAME("cName");

    private final String key;

    CategoryColumnKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
