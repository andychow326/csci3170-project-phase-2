package model;

public class Manufacturer extends BaseModel {
    protected String name;
    protected String address;
    protected int phoneNumber;

    public static final String TABLE_NAME = "manufacturer";

    public enum ColumnKey implements BaseModel.ColumnKey {
        ID("mID"),
        NAME("mName"),
        ADDRESS("mAddress"),
        PHONE_NUMBER("mPhoneNumber");

        private final String key;

        ColumnKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return Manufacturer.TABLE_NAME + "." + key;
        }
    }

    public Manufacturer() {
        super();
    }

    public Manufacturer(
            int id,
            String name,
            String address,
            int phoneNumber) throws IllegalArgumentException {
        super(id);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;

        this.validate();
    }

    // Validate the Manufacturer entries format
    public void validate() throws IllegalArgumentException {
        checkInRange("Manufacturer ID", this.id, 1, 99);
        checkNonEmptyString("Manufacturer Name", this.name);
        checkNonEmptyString("Manufacturer Address", this.address);
        checkInRange("Manufacturer Phone Number", this.phoneNumber, 10000000, 99999999);

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static Manufacturer parseString(String rawString) throws IllegalArgumentException {
        String[] record = rawString.split("\t");
        checkRecordLength(record.length, 4);
        return new Manufacturer(
                Integer.parseInt(record[0]),
                record[1],
                record[2],
                Integer.parseInt(record[3]));
    }
}
