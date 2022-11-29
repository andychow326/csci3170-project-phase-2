package model;

public class Category extends BaseModel {
    protected String name;

    public Category() {
        super();
    }

    public Category(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
