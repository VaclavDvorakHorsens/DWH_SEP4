package dwh.adapters;

import dwh.dbconnection.DbConnectionManager;
import dwh.models.ActionLog;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ActionLogDataAdapterImpl implements ActionLogDataAdapter {

    private DbConnectionManager dbConnectionManager;

    public ActionLogDataAdapterImpl() {
        dbConnectionManager = new DbConnectionManager();
    }

    @Override
    public void addActionLogToDB(ActionLog actionLog) {

    }

    @Override
    public List<ActionLog> getActionLogsFromDBGivenDateAndDevice(int device, Date beginDate, Date endDate) {
        return null;
    }
}
