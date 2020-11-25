package dwh.models;

import java.util.Date;

public class EnvironmentalValues {

    private int CO2_value;
    private int CO2_Sensor_ID;

    private int humidity_value;
    private int humidity_Sensor_ID;

    private int temperature_value;
    private int temperature_Sensor_ID;

    private int numberOfPassengers_value;
    private int numberOfPassengers_Sensor_ID;

    private Date dateAndTime;

    public EnvironmentalValues(int CO2_value, int CO2_Sensor_ID, int humidity_value, int humidity_Sensor_ID, int temperature_value, int temperature_Sensor_ID, int numberOfPassengers_value, int numberOfPassengers_Sensor_ID, Date dateAndTime) {
        this.CO2_value = CO2_value;
        this.CO2_Sensor_ID = CO2_Sensor_ID;
        this.humidity_value = humidity_value;
        this.humidity_Sensor_ID = humidity_Sensor_ID;
        this.temperature_value = temperature_value;
        this.temperature_Sensor_ID = temperature_Sensor_ID;
        this.numberOfPassengers_value = numberOfPassengers_value;
        this.numberOfPassengers_Sensor_ID = numberOfPassengers_Sensor_ID;
        this.dateAndTime = dateAndTime;
    }

    public void setCO2_value(int CO2_value) {
        this.CO2_value = CO2_value;
    }

    public void setCO2_Sensor_ID(int CO2_Sensor_ID) {
        this.CO2_Sensor_ID = CO2_Sensor_ID;
    }

    public void setHumidity_value(int humidity_value) {
        this.humidity_value = humidity_value;
    }

    public void setHumidity_Sensor_ID(int humidity_Sensor_ID) {
        this.humidity_Sensor_ID = humidity_Sensor_ID;
    }

    public void setTemperature_value(int temperature_value) {
        this.temperature_value = temperature_value;
    }

    public void setTemperature_Sensor_ID(int temperature_Sensor_ID) {
        this.temperature_Sensor_ID = temperature_Sensor_ID;
    }

    public void setNumberOfPassengers_value(int numberOfPassengers_value) {
        this.numberOfPassengers_value = numberOfPassengers_value;
    }

    public void setNumberOfPassengers_Sensor_ID(int numberOfPassengers_Sensor_ID) {
        this.numberOfPassengers_Sensor_ID = numberOfPassengers_Sensor_ID;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getCO2_value() {
        return CO2_value;
    }

    public int getCO2_Sensor_ID() {
        return CO2_Sensor_ID;
    }

    public int getHumidity_value() {
        return humidity_value;
    }

    public int getHumidity_Sensor_ID() {
        return humidity_Sensor_ID;
    }

    public int getTemperature_value() {
        return temperature_value;
    }

    public int getTemperature_Sensor_ID() {
        return temperature_Sensor_ID;
    }

    public int getNumberOfPassengers_value() {
        return numberOfPassengers_value;
    }

    public int getNumberOfPassengers_Sensor_ID() {
        return numberOfPassengers_Sensor_ID;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }
}
