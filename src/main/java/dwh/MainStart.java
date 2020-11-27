package dwh;

import dwh.adapters.EnvironmentDataAdapter;
import dwh.adapters.EnvironmentDataAdapterImpl;
import dwh.models.EnvironmentalValues;
import dwh.networking.WebSocketConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;


@SpringBootApplication
public class MainStart {

    public static void main(String[] args) {
        /*real wss*/
        String url = "wss://iotnet.cibicom.dk/app?token=vnoTOgAAABFpb3RuZXQuY2liaWNvbS5ka2z21adiqWKYdLsgxiOUnKc=";
        WebSocket ws = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create(url), new WebSocketConnection())
                .join();
        SpringApplication.run(MainStart.class, args);

    }
}
