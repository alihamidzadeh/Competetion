package Server_G;

import Server_G.Pages.Lobby;
import Server_G.Server;
import Datas.*;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;


import static java.lang.Thread.sleep;

public class ClientManager extends Thread {
    Socket client;
    Server server;

    InputStreamReader fromClientStream;
    OutputStreamWriter toClientStream;
    BufferedReader reader;
    PrintWriter writer;
    int answer = 0;
    String username;
    public boolean keepGoing = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");


    public static volatile HashMap<Integer, Integer> score = new HashMap<>();

    public String getUsername() {
        return username;
    }

    public ClientManager(Server server, Socket client, String username) {
        this.server = server;
        this.client = client;
        this.username = username;

        try {
            fromClientStream = new InputStreamReader(client.getInputStream(), "UTF-8");
            toClientStream = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
            reader = new BufferedReader(fromClientStream);
            writer = new PrintWriter(toClientStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        try {
            String logS = "";
            writer.println(Question.questions.size());
            for (int i = 0; i < Question.questions.size(); i++) {
//                logS = String.format("Question number: %d has asked.", i + 1);
//                Lobby.clientsLogTxtAr.appendText(logS);
                Thread.sleep(3000);
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
                logS = String.format("answer client (%d) to question (%d) is: %d\n", client.getPort(), i + 1, answer);
                soutLog(logS);

//                sleep(10000); //TODO 15000
                System.out.println(Question.questions.get(i).getAns());
                if (answer == Question.questions.get(i).getAns()) {
                    //update score
                    ClientManager.score.put(client.getPort(), ClientManager.score.getOrDefault(client.getPort(), 0) + 1);
                } else {
                    ClientManager.score.put(client.getPort(), ClientManager.score.getOrDefault(client.getPort(), 0));
                }
                soutLog(ClientManager.score.toString());
                Thread.sleep(5000);

                inputMessages();
                while(!checkChatExit());

            }
//            while (true) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean checkChatExit(){
        int count = 0;
        for (int i = 0; i < Server.threadList.size(); i++) {
            if(Server.threadList.get(i).keepGoing == false){
                count++;
            }
        }
        return (count == Server.threadList.size()) ? true : false;
    }
    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        System.out.println(time);
    }

    public
    void inputMessages(){
        keepGoing = true;
        String message;
        while(keepGoing) {
            try {
                message = reader.readLine();
            }
            catch (IOException e) {
                display(this.getUsername() + " Exception reading Streams: " + e);
                break;
            }

            // different actions based on type message
            if(message.contains("logout")){
                keepGoing = false;
                boolean confirmation = server.broadcast(username + ": " + "has left the chat");
                if (confirmation == false) {
                    String msg = "Sorry. No such user exists.";
                    this.writeMsg(msg);
                }
                break;
            }
            else {
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
        if(!client.isConnected()) {
           // close();
            return false;
        }
        // write the message to the stream

            writer.println(msg);

        return true;
    }

    synchronized private void soutLog(String logS){
        Lobby.clientsLogTxtAr.appendText(logS);
    }
}
