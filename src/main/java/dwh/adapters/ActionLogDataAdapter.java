package dwh.adapters;

import dwh.models.ActionLog;

import java.util.Date;
import java.util.List;

public interface ActionLogDataAdapter {

    void addActionLogToDB(ActionLog actionLog);
    List<ActionLog> getActionLogsFromDBGivenDateAndDevice(int device, Date beginDate, Date endDate);


}
