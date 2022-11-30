package dao;

import java.sql.Date;
import java.sql.SQLException;

import model.ColumnKey;

public interface Dao<T> {
    public String getQuerySuffix();

    public void addQuerySuffix(String suffix);

    public Date getCurrentDate() throws SQLException;

    public int getNewPrimaryKey(ColumnKey column, String table) throws SQLException;

    public enum OrderDirection {
        ASC,
        DESC
    }

    public T orderBy(ColumnKey column, OrderDirection direction);

    public T where(ColumnKey column);

    public T equals(String value);

    public T like(String value);
}
