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
                Platform.runLater(() -> {
                    Lobby.chatBtn.setVisible(false);
                    Lobby.setClicked(false);
                    Lobby.showBtns(true);
                    Lobby.clientAns.setVisible(false);
                });
                answer = -1;
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
                }
                if (answer == -1) {
                    answer = 0;
                }
                writer.println(answer + "");

                receiveScores(clientNumber);

                Messaging();
            }
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
            Thread.sleep(700);
            str = reader.readLine();
            Thread.sleep(500);
            data = str.split(" ");
            System.out.println("data 1: " + data[0] + " --- data 2: " + data[1] + "\n");
            records[i] = new ScoreBoard.Record(data[0], Integer.parseInt(data[1]));
        }

        Platform.runLater(() -> {
            Lobby.recordsLobbyBackup = records;
            ScoreBoard scoreBoard = new ScoreBoard(records);
            Stage stage = new Stage();
            scoreBoard.start(stage);
        });


    }

    private void display(String msg) {
        System.out.println(msg);
    }

    //TODO
    void sendMessage(String msg) {
        writer.println(msg);
    }

    public void Messaging() throws InterruptedException {
        Platform.runLater(() -> {
            Lobby.chatBtn.setVisible(true);
        });
        keepChatting = true;
//        Scanner input = new Scanner(System.in);
        ListenFromServer listenThread = new ListenFromServer();
        listenThread.start();
        System.out.println("Before Input");

        while (keepChatting) {
            //Todo set a statements for get message from graphic
            if (Chat.textField == null || Chat.sendBtn == null) {
                System.out.println("shiiiit");
                continue;
            }
            if (!Chat.sendBtn.isPressed()) {
                System.out.println("NAaaa");
                continue;
            }
            if (!Chat.pw){
                System.out.println("sokhti");
                continue;
            }
            String msg = Chat.textField.getText();
            Chat.pw = false;
            System.out.println("After Input\n" + msg);
            msg = msg.trim();
            if (msg.equals("\n"))
                continue;
            // logout if message is LOGOUT
            if (msg.contains("logout")) {
                listenThread.stopT();
//                input.close();
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