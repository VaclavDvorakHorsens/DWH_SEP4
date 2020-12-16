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

    /*Method not used */
  //  List<EnvironmentalValues> getEnvironmentalValuesFromDatabaseGivenDate(Date beginDate, Date endDate);


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
