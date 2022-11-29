package model;

public class Category extends BaseModel {
    protected String name;

    public static final String TABLE_NAME = "category";

    public Category() {
        super();
    }

    public Category(int id, String name) {
        super(id);
        this.name = name;
    }

    public static enum ColumnKey {
        ID("cID"),
        NAME("cName");

        private final String key;

        ColumnKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return key;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Category parseString(String rawString) throws IllegalArgumentException {
        String[] record = rawString.split("\t");
        if (record.length != 2) {
            throw new IllegalArgumentException(
                    "Invalid number of entries, expected: 2, actual: " + record.length);
        }
        return new Category(Integer.parseInt(record[0]), record[1]);
    }
}
