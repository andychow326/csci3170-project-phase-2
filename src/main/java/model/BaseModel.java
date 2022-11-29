package model;

import java.text.SimpleDateFormat;

public class BaseModel {
    protected int id;

    public BaseModel() {
    }

    public BaseModel(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy");
    }
}
