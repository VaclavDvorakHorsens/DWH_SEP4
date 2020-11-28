package dwh.networking;

import dwh.adapters.EnvironmentDataAdapter;
import dwh.adapters.EnvironmentDataAdapterImpl;
import dwh.models.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class WebSocketConnection implements WebSocket.Listener {
    private final CountDownLatch latch;
    private final EnvironmentDataAdapter environmentDataAdapter;




    public WebSocketConnection()
    { this.latch = new CountDownLatch(1);
      this.environmentDataAdapter = new EnvironmentDataAdapterImpl();
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("Open connection");
        webSocket.sendText("public wss test",true);
       /* webSocket.sendBinary(bbuf,true);*/
        webSocket.request(1);

        WebSocket.Listener.super.onOpen(webSocket);
    }


    public void onError(WebSocket webSocket, Throwable error)
    {
        System.out.println("A " + error.getCause() + " exception was thrown.");
        System.out.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
    }

    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason)
    {
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

    public CompletionStage<?> onText​(WebSocket webSocket, CharSequence data, boolean last) {
        String indented = null;
        try {
            indented = (new JSONObject(data.toString())).toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        while(true)
        {
            convertFromHexToString(indented);
            System.out.println(indented);
            String a = data.toString();
            webSocket.request(1);
            return new CompletableFuture().completedFuture("onText() completed.").thenAccept(System.out::println);
        }


        /*  Still needs to be constructed into environmentalValues object*/

    //    environmentDataAdapter.addEnvironmentalValuesToDB(a);


    }

    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        webSocket.request(1);
        return null;
    }

    /**
     * Mathias
     * Converts the received Hex data to a String
     * @return dataObject containing humidity, Temperature & Co2
     */


    public Data convertFromHexToString(String Received){
        System.out.println("Starting convert: \n" + Received);
        String[] parts = Received.split("\"");
        String data = parts[7];
        int temperature = Integer.parseInt(data.substring(0,4),16)/10;
        int humidity = Integer.parseInt(data.substring(4,8),16)/10;
        int co2 = Integer.parseInt(data.substring(8,12),16)/10;
        Data dataCollection = new Data(temperature,humidity,co2);
        System.out.println("Data Received From Loriot: "+dataCollection);
        return dataCollection;
    }

}
