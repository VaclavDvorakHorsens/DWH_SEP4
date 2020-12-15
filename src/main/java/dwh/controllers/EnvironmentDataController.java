package dwh.controllers;

import dwh.adapters.DWHEnvironmentDataAdapterImpl;
import dwh.adapters.DWHEnviromentDataAdapter;
import dwh.models.*;
import bridgeApp.WebSocketConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class EnvironmentDataController {

    private final DWHEnviromentDataAdapter environmentDataAdapter;
    private GsonBuilder builder;
    private Gson gson;
    private int shaftAction;
    private WebSocketConnection webSocketConnection;

    public EnvironmentDataController() {

        environmentDataAdapter = new DWHEnvironmentDataAdapterImpl();
        builder = new GsonBuilder();
        gson = builder.create();
        webSocketConnection=WebSocketConnection.getInstance();
    }


    @GetMapping("/DataValues")
    public String getValues() {

        EnvironmentalValues latest =environmentDataAdapter.getLatestEnvironmentalValue();
        latest.setShaftPos(webSocketConnection.getShaftStatus());

        String jsonString = gson.toJson(latest);

        return jsonString;
    }

    //get values for a period of time
    // localhost:8080/DataValuesByDate?date=startDate&date=endDate
    @GetMapping("/DataValuesByDate")
    public String getValuesByDate(@RequestParam List<String> date) {
//        //URl Decoding
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

    @PostMapping("/PostAction")
    public ResponseEntity<String> postAction(@RequestBody boolean action) {
        //  From ANDROID action = true means open action=false means closed
        // value 14= closed 28 = open to send TO IOT
        System.out.println("Action : "+action);
        if(action==true) {
            System.out.println("Send value 1");
            environmentDataAdapter.setAction(1);
            shaftAction=1;
            webSocketConnection.sendDownLink(1);
            HttpHeaders headers = new HttpHeaders();
            headers.add("PostAction", "success");
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        else if(action==false)
        {
            System.out.println("Send value 0");
            environmentDataAdapter.setAction(0);
            shaftAction=0;
            webSocketConnection.sendDownLink(0);

            HttpHeaders headers = new HttpHeaders();
            headers.add("PostAction", "success");

            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        // return bad request if values are outside of acceptable range
        HttpHeaders headers = new HttpHeaders();
        headers.add("PostAction", "fail");

        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/GetAction")
    public int getAction() {

        return webSocketConnection.getShaftStatus();//+ environmentDataAdapter.getAction();

    }

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

    @GetMapping("/GetAverageNumberOfPeople")
    public String getAverageNumberOfPeople()
    {
        AverageNumberOfPeople people = new AverageNumberOfPeople(environmentDataAdapter.getAverageNumberOfPeople());
        String jsonString = gson.toJson(people);

        return jsonString;
    }
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
        // System.out.println(jsonString);
        return jsonString;
    }

}

