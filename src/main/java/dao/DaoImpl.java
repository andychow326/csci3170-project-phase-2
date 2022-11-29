package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import model.ColumnKey;

public class DaoImpl<T> implements Dao<T> {
    protected Connection conn;
    protected List<String> querySuffix = new ArrayList<String>();

    public DaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getQuerySuffix() {
        return String.join(" ", this.querySuffix);
    }

    @Override
    public void addQuerySuffix(String suffix) {
        this.querySuffix.add(suffix);
    }

    // The unchecked warning is ignored assuming casting is valid
    @SuppressWarnings("unchecked")
    @Override
    public T orderBy(ColumnKey column, OrderDirection direction) {
        this.addQuerySuffix("ORDER BY");
        this.addQuerySuffix(column.toString());
        this.addQuerySuffix(direction.name());
        return (T) this;
    }
}
