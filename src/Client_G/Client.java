package Client_G;

import Client_G.Pages.Lobby;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;

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
    boolean keepChatting = false;

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public Client(int port, String name) {
        setPort(port);
        setUserName(name);

        try {
            socket = new Socket(hostAddress, 8082/*, InetAddress.getByName(hostAddress), 5003 /*Client_G.Client Port */);
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
                // System.out.println(sdf.format(new Date()));

                quiz += reader.readLine() + "\n";
                //choices:
                quiz += "1:   " + reader.readLine() + "           " + "2:   " + reader.readLine() + "\n";
                quiz += "3:   " + reader.readLine() + "           " + "4:   " + reader.readLine();
//                System.out.println(quiz);
                Lobby.LogTxtAr.setText(quiz);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Lobby.isClicked()) {
                    answer = Lobby.getChoice();
//                    System.out.println("answer is: " + answer);

                }
                if (answer == -1) {
//                    System.out.println("Time Out...");

                    answer = 0;
                }
                writer.println(answer + "");

////                answer = GAthread.getAnswer();
////                GAthread.killThread();
//                System.out.println(Server.score);
//                Messaging();
            }
            Lobby.LogTxtAr.setText("Finished ...");
            Lobby.showBtns(false);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void display(String msg) {

        System.out.println(msg);

    }

    //TODO
    void sendMessage(String msg) {
        writer.println(msg);
    }

    public void Messaging() {
        keepChatting = true;
        Scanner input = new Scanner(System.in);
        ListenFromServer listenThread = new ListenFromServer();
        listenThread.start();
        while (keepChatting) {
            System.out.print("> ");
            // read message from user
            String msg = input.nextLine();
            // logout if message is LOGOUT
            if (msg.contains("logout")) {
                input.close();
                keepChatting = false;
                this.sendMessage("logout");
                listenThread.stopT();

                break;
            }
            // regular text message

            this.sendMessage(msg);

        }
        // close resource
        // client completed its job. disconnect client.
        //client.disconnect();

    }

    class ListenFromServer extends Thread {

        public void run() {
            while (true) {
                try {
                    // read the message form the input datastream
                    String msg = reader.readLine();
                    System.out.println(msg);
                    // print the message
                    // System.out.println(msg);
                    System.out.print("> ");
                } catch (IOException e) {
                    display("Server has closed the connection: " + e);
                    break;
                }
            }
        }

        public void stopT() {
            super.stop();
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