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

    public void checkInRange(String label, int value, int start, int end) throws IllegalArgumentException {
        if (value < start || value > end) {
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid %s, expected: in range %d to %d (inclusive), actual: %d",
                            label, start, end, value));
        }
    }

    public void checkNonEmptyString(String label, String value) throws IllegalArgumentException {
        if (value.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid %s, expected: non-empty string, actual: %s",
                            label, value));
        }
    }
}
