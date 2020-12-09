package dwh.adapters;

import dwh.models.EnvironmentalValues;

import java.sql.Date;
import java.util.List;

public interface DWHEnviromentDataAdapter {
    EnvironmentalValues getLatestEnvironmentalValue();
    List<EnvironmentalValues> getEnvironmentalValuesFromDatabaseGivenDate(java.sql.Date beginDate, Date endDate);
    void setAction(int action);
    int getAction();
}
