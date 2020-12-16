package dwh.controllers;

import bridgeApp.WebSocketConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dwh.adapters.ActionAdapter;
import dwh.adapters.ActionAdapterImpl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ActionController {

    private final ActionAdapter actionAdapter;
    private GsonBuilder builder;
    private Gson gson;
    private WebSocketConnection webSocketConnection;

    public ActionController() {

        actionAdapter = new ActionAdapterImpl();
        builder = new GsonBuilder();
        gson = builder.create();
        webSocketConnection=WebSocketConnection.getInstance();
    }

    /**
     * Endpoint for posting an action value.
     * @param action the required action
     * @return a ResponseEntity
     */
    @PostMapping("/PostAction")
    public ResponseEntity<String> postAction(@RequestBody boolean action) {
        //  From ANDROID action = true means open action=false means closed
        // value 14= closed 28 = open to send TO IOT
        System.out.println("Action : "+action);
        if(action==true) {
            System.out.println("Send value 1");
            actionAdapter.setAction(1);
            webSocketConnection.sendDownLink(1);
            HttpHeaders headers = new HttpHeaders();
            headers.add("PostAction", "success");
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        else if(action==false)
        {
            System.out.println("Send value 0");
            actionAdapter.setAction(0);
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

    /*Method not used */
    /*
    @GetMapping("/GetAction")
    public int getAction() {

        return webSocketConnection.getShaftStatus();//+ environmentDataAdapter.getAction();
    }
 */
}
