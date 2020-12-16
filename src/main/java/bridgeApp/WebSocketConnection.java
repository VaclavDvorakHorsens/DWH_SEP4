package bridgeApp;

import dwh.models.EnvironmentalValues;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class WebSocketConnection implements WebSocket.Listener {

    private final CountDownLatch latch;
    private EnvironmentDataAdapter environmentDataAdapter;
    private WebSocket server;
    private HttpClient client;
    private CompletableFuture<WebSocket> ws;
    private static WebSocketConnection instance;
    private int shaftStatus = 0;

    private WebSocketConnection() {
        this.latch = new CountDownLatch(1);
        this.environmentDataAdapter = new EnvironmentDataAdapterImpl();

        client = HttpClient.newHttpClient();
        ws = client.newWebSocketBuilder()
                .buildAsync(URI.create("wss://iotnet.cibicom.dk/app?token=vnoTOgAAABFpb3RuZXQuY2liaWNvbS5ka2z21adiqWKYdLsgxiOUnKc="),
                        this);
        server = ws.join();
    }

    public static WebSocketConnection getInstance() {
        if (instance == null) {
            instance = new WebSocketConnection();
        }

        return instance;
    }

    /**
     * on Open is called when the connection is made and opens a websocket listener.
     * @param webSocket
     */
    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("Open connection");
        webSocket.request(1);
    }

    /**
     * Sends down a Json telegram to the servo
     * @param value the action value
     */
    public void sendDownLink(int value) {
        String JsonTel = "";
        if (value == 1) {
            JsonTel = "{\n" +
                    "    \"cmd\": \"tx\",\n" +
                    "    \"EUI\": \"0004A30B00259F36\",\n" +
                    "    \"port\": 2,\n" +
                    "    \"confirmed\": false,\n" +
                    "    \"data\": \"28\",\n" +
                    "    \"appid\": \"BE7A133A\"\n" +
                    "}";
        } else if (value == 0) {
            JsonTel = "{\n" +
                    "    \"cmd\": \"tx\",\n" +
                    "    \"EUI\": \"0004A30B00259F36\",\n" +
                    "    \"port\": 2,\n" +
                    "    \"confirmed\": false,\n" +
                    "    \"data\": \"14\",\n" +
                    "    \"appid\": \"BE7A133A\"\n" +
                    "}";
        }
        server.sendText(JsonTel, true);
    }

    /**
     * On errror is called when there occurs an error during the connection.
     * @param webSocket
     * @param error
     */
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println(error.getMessage());
        error.printStackTrace();
        webSocket.abort();

        client = HttpClient.newHttpClient();
        ws = client.newWebSocketBuilder()
                .buildAsync(URI.create("wss://iotnet.cibicom.dk/app?token=vnoTOgAAABFpb3RuZXQuY2liaWNvbS5ka2z21adiqWKYdLsgxiOUnKc="),
                        this);
        server = ws.join();
    }

    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed!");
        System.out.println("Status:" + statusCode + " Reason: " + reason);
        return new CompletableFuture().completedFuture("onClose() completed.").thenAccept(System.out::println);
    }

    public CompletionStage<?> onPing​(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Ping: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Ping completed.").thenAccept(System.out::println);
    }

    public CompletionStage<?> onPong​(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Pong: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Pong completed.").thenAccept(System.out::println);
    }

    /**
     * Used to receive incoming Json telegrams from IoT
     * @param webSocket
     * @param data
     * @param last
     * @return CompletableFuture
     */
    public CompletionStage<?> onText​(WebSocket webSocket, CharSequence data, boolean last) {
        webSocket.request(1);
        String indented = data.toString();
        handleData(indented);

        System.out.println(indented);
        return new CompletableFuture().completedFuture("onText() completed.").thenAccept(System.out::println);
    }

    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        webSocket.request(1);
        return null;
    }

    /**
     * Handles the received data and forwards it to the database.
     * @param jsonTelegram received data
     */
    private void handleData(String jsonTelegram) {
        // parse the telegram from String to JSONObject
        var parser = new JSONParser();
        JSONObject json;
        try {
            json = (JSONObject) parser.parse(jsonTelegram);
        } catch (ParseException e) {
            System.out.println("Something went wrong!");
            return;
        }

        if (json == null) {
            System.out.println("Failed to parse json telegram");
            return;
        }

        if (!json.get("cmd").equals("rx")) return;

        // extract data raw
        String dataAsHex;

        dataAsHex = (String) json.get("data");

        // check if data field is null
        if (dataAsHex == null) {
            System.out.println("data field in telegram is null");
            return;
        }

        // parse sensor data
        int temperature = Integer.parseInt(dataAsHex.substring(0, 4), 16) / 10;
        int humidity = Integer.parseInt(dataAsHex.substring(4, 8), 16) / 10;
        int co2 = Integer.parseInt(dataAsHex.substring(8, 12), 16) / 10;
        shaftStatus = Integer.parseInt(dataAsHex.substring(12, 16), 16);
        int passengers = Integer.parseInt(dataAsHex.substring(16), 16);

        EnvironmentalValues dataCollection = new EnvironmentalValues(
                co2, 2, humidity, 1, temperature,
                1, passengers, 3, new Date());
        System.out.println("Data Received From Loriot: " + dataCollection);
        System.out.println("Shaft status " + shaftStatus);

        // sending data to the database
        environmentDataAdapter.addEnvironmentalValuesToDB(dataCollection);
    }

    /**
     * Gets the status of the shaft's current condition (open/closed).
     * @return int
     */
    public int getShaftStatus() {
        return shaftStatus;
    }
}
