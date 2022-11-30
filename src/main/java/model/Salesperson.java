package model;

public class Salesperson extends BaseModel {
    protected String name;
    protected String address;
    protected int phoneNumber;
    protected int experience;

    public static final String TABLE_NAME = "salesperson";

    public enum ColumnKey implements BaseColumnKey {
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
            int experience) {
        super(id);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
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
        if (record.length != 5) {
            throw new IllegalArgumentException(
                    "Invalid number of entries, expected: 5, actual: " + record.length);
        }
        return new Salesperson(
                Integer.parseInt(record[0]),
                record[1],
                record[2],
                Integer.parseInt(record[3]),
                Integer.parseInt(record[4]));
    }
}
