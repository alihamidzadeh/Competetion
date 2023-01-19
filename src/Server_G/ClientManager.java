package Server_G;

import Datas.Question;
import Server_G.Pages.Lobby;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ClientManager extends Thread {
    Socket client;
    Server server;

    private InputStreamReader fromClientStream;
    private OutputStreamWriter toClientStream;
    private BufferedReader reader;
    private PrintWriter writer;
    private int answer = 0;
    private int port;
    private String username;
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
            System.out.println("user is " + this.username);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initScores();
    }

    public static void initScores() {
        for (int i = 0; i < Server.threadList.size(); i++) {
            score.put(Server.threadList.get(i).getUsername(), 0);
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
                Thread.sleep(200);
                writer.println(Question.questions.get(i).getQuest());

                writer.println(Question.questions.get(i).getChoices(1));
                writer.println(Question.questions.get(i).getChoices(2));
                writer.println(Question.questions.get(i).getChoices(3));
                writer.println(Question.questions.get(i).getChoices(4));

                //timer start
                answer = Integer.parseInt(reader.readLine());
                logS = String.format("answer client (%s) to question (%d) is: %d\n", username, i + 1, answer);

                Thread.sleep(200);
                //update score
                if (answer == Question.questions.get(i).getAns())
                    ClientManager.score.put(this.getUsername(), ClientManager.score.getOrDefault(this.getUsername(), 0) + 100);
                else if (answer == 0)
                    ClientManager.score.put(this.getUsername(), ClientManager.score.getOrDefault(this.getUsername(), 0));
                else
                    ClientManager.score.put(this.getUsername(), ClientManager.score.getOrDefault(this.getUsername(), 0) - 100);

                Thread.sleep(2000); //wait for complete score board

                class scoreSenderT extends Thread {
                    public void run() {
                        sendScoreBoard();
                    }
                }
                scoreSenderT t = new scoreSenderT();
                t.start();

                String finalLogS = logS;
                Platform.runLater(() -> {
                    soutLog(finalLogS);
                    soutLog(ClientManager.score.toString() + "\n");

                });

                inputMessages();
                while (!checkChatExit()) ;
                writer.println("chat finished");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized private void sendScoreBoard() {
        String str;
        for (int j = 0; j < Server.threadList.size(); j++) {
            str = Server.threadList.get(j).getUsername(); //username
            str = str + " " + score.get(str); // username score (Optimized)
            writer.println(str);
            System.out.println(str);
        }
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
        soutLog(time);
    }

    public void inputMessages() {
        online = true;
        String message;
        while (online) {
            try {
                message = reader.readLine();
            } catch (IOException e) {
                display(this.getUsername() + " Exception reading Streams: " + e);
                break;
            }

            // different actions based on type message
            if (message.contains("logout")) {
                this.online = false;
                boolean confirmation = server.broadcast(username + ": " + "has left the chat\n");
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

    public boolean writeMsg(String msg) {
        // if Client is still connected send the message to it
        if (!client.isConnected()) {
            return false;
        }
        // write the message to the stream

        writer.println(msg);

        return true;
    }

    static synchronized public void soutLog(String logS) {
        Lobby.clientsLogTxtAr.appendText(logS);
    }
}
