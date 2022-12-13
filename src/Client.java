import java.io.*;
import java.net.Socket;

class Client {
    Socket socket;
    private int port;
    private String UserName;
    private String type;
    InputStream fromClientStream;
    OutputStream toClientStream;
    DataInputStream reader;
    PrintWriter writer;
    public Client(String type, int port, String name) throws IOException {
        setType(type);
        setPort(port);
        setUserName(name);
        socket = new Socket("127.0.0.1",port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}