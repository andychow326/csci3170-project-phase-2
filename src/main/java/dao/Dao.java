package dao;

public interface Dao {
    public String getQuerySuffix();

    public void addQuerySuffix(String suffix);

    public enum OrderDirection {
        ASC,
        DESC
    }

    public void orderBy(String column, OrderDirection direction);
}
