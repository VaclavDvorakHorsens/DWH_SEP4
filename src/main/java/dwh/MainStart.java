package dwh;


import dwh.adapters.EnvironmentDataAdapter;
import dwh.adapters.EnvironmentDataAdapterImpl;
import dwh.models.EnvironmentalValues;
import dwh.networking.WebSocketConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Date;


@SpringBootApplication
public class MainStart {

    public static void main(String[] args) {
        /*real wss*/
        WebSocketConnection webSocketConnection = WebSocketConnection.getInstance();
       // webSocketConnection.sendDownLink(14);
        SpringApplication.run(MainStart.class, args);
    }
}
