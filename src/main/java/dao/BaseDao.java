package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T> {
    public PreparedStatement getAddStatement() throws SQLException;

    public int add(T t) throws SQLException;

    public int[] addAll(List<T> ts) throws SQLException;

    public void delete(int id) throws SQLException;

    public T get(int id) throws SQLException;

    public List<T> getAll() throws SQLException;

    public void update(T t) throws SQLException;
}
