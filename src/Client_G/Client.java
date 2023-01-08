package Client_G;

import Client_G.Pages.Lobby;
import Datas.Question;
import Server_G.Server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

import static java.lang.System.exit;

public class Client {
    public static Socket socket;
    private int port;
    private String UserName;
    InputStreamReader toServerStream;
    OutputStreamWriter fromServerStream;
    BufferedReader reader;
    PrintWriter writer;
    int answer;
    String hostAddress = "127.0.0.1";

    public Client(int port, String name) {
        setPort(port);
        setUserName(name);

        try {
//                while (socket == null) {
            socket = new Socket(hostAddress, 8082/*, InetAddress.getByName(hostAddress), 5003 /*Client_G.Client Port */);
//            System.out.println("Connected to the server!");
//            Lobby.label2.setText("Connected to the server!");
//                    if (socket.isConnected()){
//                        break;
//                    }
//                }
            Lobby.label2.setText("Connected To Server!");
            fromServerStream = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            toServerStream = new InputStreamReader(socket.getInputStream(), "UTF-8");
            reader = new BufferedReader(toServerStream);
            writer = new PrintWriter(fromServerStream, true);
            String quiz;

            for (int i = 0; i < Question.questions.size(); i++) {
                quiz = reader.readLine() + "\n" + reader.readLine();
                System.out.println(quiz);
                Lobby.LogTxtAr.setText(quiz);
                answer = 0;
                Scanner input = new Scanner(System.in);

                ExecutorService response = Executors.newSingleThreadExecutor();
                try {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            answer = input.nextInt();
                        }
                    };
                    Future<?> f = response.submit(r);
                    f.get(15, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    System.out.println("TimeOut");
                    //    input.close();

                    answer = 0;

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    writer.println(answer + "");
                    response.shutdown();
                }
//                answer = GAthread.getAnswer();
//                GAthread.killThread();
                System.out.println(Server.score);
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

}