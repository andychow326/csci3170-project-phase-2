package dao;

import java.sql.Date;
import java.sql.SQLException;

import model.BaseModel;

public interface Dao<T> {
    public String getQuerySuffix();

    public void addQuerySuffix(String suffix);

    public Date getCurrentDate() throws SQLException;

    public int getNewPrimaryKey(BaseModel.ColumnKey column, String table) throws SQLException;

    public enum OrderDirection {
        ASC,
        DESC
    }

    public T orderBy(BaseModel.ColumnKey column, OrderDirection direction);

    public T where(BaseModel.ColumnKey column);

    public T equals(String value);

    public T like(String value);

    public T limit(int value);
}
