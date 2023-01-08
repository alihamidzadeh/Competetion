package Client_G;

import Datas.Question;
import Server_G.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class Client {
    Socket socket;
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

            socket = new Socket(hostAddress, 8082/*, InetAddress.getByName(hostAddress), 5003 /*Client_G.Client Port */);
            System.out.println("Connected to the server!");
            fromServerStream = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            toServerStream = new InputStreamReader(socket.getInputStream(), "UTF-8");
            reader = new BufferedReader(toServerStream);
            writer = new PrintWriter(fromServerStream, true);

            for (int i = 0; i < Question.questions.size(); i++) {
                System.out.println(reader.readLine());
                System.out.println(reader.readLine());
                System.out.println(reader.readLine());

                //            ExecutorService response = Executors.newSingleThreadExecutor();
                /// Scanner input = new Scanner(System.in);
                answer = 0;
//                Thread.sleep(15000);
//                if(input.hasNextInt()){
//                    answer = input.nextInt();
//                }
//                class getAnswer extends Thread {
//                    private int answerNumber;
//
//                    @Override
//                    public void run() {
//                        Scanner input = new Scanner(System.in);
//                         answerNumber = input.nextInt();
//                    }
//                    public void killThread(){
//                        super.stop();
//                    }
//                    public int getAnswer(){
//                        return this.answerNumber;
//                    }
//                }
//
//                getAnswer GAthread = new getAnswer();
//                GAthread.start();
//                GAthread.join(15000);
                Scanner input = new Scanner(System.in);

                ExecutorService response = Executors.newSingleThreadExecutor();
                try{
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
                }
                catch (TimeoutException e) {
                    System.out.println("TimeOut");
                    //    input.close();

                    answer = 0;

                }catch (ExecutionException e) {
                    e.printStackTrace();
                }
                finally {
                    writer.println(answer+"");
                    response.shutdown();
                }
//                answer = GAthread.getAnswer();
//                GAthread.killThread();
                System.out.println(Server.score);
            }
            System.out.println("khar");
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

    public static void main(String[] args) {
        new Client(5230, "client1");
    }
}