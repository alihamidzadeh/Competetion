import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ClientManager implements Runnable {
    Socket client;
    Server server;

    InputStream fromClientStream;
    OutputStream toClientStream;
    DataInputStream reader;
    PrintWriter writer;

    public ClientManager(Server server, Socket client){
        this.server = server;
        this.client = client;

        try {
            fromClientStream = client.getInputStream();
            toClientStream = client.getOutputStream();
            reader = new DataInputStream(fromClientStream);
            writer = new PrintWriter(toClientStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try{
            System.out.println("hello");
            writer.println("Hello Client joon");
            for (int i = 0; i < Question.questions.size(); i++) {
                writer.println(Question.questions.get(i).getQuest().getBytes("UTF-8"));
                writer.println(Question.questions.get(i).getChoices());
                //timer start
                writer.println("type the number of your choice: ");
                int ans = Integer.parseInt(reader.readLine());
                System.out.println("answer: " + ans);
                if(ans == Question.questions.get(i).getAns()){
                    //update score
                }

               // sleep(5);
            }
            while (true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
