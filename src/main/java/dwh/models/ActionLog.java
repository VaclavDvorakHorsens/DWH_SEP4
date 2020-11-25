package dwh.models;

import java.util.Date;

public class ActionLog {

    private int device_ID;
    private int action_ID;
    private float valueSetInDevice;
    private Date dateAndTime;

    public ActionLog(int device_ID, int action_ID, float valueSetInDevice, Date dateAndTime) {
        this.device_ID = device_ID;
        this.action_ID = action_ID;
        this.valueSetInDevice = valueSetInDevice;
        this.dateAndTime = dateAndTime;
    }

    public void setDevice_ID(int device_ID) {
        this.device_ID = device_ID;
    }

    public void setAction_ID(int action_ID) {
        this.action_ID = action_ID;
    }

    public void setValueSetInDevice(float valueSetInDevice) {
        this.valueSetInDevice = valueSetInDevice;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getDevice_ID() {
        return device_ID;
    }

    public int getAction_ID() {
        return action_ID;
    }

    public float getValueSetInDevice() {
        return valueSetInDevice;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }
}
