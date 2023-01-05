import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Client {
    Socket socket;
    private int port;
    private String UserName;
    InputStream toServerStream;
    OutputStream fromServerStream;
    DataInputStream reader;
    PrintWriter writer;

    public Client(int port, String name) {
        setPort(port);
        setUserName(name);

        try {
            socket = new Socket("127.0.0.1", 8082);
            System.out.println("Connected to the server!");
            fromServerStream = socket.getOutputStream();
            toServerStream = socket.getInputStream();
            reader = new DataInputStream(toServerStream);
            writer = new PrintWriter(fromServerStream, true);
            Scanner input = new Scanner(System.in);
            System.out.println(reader.readLine());

            for (int i = 0; i < Question.questions.size(); i++) {
                System.out.println(reader.readLine());
                System.out.println(reader.readLine());
                System.out.println(reader.readLine());
                //get ans
                int ans = input.nextInt();
                writer.println(ans);
            }
            while (true) ;

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