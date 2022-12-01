package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Transaction;

public class TransactionDao extends DaoImpl<TransactionDao> implements BaseDao<Transaction> {
    public TransactionDao(Connection conn) {
        super(conn);
    }

    @Override
    public PreparedStatement getAddStatement() throws SQLException {
        String query = "INSERT INTO "
                + "transaction (tID, pID, sID, tDate) "
                + "VALUES (?, ?, ?, ?)";
        return conn.prepareStatement(query);
    }

    private PreparedStatement addTransaction(PreparedStatement ps, Transaction transaction) throws SQLException {
        ps.setInt(1, transaction.getID());
        ps.setInt(2, transaction.getPartID());
        ps.setInt(3, transaction.getSalespersonID());
        ps.setDate(4, transaction.getDate());
        return ps;
    }

    @Override
    public int add(Transaction transaction) throws SQLException {
        PreparedStatement ps = getAddStatement();
        return addTransaction(ps, transaction).executeUpdate();
    }

    @Override
    public int[] addAll(List<Transaction> transactions) throws SQLException {
        PreparedStatement ps = getAddStatement();

        for (Transaction transaction : transactions) {
            ps = addTransaction(ps, transaction);
            ps.addBatch();
        }
        return ps.executeBatch();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM transaction WHERE tID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Transaction get(int id) throws SQLException {
        String query = "SELECT * FROM transaction WHERE tID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Transaction transaction = new Transaction();
        if (rs.next()) {
            transaction.setID(rs.getInt("tID"));
            transaction.setPartID(rs.getInt("pID"));
            transaction.setSalespersonID(rs.getInt("sID"));
            transaction.setDate(rs.getDate("tDate"));
            return transaction;
        }

        return null;
    }

    @Override
    public List<Transaction> getAll() throws SQLException {
        String query = "SELECT * FROM transaction " + getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<Transaction> transactions = new ArrayList<Transaction>();
        while (rs.next()) {
            Transaction transaction = new Transaction();
            transaction.setID(rs.getInt("tID"));
            transaction.setPartID(rs.getInt("pID"));
            transaction.setSalespersonID(rs.getInt("sID"));
            transaction.setDate(rs.getDate("tDate"));
            transactions.add(transaction);
        }

        return transactions;
    }

    @Override
    public void update(Transaction part) throws SQLException {
        String query = "UPDATE transaction SET pID =?, sID =?, tDate =? WHERE tID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, part.getPartID());
        ps.setInt(2, part.getSalespersonID());
        ps.setDate(3, part.getDate());
        ps.setInt(4, part.getID());
        ps.executeUpdate();
    }
}
