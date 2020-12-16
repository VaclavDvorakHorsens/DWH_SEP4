package bridgeApp;

import dwh.models.EnvironmentalValues;

import java.sql.Date;
import java.util.List;

public interface EnvironmentDataAdapter {

   /**
    * Inserts the environmental data values into the source database.
    * @param environmentalValues contains the data required to store
    */
   void addEnvironmentalValuesToDB(EnvironmentalValues environmentalValues);

}
