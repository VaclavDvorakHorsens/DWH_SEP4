package dwh.controllers;

import dwh.adapters.EnvironmentDataAdapter;
import dwh.adapters.EnvironmentDataAdapterImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class EnvironmentDataController {

   private final EnvironmentDataAdapter environmentDataAdapter;

    public EnvironmentDataController()
    {
        environmentDataAdapter = new EnvironmentDataAdapterImpl();
    }

    @PostMapping("/DataValues")
    public ResponseEntity<String> postValues(@RequestBody String value)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("PostData", "success");

        environmentDataAdapter.addValuesToDatabase(value);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("/DataValues")
    public List<String> getValues()
    {
        return environmentDataAdapter.getValuesFromDatabase();
    }
}
