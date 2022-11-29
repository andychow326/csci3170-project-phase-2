package dao;

import java.sql.SQLException;
import java.util.List;

import model.Category;

public interface CategoryDao {
    public int add(Category category) throws SQLException;

    public void delete(int id) throws SQLException;

    public Category getCategory(int id) throws SQLException;

    public List<Category> getAllCategories() throws SQLException;

    public void update(Category category) throws SQLException;
}
