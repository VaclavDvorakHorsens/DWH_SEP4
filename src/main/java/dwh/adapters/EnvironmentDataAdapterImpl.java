package dwh.adapters;

import dwh.dbconnection.DbConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentDataAdapterImpl implements EnvironmentDataAdapter {

    private DbConnectionManager dbConnectionManager;

    public EnvironmentDataAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
    }

    @Override
    public void addValuesToDatabase(String value) {
        dbConnectionManager.openConnectionToDatabase();

        String sqlInsert = "INSERT INTO dbo.String_Values (stringValue) VALUES(?)";

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlInsert);
        try {
            preparedStatement.setString(1, value);

            dbConnectionManager.addToDatabase(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbConnectionManager.closeConnectionToDatabase();
    }

    @Override
    public /*List<String>*/String getValuesFromDatabase() {
        dbConnectionManager.openConnectionToDatabase();

        List<String> values = new ArrayList<>();

        String sqlGet = "select stringValue FROM dbo.String_Values";

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlGet);
        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);

        for(int i = 0; i < read.size(); i++)
        {
            values.add((String) read.get(i)[0]);
        }
        dbConnectionManager.closeConnectionToDatabase();

        return values.get(values.size()-1);
    }
}
