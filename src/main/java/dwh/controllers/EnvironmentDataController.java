package dwh.controllers;

import dwh.adapters.EnvironmentDataAdapter;
import dwh.adapters.EnvironmentDataAdapterImpl;
import dwh.models.EnvironmentalValues;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class EnvironmentDataController {

   private final EnvironmentDataAdapter environmentDataAdapter;
   private GsonBuilder builder;
   private Gson gson;

   public EnvironmentDataController()
    {

        environmentDataAdapter = new EnvironmentDataAdapterImpl();
        builder= new GsonBuilder();
        gson = builder.create();

    }

    @PostMapping("/DataValues")
    public ResponseEntity<String> postValues(@RequestBody String value)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("PostData", "success");

        /*  Still needs to be constructed into environmentalValues object*/

    //    environmentDataAdapter.addEnvironmentalValuesToDB(value);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("/DataValues")
    public /*List<String>*/String getValues()
    {
        String jsonString = gson.toJson(environmentDataAdapter.getLatestEnvironmentalValue());
        return jsonString;
    }
}
