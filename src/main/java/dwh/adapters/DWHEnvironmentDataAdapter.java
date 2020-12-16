package dwh.adapters;

import dwh.models.EnvironmentalValues;
import dwh.models.Forecast;
import dwh.models.Date;

import java.util.List;

public interface DWHEnvironmentDataAdapter {

    /**
     * Retrieve the latest environmental data values from the database.
     * @return EnvironmentalValues object
     */
    EnvironmentalValues getLatestEnvironmentalValue();
  //  List<EnvironmentalValues> getEnvironmentalValuesFromDatabaseGivenDate(Date beginDate, Date endDate);

    /**
     *Save the action into the database: for action logs.
     * @param action an integer value that will be inserted into the database. This value represents the state of the shaft that the android application has sent
     */
    void setAction(int action);


 //   int getActionLog();

    /**
     * Retrieve a forecast from the database for given date. (Averages of environmental values for specific time periods.)
     * @param date given Date
     * @return Forecast object
     */
    Forecast getForecast(Date date);

    /**
     * Retrieve the average number of people in the station from the last hour.
     * @return an integer value
     */
    int getAverageNumberOfPeople();

}
