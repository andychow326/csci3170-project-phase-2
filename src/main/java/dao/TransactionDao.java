package dao;

import java.sql.SQLException;
import java.util.List;

import model.Transaction;

public interface TransactionDao {
    public int add(Transaction transaction) throws SQLException;

    public void delete(int id) throws SQLException;

    public Transaction getTransaction(int id) throws SQLException;

    public List<Transaction> getAllTransactions() throws SQLException;

    public void update(Transaction transaction) throws SQLException;
}
