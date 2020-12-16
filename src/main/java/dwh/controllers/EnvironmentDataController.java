package dwh.controllers;

import dwh.adapters.DWHEnvironmentDataAdapterImpl;
import dwh.adapters.DWHEnvironmentDataAdapter;
import dwh.models.*;
import bridgeApp.WebSocketConnection;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class EnvironmentDataController {

    private final DWHEnvironmentDataAdapter environmentDataAdapter;
    private GsonBuilder builder;
    private Gson gson;
    private WebSocketConnection webSocketConnection;

    public EnvironmentDataController() {

        environmentDataAdapter = new DWHEnvironmentDataAdapterImpl();
        builder = new GsonBuilder();
        gson = builder.create();
        webSocketConnection=WebSocketConnection.getInstance();
    }



    /**
     * Endpoint for retrieving the latest environmental data values.
     * @return a Json String
     */
    @GetMapping("/DataValues")
    public String getValues() {

        EnvironmentalValues latest = environmentDataAdapter.getLatestEnvironmentalValue();
       latest.setShaftPos(webSocketConnection.getShaftStatus());

        System.out.println("Shaft status sent to Android: " + webSocketConnection.getShaftStatus() + "\n");
        String jsonString = gson.toJson(latest);

        return jsonString;
    }


    /*Method not used */
 /*
    @GetMapping("/DataValuesByDate")
    public String getValuesByDate(@RequestParam List<String> date) {
      //URl Decoding
        String decodedStartDate = "";
        String decodedEndDate = "";
        try {
            decodedStartDate = URLDecoder.decode(date.get(0), "UTF8");
            decodedEndDate = URLDecoder.decode(date.get(1), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Date startDate = gson.fromJson(decodedStartDate, Date.class);
        Date endDate = gson.fromJson(decodedEndDate, Date.class);
        if (startDate.before(endDate)) {
            Date startDateSql = new Date(startDate.getDay(), startDate.getMonth(), startDate.getYear());
            Date endDateSql = new Date(endDate.getDay(), endDate.getMonth(), endDate.getYear());
            System.out.println("return data from");
            String jsonToAND = gson.toJson(environmentDataAdapter.getEnvironmentalValuesFromDatabaseGivenDate(startDateSql, endDateSql));
            return jsonToAND;
        } else return "Start date is after end date!";
    }
*/


    /**
     * Endpoint for retrieving a forecast.
     * @param date the required date of desired forecast
     * @return a Json String
     */
    @GetMapping("/GetForecast")
    public String getForecast(@RequestParam String date)
    {
        String split[] = date.split("-");
        Date temp = new Date(Integer.parseInt(split[2]),Integer.parseInt(split[1]),Integer.parseInt(split[0]));
        Forecast forecast = environmentDataAdapter.getForecast(temp);

        String jsonString = gson.toJson(forecast);
        System.out.println(jsonString);

        return jsonString;
    }

    /**
     * Endpoint for retrieving the average number of people in the station of the last hour.
     * @return a Json String
     */
    @GetMapping("/GetAverageNumberOfPeople")
    public String getAverageNumberOfPeople()
    {
        AverageNumberOfPeople people = new AverageNumberOfPeople(environmentDataAdapter.getAverageNumberOfPeople());
        String jsonString = gson.toJson(people);

        return jsonString;
    }

    /**
     * Get a list of forecasts.
     * @return a Json String
     */
    @GetMapping("/GetLogList")
    public String getListForecast()
    {
        Date temp = new Date();
        int nextDay=temp.getDay()-1;
        int nextMonth=temp.getDay()-1;
        ArrayList<Forecast> listForecast= new ArrayList<>();
        Forecast forecast = environmentDataAdapter.getForecast(temp);
        listForecast.add(forecast);
        String jsonString="";
        for (int i = 0; i < 5; i++) {
            nextDay-=1;
            nextMonth-=1;
            if( nextDay>0)
            {
                temp.setDay(nextDay);
                listForecast.add(environmentDataAdapter.getForecast(temp));
            }

            // date 1-01-2020
            if(nextDay == 0){
                if( nextMonth>0)
                {
                    temp.setDay(LocalDate.of(temp.getYear(),temp.getMonth()-1,1).lengthOfMonth());
                    temp.setMonth(temp.getMonth()-1);
                    listForecast.add(environmentDataAdapter.getForecast(temp));
                }
                else {
                    temp.setDay(31);
                    temp.setMonth(12);
                    temp.setYear(temp.getYear()-1);
                    listForecast.add(environmentDataAdapter.getForecast(temp));
                }
            }
        }

        jsonString = gson.toJson(listForecast);
        return jsonString;
    }
}

