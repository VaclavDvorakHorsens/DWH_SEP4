package bridgeApp;

import dwh.models.EnvironmentalValues;

import java.sql.Date;
import java.util.List;

public interface EnvironmentDataAdapter {

   void addEnvironmentalValuesToDB(EnvironmentalValues environmentalValues);


}
