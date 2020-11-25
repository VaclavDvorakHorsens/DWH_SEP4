package dwh.adapters;

import dwh.dbconnection.DbConnectionManager;
import dwh.models.EnvironmentalValues;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnvironmentDataAdapterImpl implements EnvironmentDataAdapter {

    private DbConnectionManager dbConnectionManager;

    public EnvironmentDataAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
    }

    /*  CONNECT TO THE SOURCE DATABASE - change later */
    @Override
    public void addEnvironmentalValuesToDB(EnvironmentalValues environmentalValues) {
        dbConnectionManager.openConnectionToDatabase();

        String sqlInsert = "INSERT INTO source_EnvironmentalValues" +
                "(CO2_value, CO2_sensorID, humidity_value, humidity_Sensor_ID, temperature_value, temperature_Sensor_ID," +
                "numberOfPassengers_value, numberOfPassengers_Sensor_ID, dateAndTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlInsert);
        try {
            preparedStatement.setString(1, String.valueOf(environmentalValues.getCO2_value()));
            preparedStatement.setString(2, String.valueOf(environmentalValues.getCO2_Sensor_ID()));
            preparedStatement.setString(3, String.valueOf(environmentalValues.getHumidity_value()));
            preparedStatement.setString(4, String.valueOf(environmentalValues.getHumidity_Sensor_ID()));
            preparedStatement.setString(5, String.valueOf(environmentalValues.getTemperature_value()));
            preparedStatement.setString(6, String.valueOf(environmentalValues.getTemperature_Sensor_ID()));
            preparedStatement.setString(7, String.valueOf(environmentalValues.getNumberOfPassengers_value()));
            preparedStatement.setString(8, String.valueOf(environmentalValues.getNumberOfPassengers_Sensor_ID()));
            preparedStatement.setString(9, String.valueOf(environmentalValues.getDateAndTime()));

            dbConnectionManager.addToDatabase(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbConnectionManager.closeConnectionToDatabase();
    }

    /*  CONNECT TO THE VIEW - change later */
    @Override
    public EnvironmentalValues getLatestEnvironmentalValue() {

        dbConnectionManager.openConnectionToDatabase();

        String sqlGet = "SELECT TOP 1 (CO2_value, CO2_sensor, humidity_value, humidity_sensor, temperature_value, temperature_sensor," +
                "passengers_value, passengers_sensor, DateTime) FROM f_Environmental_View ORDER BY DateTime DESC;";

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlGet);

        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);

        int CO2_value = (int) read.get(0)[0];
        int CO2_sensor = (int) read.get(0)[1];
        int humidity_value = (int) read.get(0)[2];
        int humidity_sensor = (int) read.get(0)[3];
        int temperature_value = (int) read.get(0)[4];
        int temperature_sensor = (int) read.get(0)[5];
        int passenger_value = (int) read.get(0)[6];
        int passenger_sensor = (int) read.get(0)[7];
        Date date = (Date) read.get(0)[8];

        return new EnvironmentalValues(CO2_value, CO2_sensor, humidity_value, humidity_sensor, temperature_value,
                temperature_sensor, passenger_value, passenger_sensor, date);
    }

    /*  CONNECT TO THE VIEW - change later */
    @Override
    public List<EnvironmentalValues> getEnvironmentalValuesFromDatabaseGivenDate(Date beginDate, Date endDate) {

        dbConnectionManager.openConnectionToDatabase();

        List<EnvironmentalValues> values = new ArrayList<>();

        String sqlGet = "SELECT (CO2_value, CO2_sensor, humidity_value, humidity_sensor, temperature_value, temperature_sensor" +
                "passengers_value, passengers_sensor, DateTime) " +
                "FROM f_Environmental_View WHERE Datetime >= (?) AND Datetime <= (?);";

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlGet);
        try {
            preparedStatement.setString(1, String.valueOf(beginDate));
            preparedStatement.setString(2, String.valueOf(endDate));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);

        for(int i = 0; i < read.size(); i++)
        {
            int CO2_value = (int) read.get(i)[0];
            int CO2_sensor = (int) read.get(i)[1];
            int humidity_value = (int) read.get(i)[2];
            int humidity_sensor = (int) read.get(i)[3];
            int temperature_value = (int) read.get(i)[4];
            int temperature_sensor = (int) read.get(i)[5];
            int passenger_value = (int) read.get(i)[6];
            int passenger_sensor = (int) read.get(i)[7];
            Date date = (Date) read.get(i)[8];

            EnvironmentalValues object = new EnvironmentalValues(CO2_value, CO2_sensor, humidity_value, humidity_sensor, temperature_value,
                    temperature_sensor, passenger_value, passenger_sensor, date);

            values.add(object);
        }
        dbConnectionManager.closeConnectionToDatabase();

        return values;

    }


}
