package Server_G;

import Server_G.Pages.Lobby;
import Client_G.Pages.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static ServerSocket socket;
    private int port;
    private String UserName;

    public static volatile HashMap<Integer, Integer> score = new HashMap<>();
    String logS = "";
    public static ArrayList<Thread> threadList = new ArrayList<Thread>();
    public static ArrayList<Socket> clientList = new ArrayList<>();
    //ArrayList<Client> threadList = new ArrayList<Thread>();


//    public static void main(String[] args) {
//        try {
//            new Server(8081, "host");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void serverStart() {
        try {
            if (!socket.isClosed())
                socket.close();
            socket = new ServerSocket(8082);
            String logS = "Server Created!\n";
            Lobby.clientsLogTxtAr.setText(logS);
//            System.out.println(logS);
            int countClient = 0;
            while (countClient < 1) {
                Socket client = socket.accept();
                clientList.add(client);
                countClient++;

                logS = String.format("client %d has connected!, port is: %d\n", countClient, client.getPort());
                Lobby.clientsLogTxtAr.appendText(logS);

                Thread t = new Thread(new ClientManager(this, client));
                threadList.add(t);
            }
            logS = "------------------------------------------\nQuiz has started ...\n";
            Lobby.clientsLogTxtAr.appendText(logS);
            Client_G.Pages.Lobby.LogTxtAr.setText(logS); //The previous text was deleted
            for (int i = 0; i < threadList.size(); i++) {
                threadList.get(i).start();
            }
            while (!socket.isClosed()) ;
            System.out.println("Server has downed!");
//            while(true);
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
