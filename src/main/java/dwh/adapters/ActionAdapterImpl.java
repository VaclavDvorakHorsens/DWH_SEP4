package dwh.adapters;

import bridgeApp.dbconnection.DbConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ActionAdapterImpl implements ActionAdapter {

    private DbConnectionManager dbConnectionManager;

    public ActionAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
    }

    @Override
    public void setAction(int action) {
        dbConnectionManager.openConnectionToSourceDatabase();
        String sqlInsert = "INSERT INTO source_Action_Device_Log" +
                " (Device_ID,Action_ID,dateAndTime) VALUES (?,?,?);";
        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlInsert);
        try {
            preparedStatement.setString(1, String.valueOf(1));
            preparedStatement.setString(2, String.valueOf(action));
            preparedStatement.setString(3, String.valueOf(new Timestamp(System.currentTimeMillis())));

            dbConnectionManager.addToDatabase(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbConnectionManager.closeConnectionToDatabase();
    }

    /*Method not used */
    /*
    @Override
    public int getActionLog() {

        dbConnectionManager.openConnectionToSourceDatabase();
        String sqlGet ="SELECT TOP 1  Device_ID,Action_ID,dateAndTime FROM source_Action_Device_Log" ;
        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlGet);
        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);
        dbConnectionManager.closeConnectionToDatabase();
        int device = (int) read.get(0)[0];
        int shaftCurrentPos = (int) read.get(0)[1];
        Date testDate= (Date)read.get(0)[2];
        System.out.println("device " + device + " Shaft " + shaftCurrentPos + "Date " + testDate);
        return shaftCurrentPos;
    }
*/
}
