package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.BaseModel;

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

    @Override
    public Date getCurrentDate() throws SQLException {
        String query = "SELECT CURDATE()";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getDate(1);
    }

    @Override
    public int getNewPrimaryKey(BaseModel.ColumnKey column, String table) throws SQLException {
        String query = "SELECT MAX(" + column.toString() + ") FROM " + table;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        return rs.getInt(1) + 1;
    }

    // The unchecked warning is ignored assuming casting is valid
    @SuppressWarnings("unchecked")
    @Override
    public T orderBy(BaseModel.ColumnKey column, OrderDirection direction) {
        this.addQuerySuffix("ORDER BY");
        this.addQuerySuffix(column.toString());
        this.addQuerySuffix(direction.name());
        return (T) this;
    }

    // The unchecked warning is ignored assuming casting is valid
    @SuppressWarnings("unchecked")
    @Override
    public T where(BaseModel.ColumnKey column) {
        this.addQuerySuffix("WHERE");
        this.addQuerySuffix(column.toString());
        return (T) this;
    }

    // The unchecked warning is ignored assuming casting is valid
    @SuppressWarnings("unchecked")
    @Override
    public T equals(String value) {
        this.addQuerySuffix("=");
        this.addQuerySuffix("'" + value + "'");
        return (T) this;
    }

    // The unchecked warning is ignored assuming casting is valid
    @SuppressWarnings("unchecked")
    @Override
    public T like(String value) {
        this.addQuerySuffix("LIKE");
        this.addQuerySuffix("'%" + value + "%'");
        return (T) this;
    }

    // The unchecked warning is ignored assuming casting is valid
    @SuppressWarnings("unchecked")
    @Override
    public T limit(int value) {
        this.addQuerySuffix("LIMIT");
        this.addQuerySuffix(Integer.toString(value));
        return (T) this;
    }
}
