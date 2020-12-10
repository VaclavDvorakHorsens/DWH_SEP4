package dwh.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Forecast {
    //CO2 forecast values
    private int co2_8;
    private int co2_12;
    private int co2_16;
    private int co2_20;
    //Humidity forecast
    private int humidity_8;
    private int humidity_12;
    private int humidity_16;
    private int humidity_20;
    //Temperature forecast
    private int temp_8;
    private int temp_12;
    private int temp_16;
    private int temp_20;
    //Number of passengers forecast
    private int numberOfPassengers_8;
    private int numberOfPassengers_12;
    private int numberOfPassengers_16;
    private int numberOfPassengers_20;

    public void setCO2Forecast(int co2_8,int co2_12,int co2_16,int co2_20)
    {
        this.co2_8=co2_8;
        this.co2_12=co2_12;
        this.co2_16=co2_16;
        this.co2_20=co2_20;
    }
    public ArrayList<Integer> getCO2Forecast(){
        ArrayList<Integer> temp= new ArrayList<>();
        temp.add(co2_8);
        temp.add(co2_12);
        temp.add(co2_16);
        temp.add(co2_20);
        return temp;
     }
    public void setHumidityForecast(int humidity_8,int humidity_12,int humidity_16,int humidity_20)
    {
        this.humidity_8=humidity_8;
        this.humidity_12=humidity_12;
        this.humidity_16=humidity_16;
        this.humidity_20=humidity_20;
    }

    public ArrayList<Integer> getHumidityForecast(){
        ArrayList<Integer> temp= new ArrayList<>();
        temp.add(humidity_8);
        temp.add(humidity_12);
        temp.add(humidity_16);
        temp.add(humidity_20);
        return temp;
    }

    public void setTemperatureForecast(int temp_8,int temp_12,int temp_16,int temp_20)
    {
        this.temp_8=temp_8;
        this.temp_12=temp_12;
        this.temp_16=temp_16;
        this.temp_20=temp_20;
    }
    public ArrayList<Integer> getTemperatureForecast(){
        ArrayList<Integer> temp= new ArrayList<>();
        temp.add(temp_8);
        temp.add(temp_12);
        temp.add(temp_16);
        temp.add(temp_20);
        return temp;
    }
    public void setNumberOfPassengersForecast(int numberOfPassengers_8,int numberOfPassengers_12,int numberOfPassengers_16,int numberOfPassengers_20)
    {
        this.numberOfPassengers_8=numberOfPassengers_8;
        this.numberOfPassengers_12=numberOfPassengers_12;
        this.numberOfPassengers_16=numberOfPassengers_16;
        this.numberOfPassengers_20=numberOfPassengers_20;
    }
    public ArrayList<Integer> getNumberOfPassengersForecast(){
        ArrayList<Integer> temp= new ArrayList<>();
        temp.add(numberOfPassengers_8);
        temp.add(numberOfPassengers_12);
        temp.add(numberOfPassengers_16);
        temp.add(numberOfPassengers_20);
        return temp;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "co2_8=" + co2_8 +
                ", co2_12=" + co2_12 +
                ", co2_16=" + co2_16 +
                ", co2_20=" + co2_20 +
                ", humidity_8=" + humidity_8 +
                ", humidity_12=" + humidity_12 +
                ", humidity_16=" + humidity_16 +
                ", humidity_20=" + humidity_20 +
                ", temp_8=" + temp_8 +
                ", temp_12=" + temp_12 +
                ", temp_16=" + temp_16 +
                ", temp_20=" + temp_20 +
                ", numberOfPassengers_8=" + numberOfPassengers_8 +
                ", numberOfPassengers_12=" + numberOfPassengers_12 +
                ", numberOfPassengers_16=" + numberOfPassengers_16 +
                ", numberOfPassengers_20=" + numberOfPassengers_20 +
                '}';
    }
}
