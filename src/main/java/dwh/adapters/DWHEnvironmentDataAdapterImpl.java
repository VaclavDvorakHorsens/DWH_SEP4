package dwh.adapters;

import bridgeApp.dbconnection.DbConnectionManager;
import dwh.models.EnvironmentalValues;
import dwh.models.Forecast;
import dwh.models.Date;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DWHEnvironmentDataAdapterImpl implements DWHEnviromentDataAdapter {

    private DbConnectionManager dbConnectionManager;

    public DWHEnvironmentDataAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
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
            java.util.Date date = (java.util.Date) read.get(i)[8];

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

    @Override
    public Forecast getForecast(Date date) {

        dbConnectionManager.openConnectionToDWHDatabase();

        String query = "";
        try {
            query = new String(Files.readAllBytes(Paths.get("SQL_scripts/8_SCRIPT_FORECAST_VALUES.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String day = date.getDay() + "";
        String month = date.getMonth() + "";
        String year = date.getYear() + "";

        if(date.getDay() <= 9)
        {
             day = "0" + date.getDay();
        }

         if(date.getMonth() <= 9)
        {
            month = "0" + date.getMonth();
        }

        String replace = query.replaceAll(":myDate", year+ "-" + month + "-" + day);

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(replace);

        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);

        if(read.size() <= 0 || read.get(0)[0] != null) {

            int avgCO2_7to9 = (int) Math.round((Double) read.get(0)[0]);
            int avgCO2_11to13 = (int) Math.round((Double) read.get(0)[1]);
            int avgCO2_15to17 = (int) Math.round((Double) read.get(0)[2]);
            int avgCO2_19to21 = (int) Math.round((Double) read.get(0)[3]);

            int avgHumidity_7to9 = (int) Math.round((Double) read.get(0)[4]);
            int avgHumidity_11to13 = (int) Math.round((Double) read.get(0)[5]);
            int avgHumidity_15to17 = (int) Math.round((Double) read.get(0)[6]);
            int avgHumidity_19to21 = (int) Math.round((Double) read.get(0)[7]);

            int avgTemperature_7to9 = (int) Math.round((Double) read.get(0)[8]);
            int avgTemperature_11to13 = (int) Math.round((Double) read.get(0)[9]);
            int avgTemperature_15to17 = (int) Math.round((Double) read.get(0)[10]);
            int avgTemperature_19to21 = (int) Math.round((Double) read.get(0)[11]);

            Forecast forecast = new Forecast();
            forecast.setCO2Forecast(avgCO2_7to9, avgCO2_11to13, avgCO2_15to17, avgCO2_19to21);
            forecast.setHumidityForecast(avgHumidity_7to9, avgHumidity_11to13, avgHumidity_15to17, avgHumidity_19to21);
            forecast.setTemperatureForecast(avgTemperature_7to9, avgTemperature_11to13, avgTemperature_15to17, avgTemperature_19to21);

            dbConnectionManager.closeConnectionToDatabase();

            System.out.println(forecast.toString());
            return forecast;
        }
        else {
            dbConnectionManager.closeConnectionToDatabase();
            return null;
        }
    }

}
