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
    public static int answer;
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
            int numberOfQuestions = Integer.parseInt(reader.readLine());
//            System.out.println("numberOfQuestions= " + numberOfQuestions);
            for (int i = 0; i < numberOfQuestions; i++) {
                Lobby.setClicked(false);
                Lobby.showBtns(true);
                answer = -1;
                quiz = "شماره سوال: " + (i + 1) + " از " + numberOfQuestions + "\n";
                quiz += reader.readLine() + "\n";
                //choices:
                quiz += "1:   " + reader.readLine() + "           " + "2:   " + reader.readLine() + "\n";
                quiz += "3:   " + reader.readLine() + "           " + "4:   " + reader.readLine();

                System.out.println(quiz);
                Lobby.LogTxtAr.setText(quiz);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Lobby.isClicked()) {
                    answer = Lobby.getChoice();
                    System.out.println("answer is: " + answer);

                }
                if (answer == -1) {
                    System.out.println("Time Out...");
                    answer = 0;
                }
                writer.println(answer + "");

////                answer = GAthread.getAnswer();
////                GAthread.killThread();
//                System.out.println(Server.score);
            }

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