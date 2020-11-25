package dwh.adapters;

import dwh.dbconnection.DbConnectionManager;

public class ActionLogDataAdapterImpl implements ActionLogDataAdapter {

    private DbConnectionManager dbConnectionManager;

    public ActionLogDataAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
    }
}
