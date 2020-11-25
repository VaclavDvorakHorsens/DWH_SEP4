package dwh.adapters;

import dwh.models.EnvironmentalValues;

import java.util.Date;
import java.util.List;

public interface EnvironmentDataAdapter {

   void addEnvironmentalValuesToDB(EnvironmentalValues environmentalValues);
   EnvironmentalValues getLatestEnvironmentalValue();
   List<EnvironmentalValues> getEnvironmentalValuesFromDatabaseGivenDate(Date beginDate, Date endDate);
}
