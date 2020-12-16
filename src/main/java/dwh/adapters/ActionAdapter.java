package dwh.adapters;

public interface ActionAdapter {

    /**
     *Save the action into the database: for action logs.
     * @param action an integer value that will be inserted into the database. This value represents the state of the shaft that the android application has sent
     */
    void setAction(int action);

    /*Method not used */
    //   int getActionLog();
}
