import java.io.*;
import java.net.Socket;

public class ClientManager implements Runnable {
    Socket client;
    Server server;

    public ClientManager(Server server, Socket client){
        this.server = server;
        this.client = client;

        try {
            server.fromClientStream = client.getInputStream();
            server.toClientStream = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.reader = new DataInputStream(server.fromClientStream);
        server.writer = new PrintWriter(server.toClientStream, true);
    }

    @Override
    public void run() {
        try{

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
