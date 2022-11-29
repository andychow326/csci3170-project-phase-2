package model;

import java.sql.Date;
import java.text.ParseException;

public class Transaction extends BaseModel {
    protected int partID;
    protected int salesPersonID;
    protected Date date;

    public static final String TABLE_NAME = "transaction";

    public Transaction() {
        super();
    }

    public Transaction(int id, int partID, int salesPersonID, Date date) {
        super(id);
        this.partID = partID;
        this.salesPersonID = salesPersonID;
        this.date = date;
    }

    public int getPartID() {
        return this.partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public int getSalesPersonID() {
        return this.salesPersonID;
    }

    public void setSalesPersonID(int salesPersonID) {
        this.salesPersonID = salesPersonID;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return getDateFormat().format(this.date);
    }

    public static Transaction parseString(String rawString) throws IllegalArgumentException, ParseException {
        String[] record = rawString.split("\t");
        if (record.length != 4) {
            throw new IllegalArgumentException(
                    "Invalid number of entries, expected: 4, actual: " + record.length);
        }
        return new Transaction(
                Integer.parseInt(record[0]),
                Integer.parseInt(record[1]),
                Integer.parseInt(record[2]),
                new Date(getDateFormat().parse(record[3]).getTime()));
    }
}
