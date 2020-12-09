package bridgeApp;

import bridgeApp.dbconnection.DbConnectionManager;
import dwh.models.EnvironmentalValues;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class EnvironmentDataAdapterImpl implements EnvironmentDataAdapter {

    private DbConnectionManager dbConnectionManager;

    public EnvironmentDataAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
    }
    /*  CONNECT TO THE SOURCE DATABASE */
    @Override
    public void addEnvironmentalValuesToDB(EnvironmentalValues environmentalValues) {
        if(!(environmentalValues.getCO2_value() <= 0 || environmentalValues.getHumidity_value() <= 0))
        {
            dbConnectionManager.openConnectionToSourceDatabase();

            String sqlInsert = "INSERT INTO dbo.source_EnvironmentValues" +
                    "(CO2_value, CO2_sensor_ID, humidity_value, humidity_Sensor_ID, temperature_value, temperature_Sensor_ID," +
                    "numberOfPassengers_value, numberOfPassengers_Sensor_ID, dateAndTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlInsert);
            try {
                preparedStatement.setString(1, String.valueOf(environmentalValues.getCO2_value()));
                preparedStatement.setString(2, String.valueOf(2));
                preparedStatement.setString(3, String.valueOf(environmentalValues.getHumidity_value()));
                preparedStatement.setString(4, String.valueOf(1));
                preparedStatement.setString(5, String.valueOf(environmentalValues.getTemperature_value()));
                preparedStatement.setString(6, String.valueOf(1));
                preparedStatement.setString(7, String.valueOf(environmentalValues.getNumberOfPassengers_value()));
                preparedStatement.setString(8, String.valueOf(3));
                preparedStatement.setString(9, String.valueOf(new Timestamp(System.currentTimeMillis())));

               dbConnectionManager.addToDatabase(preparedStatement);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            dbConnectionManager.closeConnectionToDatabase();
        }

    }





}
