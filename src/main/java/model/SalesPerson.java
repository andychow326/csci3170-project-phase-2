package model;

public class SalesPerson extends BaseModel {
    protected String name;
    protected String address;
    protected int phoneNumber;
    protected int experience;

    public static final String TABLE_NAME = "salesperson";

    public SalesPerson() {
        super();
    }

    public SalesPerson(
            int id,
            String name,
            String address,
            int phoneNumber,
            int experience) {
        super(id);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
    }

    public static enum ColumnKey {
        ID("sID"),
        NAME("sName"),
        ADDRESS("sAddress"),
        PHONE_NUMBER("sPhoneNumber");

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

    public int getExperience() {
        return this.experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public static SalesPerson parseString(String rawString) throws IllegalArgumentException {
        String[] record = rawString.split("\t");
        if (record.length != 5) {
            throw new IllegalArgumentException(
                    "Invalid number of entries, expected: 5, actual: " + record.length);
        }
        return new SalesPerson(
                Integer.parseInt(record[0]),
                record[1],
                record[2],
                Integer.parseInt(record[3]),
                Integer.parseInt(record[4]));
    }
}
