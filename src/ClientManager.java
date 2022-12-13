import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ClientManager {
    Client client;
    Server server;
    InputStream fromClientStream;
    OutputStream toClientStream;
    DataInputStream reader;
    PrintWriter writer;
    public ClientManager(Server server, Client client){
        this.server = server;
        this.client = client;
    }

}
