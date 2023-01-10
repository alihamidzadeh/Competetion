package Server_G;

import Server_G.Pages.Lobby;
import Server_G.Server;
import Datas.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class ClientManager implements Runnable {
    Socket client;
    Server server;

    InputStreamReader fromClientStream;
    OutputStreamWriter toClientStream;
    BufferedReader reader;
    PrintWriter writer;
    int answer = 0;


    public ClientManager(Server server, Socket client) {
        this.server = server;
        this.client = client;

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
                writer.println(Question.questions.get(i).getQuest());
                writer.println(Question.questions.get(i).getChoices());
//                System.out.println(Question.questions.get(i).getChoices());
                //timer start
//                writer.println("type the number of your choice: ");
                answer = Integer.parseInt(reader.readLine());
                logS = String.format("answer client (%d) to question (%d) is: %d\n", client.getPort(), i + 1, answer);
                Lobby.clientsLogTxtAr.appendText(logS);

//                sleep(10000); //TODO 15000
                if (answer == Question.questions.get(i).getAns()) {
                    //update score
                    Server.score.put(client.getPort(), Server.score.getOrDefault(client.getPort(), 0) + 1);
                } else {
                    Server.score.put(client.getPort(), Server.score.getOrDefault(client.getPort(), 0));
                }
            }
//            while (true) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
