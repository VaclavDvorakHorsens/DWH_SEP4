package dwh.adapters;

import dwh.dbconnection.DbConnectionManager;
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
        if(!(environmentalValues.getCO2_value() <= 0 || environmentalValues.getHumidity_value() <= 0 || environmentalValues.getTemperature_value() <= 0))
        {
            dbConnectionManager.openConnectionToSourceDatabase();

            String sqlInsert = "INSERT INTO dbo.source_EnvironmentValues" +
                    "(CO2_value, CO2_sensor_ID, humidity_value, humidity_Sensor_ID, temperature_value, temperature_Sensor_ID," +
                    "numberOfPassengers_value, numberOfPassengers_Sensor_ID, dateAndTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlInsert);
            try {
                preparedStatement.setString(1, String.valueOf(environmentalValues.getCO2_value()));
                preparedStatement.setString(2, String.valueOf(1));
                preparedStatement.setString(3, String.valueOf(environmentalValues.getHumidity_value()));
                preparedStatement.setString(4, String.valueOf(2));
                preparedStatement.setString(5, String.valueOf(environmentalValues.getTemperature_value()));
                preparedStatement.setString(6, String.valueOf(3));
                preparedStatement.setString(7, String.valueOf(environmentalValues.getNumberOfPassengers_value()));
                preparedStatement.setString(8, String.valueOf(4));
                preparedStatement.setString(9, String.valueOf(new Timestamp(System.currentTimeMillis())));

                dbConnectionManager.addToDatabase(preparedStatement);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            dbConnectionManager.closeConnectionToDatabase();
        }

    }

    /*  CONNECT TO THE VIEW */
    @Override
    public EnvironmentalValues getLatestEnvironmentalValue() {

        dbConnectionManager.openConnectionToDWHDatabase();

        String sqlGet ="SELECT TOP 1 CO2_value,CO2_sensor,Humidity_value,Humidity_sensor,Temp_value,Temp_sensor, Passangers_value, " +
                "Passangers_sensor,DateTime FROM f_EnvironmentalValues_View ORDER BY DateTime DESC;" ;
        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlGet);

        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);
        dbConnectionManager.closeConnectionToDatabase();
        double CO2_value = (double) read.get(0)[0];
        int CO2_sensor = (int) read.get(0)[1];
        double humidity_value = (double) read.get(0)[2];
        int humidity_sensor = (int) read.get(0)[3];
        double temperature_value = (double) read.get(0)[4];
        int temperature_sensor = (int) read.get(0)[5];
        double passenger_value = (double) read.get(0)[6];
        int passenger_sensor = (int) read.get(0)[7];
        String test=(String)read.get(0)[8];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

       java.util.Date testDate= null;
        try {
            testDate = format.parse(test);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new EnvironmentalValues((int)CO2_value, CO2_sensor, (int)humidity_value, humidity_sensor, (int)temperature_value,
                temperature_sensor, (int)passenger_value, passenger_sensor, testDate);
    }

    /*  CONNECT TO THE VIEW */
    @Override
    public List<EnvironmentalValues> getEnvironmentalValuesFromDatabaseGivenDate(Date beginDate, Date endDate) {

        dbConnectionManager.openConnectionToDWHDatabase();

        List<EnvironmentalValues> values = new ArrayList<>();

        String sqlGet = " SELECT CO2_value,CO2_sensor,Humidity_value,Humidity_sensor,Temp_value,Temp_sensor, Passangers_value,Passangers_sensor,DateTime FROM f_EnvironmentalValues_View WHERE Datetime >= (?) AND Datetime <= (?);";

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


    /**
     *
     * @param action an integer value that will be inserted into the database. This value represents the state of the shaft that the android application has sent
     */
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

    @Override
    public int getAction() {

        dbConnectionManager.openConnectionToDWHDatabase();
        String sqlGet ="SELECT TOP 1 shaftCurrent FROM source_Action_Device_Log" ;
        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlGet);
        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);
        dbConnectionManager.closeConnectionToDatabase();
        int shaftCurrentPos = (int) read.get(0)[0];
        return shaftCurrentPos;
    }
}
