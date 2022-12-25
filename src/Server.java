import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    ServerSocket socket;
    private int port;
    private String UserName;

    InputStream fromClientStream;
    OutputStream toClientStream;
    DataInputStream reader;
    PrintWriter writer;

    ArrayList<Thread> threadList = new ArrayList<Thread>();
    //ArrayList<Client> threadList = new ArrayList<Thread>();


    public static void main(String[] args) {
        try {
            new Server(2518, "host");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void serverStart(){
        try {
            socket = new ServerSocket(2517);
            System.out.println("Server Created!");
            int countClient = 0;
            while (countClient < 3) {
                Socket client = socket.accept();
                countClient++;
                System.out.printf("client %d has connected!", countClient);
                Thread t = new Thread(new ClientManager(this, client));
                threadList.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Server(int port, String name) throws IOException {
        setUserName(name);
        setPort(port);
        socket = new ServerSocket(port);
        this.serverStart();
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

}
