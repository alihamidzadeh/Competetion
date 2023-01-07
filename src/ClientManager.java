import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class ClientManager implements Runnable {
    Socket client;
    Server server;

    InputStreamReader fromClientStream;
    OutputStreamWriter toClientStream;
    BufferedReader reader;
    PrintWriter writer;
    int answer = 0;


    public ClientManager(Server server, Socket client){
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
        try{

            for (int i = 0; i < Question.questions.size(); i++) {
                writer.println(Question.questions.get(i).getQuest());
                writer.println(Question.questions.get(i).getChoices());
                //timer start
                writer.println("type the number of your choice: ");
                Thread.sleep(15000);
                answer = Integer.parseInt(reader.readLine());

                System.out.println("answer client (" + client.getPort() + ") to question (" + (i+1)+") is: " + answer);
                if(answer == Question.questions.get(i).getAns()){
                    //update score
                    Server.score.put(client.getPort(), Server.score.getOrDefault(client.getPort(), 0) + 1 );
                }
                else{
                    Server.score.put(client.getPort(), Server.score.getOrDefault(client.getPort(), 0));
                }
            }
            while (true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
