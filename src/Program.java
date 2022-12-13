import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class Program {
    ServerSocket mServer;
    int serverPort = 9090;
    InputStream fromClientStream;
    OutputStream toClientStream;
    DataInputStream reader;
    PrintWriter writer;
    int countClient;
    public Program() {
        try {
            mServer = new ServerSocket(serverPort);
            System.out.println("Server Created!");
            while (countClient < 3){
                Socket client = mServer.accept();
                countClient++;
                System.out.printf("client %d has connected!", countClient+1);
//                thread
            }

// input stream (stream from client)
            fromClientStream = client.getInputStream();
// output sream (stream to client)
            toClientStream = client.getOutputStream();
            reader = new DataInputStream(fromClientStream);
            writer = new PrintWriter(toClientStream, true);
// send message to client
            writer.println("Salam Client joon");
            System.out.println("Server :Salam Client joon");
// Receive client response (javab:D)
            String javab = reader.readLine();
            System.out.println("Client :" + javab);
// send message to client (again)
            writer.println("khobi??");
            System.out.println("Server :khobi?");
// Receive client response (javab:D)
            javab = reader.readLine();
            System.out.println("Client :" + javab);
        } catch (IOException e) { }
    }
    public static void main(String[] args) {
        new Program();
    }
}