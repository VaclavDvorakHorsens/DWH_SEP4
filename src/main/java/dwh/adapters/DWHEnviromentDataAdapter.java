package dwh.adapters;

import dwh.models.EnvironmentalValues;
import dwh.models.Forecast;
import dwh.models.Date;

import java.util.List;

public interface DWHEnviromentDataAdapter {
    EnvironmentalValues getLatestEnvironmentalValue();
    List<EnvironmentalValues> getEnvironmentalValuesFromDatabaseGivenDate(Date beginDate, Date endDate);
    void setAction(int action);
    int getAction();
    Forecast getForecast(Date date);
    int getAverageNumberOfPeople();

}
