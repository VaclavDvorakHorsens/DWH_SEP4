package bridgeApp.dbconnection;

import java.sql.*;
import java.util.ArrayList;

public class DbConnectionManager {


    String url = "jdbc:sqlserver://";
    String driver = "35.228.243.131 ;";
    String sourceDatabase = "databaseName=copenhagenmetro";
    String dataWarehouse = "databaseName=copenhagenmetro_dwh";
    String username = "admin";
    String password = "admin";
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;


    /**
     * Open the connection to the source database.
     */
    public void openConnectionToSourceDatabase() {
        {
            try {
                connection = DriverManager.getConnection(url + driver + sourceDatabase, username, password);
            //    System.out.println("Connected to database server.");

            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Open the connection to the data warehouse.
     */
    public void openConnectionToDWHDatabase() {
        {
            try {
                connection = DriverManager.getConnection(url + driver + dataWarehouse, username, password);
             //   System.out.println("Connected to database server.");

            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Close the database connection.
     */
    public void closeConnectionToDatabase()
    {
        try {
            connection.close();
         //   System.out.println("Close connection to database server");
        } catch (SQLException e) {
            System.out.println("Closing failed");
            e.printStackTrace();
        }
    }

    /**
     * Get the PreparedStatement from the connection.
     * @param sql a String for containing SQL statements.
     * @return a PreparedStatement.
     */
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

    /**
     * Execute the PreparedStatement
     * @param preparedStatement
     */
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

    /**
     * Execute the PreparedStatement and retrieve a ResultSet.
     * @param preparedStatement
     * @return an ArrayList<object[]>
     */
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
