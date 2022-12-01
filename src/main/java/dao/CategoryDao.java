package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Category;

public class CategoryDao extends DaoImpl<CategoryDao> implements BaseDao<Category> {
    public CategoryDao(Connection conn) {
        super(conn);
    }

    @Override
    public PreparedStatement getAddStatement() throws SQLException {
        String query = "INSERT INTO category (cID, cName) VALUES (?, ?)";
        return conn.prepareStatement(query);
    }

    private PreparedStatement addCategory(PreparedStatement ps, Category category) throws SQLException {
        ps.setInt(1, category.getID());
        ps.setString(2, category.getName());
        return ps;
    }

    @Override
    public int add(Category category) throws SQLException {
        PreparedStatement ps = getAddStatement();
        return addCategory(ps, category).executeUpdate();
    }

    @Override
    public int[] addAll(List<Category> categories) throws SQLException {
        PreparedStatement ps = getAddStatement();
        for (Category category : categories) {
            ps = addCategory(ps, category);
            ps.addBatch();
        }
        return ps.executeBatch();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM category WHERE cID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Category get(int id) throws SQLException {
        String query = "SELECT * FROM category WHERE cID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Category category = new Category();
        if (rs.next()) {
            category.setID(rs.getInt("cID"));
            category.setName(rs.getString("cName"));
            return category;
        }

        return null;
    }

    @Override
    public List<Category> getAll() throws SQLException {
        String query = "SELECT * FROM category " + getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<Category> categories = new ArrayList<Category>();

        while (rs.next()) {
            Category category = new Category();
            category.setID(rs.getInt("cID"));
            category.setName(rs.getString("cName"));
            categories.add(category);
        }

        return categories;
    }

    @Override
    public void update(Category category) throws SQLException {
        String query = "UPDATE category SET cName =? WHERE cID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, category.getName());
        ps.setInt(2, category.getID());
        ps.executeUpdate();
    }
}
