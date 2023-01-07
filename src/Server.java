import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    ServerSocket socket;
    private int port;
    private String UserName;

    public static volatile HashMap<Integer, Integer> score = new HashMap<>();

    ArrayList<Thread> threadList = new ArrayList<Thread>();
    //ArrayList<Client> threadList = new ArrayList<Thread>();


    public static void main(String[] args) {
        try {
            new Server(8081, "host");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverStart() {
        try {
            socket = new ServerSocket(8082);
            System.out.println("Server Created!");
            int countClient = 0;
            while (countClient < 2) {
                Socket client = socket.accept();
                countClient++;
                System.out.printf("client %d has connected!, port is: %d\n", countClient,client.getPort());
                Thread t = new Thread(new ClientManager(this, client));
                threadList.add(t);
            }

            for (int i = 0; i < this.threadList.size(); i++) {
                this.threadList.get(i).start();
            }
            while(true);
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
