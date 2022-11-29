package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {
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

    @Override
    public void orderBy(String column, OrderDirection direction) {
        this.addQuerySuffix("ORDER BY");
        this.addQuerySuffix(column);
        this.addQuerySuffix(direction.name());
    }
}
