package dwh.adapters;

import bridgeApp.dbconnection.DbConnectionManager;
import dwh.models.ActionLog;

import java.util.Date;
import java.util.List;

public class ActionLogDataAdapterImpl implements ActionLogDataAdapter {

    private DbConnectionManager dbConnectionManager;

    public ActionLogDataAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
    }
//TODO split into source and dwh
    @Override
    public void addActionLogToDB(ActionLog actionLog) {

    }

    @Override
    public List<ActionLog> getActionLogsFromDBGivenDateAndDevice(int device, Date beginDate, Date endDate) {
        return null;
    }
}
