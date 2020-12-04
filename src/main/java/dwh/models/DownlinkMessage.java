package dwh.models;

public class DownlinkMessage {
    private String cmd = "tx";    // must always have the value 'tx'
    private String EUI;   // device EUI, 16 hex digits (without dashes)
    private int port; // port to be used (1..223)
    // private  boolean confirmed; // NEW!  (optional) request confirmation (ACK) from end-device
    private String data;  // data payload (to be encrypted by our server)
    // if no APPSKEY is assigned to device, this will return an error

    public DownlinkMessage(String EUI, int port, String data){
        this.EUI=EUI;
        this.port=port;
        this.data=data;
    }


    public String getEUI() {
        return EUI;
    }

    public void setEUI(String EUI) {
        this.EUI = EUI;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
