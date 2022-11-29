package model;

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
}
