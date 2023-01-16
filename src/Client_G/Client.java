package Client_G;

import Client_G.Pages.Chat;
import Client_G.Pages.Lobby;
import Client_G.Pages.ScoreBoard;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

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
    public static boolean chatPermit = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public Client(int port, String name) throws InterruptedException {
        setPort(port);
        setUserName(name);

        try {
            socket = new Socket(hostAddress, 8082/*, InetAddress.getByName(hostAddress), 5003 /*Client_G.Client Port */);
            Platform.runLater(() -> {
                Lobby.label2.setText(this.getUserName());
            });
            fromServerStream = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            toServerStream = new InputStreamReader(socket.getInputStream(), "UTF-8");
            reader = new BufferedReader(toServerStream);
            writer = new PrintWriter(fromServerStream, true);
            String quiz;
            writer.println(this.getUserName());
            int numberOfQuestions = Integer.parseInt(reader.readLine());
            int qDuration = Integer.parseInt(reader.readLine());
            int clientNumber = Integer.parseInt(reader.readLine());

            for (int i = 0; i < numberOfQuestions; i++) {
                chatPermit = false;
                Platform.runLater(() -> {
                    Lobby.setClicked(false);
                    Lobby.showBtns(true);
                    Lobby.clientAns.setVisible(false);
                });
                answer = 0;
                quiz = "شماره سوال: " + (i + 1) + " از " + numberOfQuestions + "\n";
                // System.out.println(sdf.format(new Date()));

                quiz += reader.readLine() + "\n";
                //choices:
                quiz += "1:   " + reader.readLine() + "           " + "2:   " + reader.readLine() + "\n";
                quiz += "3:   " + reader.readLine() + "           " + "4:   " + reader.readLine();
//                System.out.println(quiz);
                String finalQuiz = quiz;
                Platform.runLater(() -> {
                    Lobby.LogTxtAr.setText(finalQuiz);
                });
                try {
                    Thread.sleep(qDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Lobby.isClicked()) {
                    answer = Lobby.getChoice();
                } else {
                    answer = 0;
                }
                writer.println(answer + "");

                receiveScores(clientNumber);
                chatPermit = true;
                Messaging();
            }

            chatPermit = false;
            Thread.sleep(1000);
            Platform.runLater(() -> {
                Lobby.LogTxtAr.setText("Finished ...");
                Lobby.showBtns(false);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveScores(int num) throws IOException, InterruptedException {
        ScoreBoard.Record[] records = new ScoreBoard.Record[num];
        String str;
        String[] data = new String[2];
        for (int i = 0; i < num; i++) {
            Thread.sleep(500);
            str = reader.readLine();
            data = str.split(" ");
            records[i] = new ScoreBoard.Record(data[0], data[1]);
        }

        Platform.runLater(() -> {
            Lobby.chatAlarm.setVisible(true);
            Lobby.recordsLobbyBackup = records;
            ScoreBoard scoreBoard = new ScoreBoard(records);
            Stage stage = new Stage();
            scoreBoard.start(stage);
        });


    }

    //TODO
    private void display(String msg) {
//        System.out.println(msg);
        Chat.receiveMessage(msg);
    }

    void sendMessage(String msg) {
        writer.println(msg);
    }

    public void Messaging() throws InterruptedException {
        keepChatting = true;
        ListenFromServer listenThread = new ListenFromServer();
        listenThread.start();

        while (keepChatting) {
            if (!Chat.pw) {
                Thread.sleep(300);
                continue;
            }
            Chat.pw = false;
            String msg = Chat.messageStr;
            msg = msg.trim();
            if (msg.equals("\n"))
                continue;
            if (msg.contains("logout")) {
                listenThread.stopT();
                keepChatting = false;
                this.sendMessage("logout");
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
                    // read the message form the input data stream
                    String msg = reader.readLine();
                    display(msg);
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

    private void logout() {

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