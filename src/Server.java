import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket socket;
    private int port;
    private String UserName;
    private String type;

    InputStream fromClientStream;
    OutputStream toClientStream;
    DataInputStream reader;
    PrintWriter writer;

//    public static void main(String[] args) {
//        try {
//
//             =new ServerSocket(serverPort);
//            System.out.println("Server Created!");
//            while (countClient < 3) {
//                Socket client = mServer.accept();
//                countClient++;
//                System.out.printf("client %d has connected!", countClient + 1);
//                Thread t = new Thread(new ClientManager());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public Server(String type, int port, String name) throws IOException {
        setUserName(name);
        setType(type);
        setPort(port);
        socket = new ServerSocket(port);
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
