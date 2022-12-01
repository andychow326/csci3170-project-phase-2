package model;

public class Salesperson extends BaseModel {
    protected String name;
    protected String address;
    protected int phoneNumber;
    protected int experience;

    public static final String TABLE_NAME = "salesperson";

    public enum ColumnKey implements BaseModel.ColumnKey {
        ID("sID"),
        NAME("sName"),
        ADDRESS("sAddress"),
        PHONE_NUMBER("sPhoneNumber"),
        EXPERIENCE("sExperience");

        private final String key;

        ColumnKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return Salesperson.TABLE_NAME + "." + key;
        }
    }

    public Salesperson() {
        super();
    }

    public Salesperson(
            int id,
            String name,
            String address,
            int phoneNumber,
            int experience) throws IllegalArgumentException {
        super(id);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.experience = experience;

        this.validate();
    }

    // Validate the Salesperson entries format
    public void validate() throws IllegalArgumentException {
        checkInRange("Salesperson ID", id, 1, 99);
        checkNonEmptyString("Salesperson Name", name);
        checkNonEmptyString("Salesperson Address", address);
        checkInRange("Salesperson Phone Number", phoneNumber, 10000000, 99999999);
        checkInRange("Salesperson Experience", experience, 1, 9);

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

    public static Salesperson parseString(String rawString) throws IllegalArgumentException {
        String[] record = rawString.split("\t");
        checkRecordLength(record.length, 5);
        return new Salesperson(
                Integer.parseInt(record[0]),
                record[1],
                record[2],
                Integer.parseInt(record[3]),
                Integer.parseInt(record[4]));
    }
}
