package dwh.adapters;

import java.util.List;

public interface EnvironmentDataAdapter {

    void addValuesToDatabase(String value);
    /*List<String>*/ String getValuesFromDatabase();
}
