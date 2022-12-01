package model;

public class Part extends BaseModel {
    protected String name;
    protected int price;
    protected int manufacturerID;
    protected int categoryID;
    protected int warrantyPeriod;
    protected int availableQuantity;

    public static final String TABLE_NAME = "part";

    public enum ColumnKey implements BaseModel.ColumnKey {
        ID("pID"),
        NAME("pName"),
        PRICE("pPrice"),
        MANUFACTURER_ID("mID"),
        CATEGORY_ID("cID"),
        WARRANTY_PERIOD("pWarrantyPeriod"),
        AVAILABLE_QUANTITY("pAvailableQuantity");

        private final String key;

        ColumnKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return Part.TABLE_NAME + "." + key;
        }
    }

    public Part() {
        super();
    }

    public Part(
            int id,
            String name,
            int price,
            int manufacturerID,
            int categoryID,
            int warrantyPeriod,
            int availableQuantity) throws IllegalArgumentException {
        super(id);
        this.name = name;
        this.price = price;
        this.manufacturerID = manufacturerID;
        this.categoryID = categoryID;
        this.warrantyPeriod = warrantyPeriod;
        this.availableQuantity = availableQuantity;

        this.validate();
    }

    // Validate the Part entries format
    public void validate() throws IllegalArgumentException {
        checkInRange("Part ID", this.id, 1, 999);
        checkNonEmptyString("Part Name", this.name);
        checkInRange("Part Price", this.price, 1, 99999);
        checkInRange("Part Manufacturer ID", this.manufacturerID, 1, 99);
        checkInRange("Part Category ID", this.categoryID, 1, 9);
        checkInRange("Part Warranty Period", this.warrantyPeriod, 1, 99);
        checkInRange("Part Available Quantity", this.availableQuantity, 0, 99);

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getManufacturerID() {
        return this.manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public int getCategoryID() {
        return this.categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getWarrantyPeriod() {
        return this.warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getAvailableQuantity() {
        return this.availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public static Part parseString(String rawString) throws IllegalArgumentException {
        String[] record = rawString.split("\t");
        checkRecordLength(record.length, 7);
        return new Part(
                Integer.parseInt(record[0]),
                record[1],
                Integer.parseInt(record[2]),
                Integer.parseInt(record[3]),
                Integer.parseInt(record[4]),
                Integer.parseInt(record[5]),
                Integer.parseInt(record[6]));
    }
}
