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
//            System.out.println("hello");
//            writer.println("Hello Client joon");
            for (int i = 0; i < Question.questions.size(); i++) {
                writer.println(Question.questions.get(i).getQuest());
                writer.println(Question.questions.get(i).getChoices());
                //timer start
                writer.println("type the number of your choice: ");

                ExecutorService response = Executors.newSingleThreadExecutor();
                answer = 0;
                try{
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                answer = Integer.parseInt(reader.readLine());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    Future<?> f = response.submit(r);
                    f.get(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (TimeoutException e) {
                    System.out.println("TimeOut");

                }catch (ExecutionException e) {
                    e.printStackTrace();
                }
                finally {
                    response.shutdown();
                }

                System.out.println("answer client (" + client.getPort() + ") to question (" + (i+1)+") is: " + answer);
                if(answer == Question.questions.get(i).getAns()){
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
