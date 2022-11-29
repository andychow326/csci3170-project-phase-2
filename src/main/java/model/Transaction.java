package model;

import java.sql.Date;

public class Transaction extends BaseModel {
    protected int partID;
    protected int salesPersonID;
    protected Date date;

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
}
