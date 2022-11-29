package dao;

import model.ColumnKey;

public interface Dao<T> {
    public String getQuerySuffix();

    public void addQuerySuffix(String suffix);

    public enum OrderDirection {
        ASC,
        DESC
    }

    public T orderBy(ColumnKey column, OrderDirection direction);
}
