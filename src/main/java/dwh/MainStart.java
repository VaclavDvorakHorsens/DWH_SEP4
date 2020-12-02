package dwh;

import dwh.networking.WebSocketConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainStart {

    public static void main(String[] args) {
        /*real wss*/
        WebSocketConnection webSocketConnection = WebSocketConnection.getInstance();

        SpringApplication.run(MainStart.class, args);
    }
}
