package model;

public class Category extends BaseModel {
    protected String name;

    public static final String TABLE_NAME = "category";

    public enum ColumnKey implements BaseModel.ColumnKey {
        ID("cID"),
        NAME("cName");

        private final String key;

        ColumnKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return Category.TABLE_NAME + "." + key;
        }
    }

    public Category() {
        super();
    }

    public Category(int id, String name) throws IllegalArgumentException {
        super(id);
        this.name = name;

        this.validate();
    }

    // Validate the Category entries format
    public void validate() throws IllegalArgumentException {
        checkInRange("Category ID", this.id, 1, 9);
        checkNonEmptyString("Category Name", this.name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Category parseString(String rawString) throws IllegalArgumentException {
        String[] record = rawString.split("\t");
        checkRecordLength(record.length, 2);
        return new Category(Integer.parseInt(record[0]), record[1]);
    }
}
