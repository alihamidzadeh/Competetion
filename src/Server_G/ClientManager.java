package Server_G;

import Server_G.Pages.Lobby;
import Datas.*;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import static java.lang.Thread.sleep;

public class ClientManager extends Thread {
    Socket client;
    Server server;

    InputStreamReader fromClientStream;
    OutputStreamWriter toClientStream;
    BufferedReader reader;
    PrintWriter writer;
    int answer = 0;
    int port;
    String username;
    public boolean online = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");


    public static volatile HashMap<String, Integer> score = new HashMap<>();

    public String getUsername() {
        return username;
    }

    public boolean isOnline() {
        return online;
    }

    public ClientManager(Server server, Socket client, int port) {
        this.server = server;
        this.client = client;
        this.port = port;

        try {
            fromClientStream = new InputStreamReader(client.getInputStream(), "UTF-8");
            toClientStream = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
            reader = new BufferedReader(fromClientStream);
            writer = new PrintWriter(toClientStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.username = reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            String logS = "";
            writer.println(Question.questions.size());
            writer.println(Server.qDuration);
            writer.println(Server.threadList.size());

            for (int i = 0; i < Question.questions.size(); i++) {
//                logS = String.format("Question number: %d has asked.", i + 1);
//                Lobby.clientsLogTxtAr.appendText(logS);
                 Thread.sleep(3000); //TODO WHY because the sky is high
                //  System.out.println(Question.questions.get(i).getQuest() + sdf.format(new Date()));
                writer.println(Question.questions.get(i).getQuest());
                //  System.out.println(sdf.format(new Date()));

                writer.println(Question.questions.get(i).getChoices(1));
                writer.println(Question.questions.get(i).getChoices(2));
                writer.println(Question.questions.get(i).getChoices(3));
                writer.println(Question.questions.get(i).getChoices(4));

//                System.out.println(Question.questions.get(i).getChoices());
                //timer start
//                writer.println("type the number of your choice: ");
                answer = Integer.parseInt(reader.readLine());


//                sleep(10000); //TODO 15000
//                System.out.println(Question.questions.get(i).getAns());
                if (answer == Question.questions.get(i).getAns()) {
                    //update score
                    ClientManager.score.put(this.getUsername(), ClientManager.score.getOrDefault(this.getUsername(), 0) + 100);
                } else if(answer == 0) {
                    ClientManager.score.put(this.getUsername(), ClientManager.score.getOrDefault(this.getUsername(), 0));
                }
                else{
                    ClientManager.score.put(this.getUsername(), ClientManager.score.getOrDefault(this.getUsername(), 0) - 100);
                }

                class test extends Thread {
                    public void run() {
                        sendScoreBoard();
                    }
                }
                Thread.sleep(500);
                test t = new test();
                t.start();

                logS = String.format("answer client (%d) to question (%d) is: %d\n", client.getPort(), i + 1, answer);
                String finalLogS = logS;
                Platform.runLater(() -> {
                    soutLog(finalLogS);
                    soutLog(ClientManager.score.toString() + "\n");

                });

                Thread.sleep(5000); //for check scoreboard
                t.stop();
                inputMessages();
                while (!checkChatExit()) ;
                writer.println("chat finished");

            }
//            while (true) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized private void sendScoreBoard() {
        for (int j = 0; j < Server.threadList.size(); j++) {
            writer.println(Server.threadList.get(j).client.getPort() + " " + score.get(Server.threadList.get(j).client.getPort()));
//            writer.println();
//            System.out.println(Server.threadList.get(j).client.getPort() + " - " + score.get(Server.threadList.get(j).client.getPort()));
        }
//        System.out.println("-----------------------");
    }

    private boolean checkChatExit() {
        int count = 0;
        for (int i = 0; i < Server.threadList.size(); i++) {
            if (!Server.threadList.get(i).online) {
                count++;
            }
        }
        return count == Server.threadList.size();
    }

    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        System.out.println(time);
    }

    public void inputMessages() {
        online = true;
        String message;
        while (online) {
            try {
                message = reader.readLine();
            } catch (IOException e) {
                display(this.getUsername() + " Exception reading Streams: " + e); //TODO key lazem mishe?//readline ada da biare
                break;
            }

            // different actions based on type message
            if (message.contains("logout")) {
                this.online = false;
                boolean confirmation = server.broadcast(username + ": " + "has left the chat");
                if (confirmation == false) {
                    String msg = "Sorry. No such user exists.";
                    this.writeMsg(msg);
                }
                break;
            } else {
                boolean confirmation = server.broadcast(username + ": " + message);
                if (confirmation == false) {
                    String msg = "Sorry. No such user exists.";
                    this.writeMsg(msg);
                }
            }

        }
    }


//    class ChatThread extends Thread{
//        public void run(){
//
//        }
//    }

    //TODO

    public boolean writeMsg(String msg) {
        // if Client is still connected send the message to it
        if (!client.isConnected()) {
            // close();
            return false;
        }
        // write the message to the stream

        writer.println(msg);

        return true;
    }

    synchronized private void soutLog(String logS) {
        Lobby.clientsLogTxtAr.appendText(logS);
    }
}
