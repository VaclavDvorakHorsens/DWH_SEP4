package dwh.dbconnection;

import java.sql.*;
import java.util.ArrayList;

public class DbConnectionManager {

    String url = "jdbc:sqlserver://";
    String driver = "34.66.112.80;";
    String sourceDatabase = "databaseName=copenhagenmetro";
    String dataWarehouse = "databaseName=copenhagenmetro_dwh";
    String username = "sqlserver";
    String password = "admin";
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void openConnectionToSourceDatabase() {
        {
            try {
                connection = DriverManager.getConnection(url + driver + sourceDatabase, username, password);
                System.out.println("Connected to database server.");

            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
        }
    }

    public void openConnectionToDWHDatabase() {
        {
            try {
                connection = DriverManager.getConnection(url + driver + dataWarehouse, username, password);
                System.out.println("Connected to database server.");

            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
        }
    }

    public void closeConnectionToDatabase()
    {
        try {
            connection.close();
            System.out.println("Close connection to database server");
        } catch (SQLException e) {
            System.out.println("Closing failed");
            e.printStackTrace();
        }
    }

    public PreparedStatement getPreparedStatement(String sql)
    {
        try {
            preparedStatement = connection.prepareStatement(sql);

        } catch (SQLException e) {
            System.out.println("Retrieving prepared statement failed.");
            e.printStackTrace();
        }

        return preparedStatement;
    }

    public void addToDatabase(PreparedStatement preparedStatement)
    {
        try {
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Executing statement failed.");
            e.printStackTrace();
        }
    }

    public ArrayList<Object[]> retrieveFromDatabase(PreparedStatement preparedStatement)
    {
        ArrayList<Object[]> results = new ArrayList<>();

        try {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                Object[] row = new Object[resultSet.getMetaData().getColumnCount()];

                for(int i = 0; i < row.length; i++)
                {
                    row[i] = resultSet.getObject(i +1);
                }
                results.add(row);
            }
            preparedStatement.close();
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
