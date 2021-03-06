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
import java.util.Calendar;
import java.util.List;

public class DWHEnvironmentDataAdapterImpl implements DWHEnvironmentDataAdapter {

    private DbConnectionManager dbConnectionManager;

    public DWHEnvironmentDataAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
    }

    @Override
    public EnvironmentalValues getLatestEnvironmentalValue() {

        dbConnectionManager.openConnectionToDWHDatabase();

        String sqlGet ="SELECT TOP 1 CO2_value,CO2_sensor,Humidity_value,Humidity_sensor,Temp_value,Temp_sensor, Passangers_value, " +
                "Passangers_sensor,DateTime FROM f_EnvironmentalValues_View ORDER BY DateTime DESC;" ;

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(sqlGet);

        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);

        dbConnectionManager.closeConnectionToDatabase();

        int CO2_value = (int) Math.round((Double) read.get(0)[0]);
        int CO2_sensor = (int) read.get(0)[1];
        int humidity_value = (int) Math.round((Double) read.get(0)[2]);
        int humidity_sensor = (int) read.get(0)[3];
        int temperature_value = (int) Math.round((Double) read.get(0)[4]);
        int temperature_sensor = (int) read.get(0)[5];
        int passenger_value = (int) read.get(0)[6];
        int passenger_sensor = (int) read.get(0)[7];
        String test=(String)read.get(0)[8];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        java.util.Date testDate= null;
        try {
            testDate = format.parse(test);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new EnvironmentalValues(CO2_value, CO2_sensor, humidity_value, humidity_sensor, temperature_value,
                temperature_sensor, passenger_value, passenger_sensor, testDate);
    }

/*Method not used */
/*
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
        dbConnectionManager.closeConnectionToDatabase();

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
            java.util.Date date = (java.util.Date ) read.get(i)[8];

            EnvironmentalValues object = new EnvironmentalValues(CO2_value, CO2_sensor, humidity_value, humidity_sensor, temperature_value,
                    temperature_sensor, passenger_value, passenger_sensor, date);

            values.add(object);
        }

        return values;

    }
    */


    @Override
    public Forecast getForecast(Date date) {

        dbConnectionManager.openConnectionToDWHDatabase();

        String query = "";
        try {
            query = new String(Files.readAllBytes(Paths.get("SQL_scripts/8_SCRIPT_FORECAST_VALUES.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sqlDate = constructDate(date);
        String replace = query.replaceAll(":myDate", sqlDate);

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(replace);
        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);
        dbConnectionManager.closeConnectionToDatabase();

        if(read.size() > 0 ) {

            for(int i = 0; i < 12; i++)
            {
                if(read.get(0)[i] == null)
                {
                    read.get(0)[i] = -1.0;
                }
            }

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

            for(int i = 12; i < 16; i++)
            {
                if(read.get(0)[i] == null)
                {
                    read.get(0)[i] = -1;
                }
            }
               int avgPassengers_7to9 = (int) read.get(0)[12];
               int avgPassengers_11to13 = (int) read.get(0)[13];
               int avgPassengers_15to17 = (int) read.get(0)[14];
               int avgPassengers_19to21 = (int) read.get(0)[15];

                Forecast forecast = new Forecast();
                forecast.setCO2Forecast(avgCO2_7to9, avgCO2_11to13, avgCO2_15to17, avgCO2_19to21);
                forecast.setHumidityForecast(avgHumidity_7to9, avgHumidity_11to13, avgHumidity_15to17, avgHumidity_19to21);
                forecast.setTemperatureForecast(avgTemperature_7to9, avgTemperature_11to13, avgTemperature_15to17, avgTemperature_19to21);
                forecast.setNumberOfPassengersForecast(avgPassengers_7to9, avgPassengers_11to13, avgPassengers_15to17, avgPassengers_19to21);

                return forecast;
        }

            return null;

    }

    @Override
    public int getAverageNumberOfPeople() {
        dbConnectionManager.openConnectionToDWHDatabase();

        String query = "";
        try {
            query = new String(Files.readAllBytes(Paths.get("SQL_scripts/9_SCRIPT_AVERAGE_NUM_PASSANGERS.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        String sqlDate = constructDate(new Date(day, month, year));

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if(hour == 0)
        {
            hour = 23;
        }
        else
        {
            hour -= 1;
        }

        String replace = query.replaceAll(":myDate", sqlDate).replaceAll(":myHour", String.valueOf(hour));

        PreparedStatement preparedStatement = dbConnectionManager.getPreparedStatement(replace);
        ArrayList<Object[]> read = dbConnectionManager.retrieveFromDatabase(preparedStatement);

        dbConnectionManager.closeConnectionToDatabase();
        if(read.size() > 0)
        {
            if(read.get(0)[0] == null)
            {
                return 9999999;
            }
            else {
                return (int) read.get(0)[0];
            }
        }

        return 9999999;
    }

    private String constructDate(Date date)
    {
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

        return year + "-" + month + "-" + day;
    }

}
