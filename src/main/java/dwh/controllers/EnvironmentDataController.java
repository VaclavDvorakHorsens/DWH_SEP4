package dwh.controllers;

import dwh.adapters.DWHEnviomentDataAdapterImpl;
import dwh.adapters.DWHEnviromentDataAdapter;
import dwh.models.*;
import bridgeApp.WebSocketConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class EnvironmentDataController {

    private final DWHEnviromentDataAdapter environmentDataAdapter;
    private GsonBuilder builder;
    private Gson gson;
    private int shaftAction;
    WebSocketConnection webSocketConnection;


    public EnvironmentDataController() {

        environmentDataAdapter = new DWHEnviomentDataAdapterImpl();
        builder = new GsonBuilder();
        gson = builder.create();
        webSocketConnection=WebSocketConnection.getInstance();

    }

    @PostMapping("/DataValues")
    public ResponseEntity<String> postValues(@RequestBody String value) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("PostData", "success");

        //    environmentDataAdapter.addEnvironmentalValuesToDB(value);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("/DataValues")
    public /*List<String>*/String getValues() {

        EnvironmentalValues test =environmentDataAdapter.getLatestEnvironmentalValue();
        test.setShaftPos(shaftAction);

        String jsonString = gson.toJson(test);

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
            java.sql.Date startDateSql = new java.sql.Date(startDate.getDay(), startDate.getMonth(), startDate.getYear());
            java.sql.Date endDateSql = new java.sql.Date(endDate.getDay(), endDate.getMonth(), endDate.getYear());
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
    public String getForecast(@RequestBody dwh.models.Date date)
    {
        Forecast forecast = environmentDataAdapter.getForecast(date);
        String jsonString = gson.toJson(forecast);

        return jsonString;
    }
}

