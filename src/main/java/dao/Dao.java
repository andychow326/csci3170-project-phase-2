package dao;

import java.sql.Date;
import java.sql.SQLException;

import model.BaseColumnKey;

public interface Dao<T> {
    public String getQuerySuffix();

    public void addQuerySuffix(String suffix);

    public Date getCurrentDate() throws SQLException;

    public int getNewPrimaryKey(BaseColumnKey column, String table) throws SQLException;

    public enum OrderDirection {
        ASC,
        DESC
    }

    public T orderBy(BaseColumnKey column, OrderDirection direction);

    public T where(BaseColumnKey column);

    public T equals(String value);

    public T like(String value);

    public T limit(int value);
}
