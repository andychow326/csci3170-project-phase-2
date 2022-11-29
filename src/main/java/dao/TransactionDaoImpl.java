package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Transaction;

public class TransactionDaoImpl extends DaoImpl implements TransactionDao {
    public TransactionDaoImpl(Connection conn) {
        super(conn);
    }

    @Override
    public int add(Transaction transaction) throws SQLException {
        String query = "INSERT INTO "
                + "transaction (tID, pID, sID, tDate) "
                + "VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, transaction.getID());
        ps.setInt(2, transaction.getPartID());
        ps.setInt(3, transaction.getSalesPersonID());
        ps.setDate(4, transaction.getDate());
        return ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM transaction WHERE tID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Transaction getTransaction(int id) throws SQLException {
        String query = "SELECT * FROM transaction WHERE tID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Transaction transaction = new Transaction();
        if (rs.next()) {
            transaction.setID(rs.getInt("tID"));
            transaction.setPartID(rs.getInt("pID"));
            transaction.setSalesPersonID(rs.getInt("sID"));
            transaction.setDate(rs.getDate("tDate"));
            return transaction;
        }

        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() throws SQLException {
        String query = "SELECT * FROM transaction";
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<Transaction> transactions = new ArrayList<Transaction>();
        while (rs.next()) {
            Transaction transaction = new Transaction();
            transaction.setID(rs.getInt("tID"));
            transaction.setPartID(rs.getInt("pID"));
            transaction.setSalesPersonID(rs.getInt("sID"));
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
        ps.setInt(2, part.getSalesPersonID());
        ps.setDate(3, part.getDate());
        ps.setInt(4, part.getID());
        ps.executeUpdate();
    }
}
