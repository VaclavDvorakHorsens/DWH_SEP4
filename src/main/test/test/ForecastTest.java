package test;

import dwh.models.Forecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForecastTest {

    Forecast t;
    @BeforeEach
    void setUp() {
        t=new Forecast();
    }

    @Test
    void setAndGetCO2Forecast() {
        for (int i = 0; i < 100; i++) {
            t.setCO2Forecast(i,i,i,i);
            int [] test= new int[4];
            test[0]=i;
            test[1]=i;
            test[2]=i;
            test[3]=i;
            int [] actual=new int[4];
            actual[0]=t.getCO2Forecast().get(0);
            actual[1]=t.getCO2Forecast().get(1);
            actual[2]=t.getCO2Forecast().get(2);
            actual[3]=t.getCO2Forecast().get(3);
            assertArrayEquals(test,actual);
        }
    }


    @Test
    void setAndGetHumidityForecast() {
        for (int i = 0; i < 100; i++) {
            t.setHumidityForecast(i,i,i,i);
            int [] test= new int[4];
            test[0]=i;
            test[1]=i;
            test[2]=i;
            test[3]=i;
            int [] actual=new int[4];
            actual[0]=t.getHumidityForecast().get(0);
            actual[1]=t.getHumidityForecast().get(1);
            actual[2]=t.getHumidityForecast().get(2);
            actual[3]=t.getHumidityForecast().get(3);
            assertArrayEquals(test,actual);
        }
    }


    @Test
    void setAndGetTemperatureForecast() {
        for (int i = 0; i < 100; i++) {
            t.setHumidityForecast(i,i,i,i);
            int [] test= new int[4];
            test[0]=i;
            test[1]=i;
            test[2]=i;
            test[3]=i;
            int [] actual=new int[4];
            actual[0]=t.getTemperatureForecast().get(0);
            actual[1]=t.getTemperatureForecast().get(1);
            actual[2]=t.getTemperatureForecast().get(2);
            actual[3]=t.getTemperatureForecast().get(3);
            assertArrayEquals(test,actual);
        }
    }


    @Test
    void setAndGetNumberOfPassengersForecast() {
        for (int i = 0; i < 100; i++) {
            t.setHumidityForecast(i,i,i,i);
            int [] test= new int[4];
            test[0]=i;
            test[1]=i;
            test[2]=i;
            test[3]=i;
            int [] actual=new int[4];
            actual[0]=t.getNumberOfPassengersForecast().get(0);
            actual[1]=t.getNumberOfPassengersForecast().get(1);
            actual[2]=t.getNumberOfPassengersForecast().get(2);
            actual[3]=t.getNumberOfPassengersForecast().get(3);
            assertArrayEquals(test,actual);
        }
    }



    @Test
    void getDate() {
    }

    @Test
    void setDate() {
    }
}