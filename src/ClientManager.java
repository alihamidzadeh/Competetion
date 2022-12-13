import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManager implements Runnable {
    Socket client;
    Server server;

    public ClientManager(Server server, Socket client){
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        try{
            server.fromClientStream = client.getInputStream();
            server.toClientStream = client.getOutputStream();
            server.reader = new DataInputStream(server.fromClientStream);
            server.writer = new PrintWriter(server.toClientStream, true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
