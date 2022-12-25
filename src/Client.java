import java.io.*;
import java.net.Socket;

class Client {
    Socket socket;
    private int port;
    private String UserName;
    InputStream fromClientStream;
    OutputStream toClientStream;
    DataInputStream reader;
    PrintWriter writer;

    public Client(int port, String name) {
        setPort(port);
        setUserName(name);

        try {
            socket = new Socket("127.0.0.1",2517);
            System.out.println("Connected to the server!");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        new Client(5230, "client1");
    }
}