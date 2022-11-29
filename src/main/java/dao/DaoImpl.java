package dao;

import java.sql.Connection;

public class DaoImpl {
    protected Connection conn;

    public DaoImpl(Connection conn) {
        this.conn = conn;
    }
}
