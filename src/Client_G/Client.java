package Client_G;

import Client_G.Pages.Chat;
import Client_G.Pages.Lobby;
import Client_G.Pages.ScoreBoard;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class Client {
    public static Socket socket;
    private int Sport;
    private int Cport;
    private static String CUserName;

    InputStreamReader toServerStream;
    OutputStreamWriter fromServerStream;
    BufferedReader reader;
    PrintWriter writer;
    public static int answer;
    String hostAddress = "127.0.0.1";
    boolean keepChatting = false;
    public static boolean chatPermit = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public Client(int Cport, String Cname, int Sport) throws InterruptedException {
        setCPort(Cport);
        setCUserName(Cname);
        setSPort(Sport);

        try {
            socket = new Socket(hostAddress, getSPort(), InetAddress.getByName(hostAddress), getCPort() );
            Platform.runLater(() -> {
                Lobby.label2.setText(this.getCUserName());
            });
            fromServerStream = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            toServerStream = new InputStreamReader(socket.getInputStream(), "UTF-8");
            reader = new BufferedReader(toServerStream);
            writer = new PrintWriter(fromServerStream, true);
            String quiz;
            writer.println(this.getCUserName());
            int numberOfQuestions = Integer.parseInt(reader.readLine());
            int qDuration = Integer.parseInt(reader.readLine());
            int clientNumber = Integer.parseInt(reader.readLine());

            for (int i = 0; i < numberOfQuestions; i++) {
                chatPermit = false;
                Platform.runLater(() -> {
                    Lobby.setClicked(false);
                    Lobby.showBtns(true);
                    Lobby.clientAns.setVisible(false);
                    Lobby.chatAlarm.setVisible(false);
                });
                answer = 0;
                quiz = "شماره سوال: " + (i + 1) + " از " + numberOfQuestions + "\n";
                quiz += reader.readLine() + "\n";
                //choices:
                quiz += "1:   " + reader.readLine() + "           " + "2:   " + reader.readLine() + "\n";
                quiz += "3:   " + reader.readLine() + "           " + "4:   " + reader.readLine();
                String finalQuiz = quiz;
                Platform.runLater(() -> {
                    Lobby.setQuiz(finalQuiz);
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
        String[] data;
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

    private void display(String msg) {
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

    public int getCPort() {
        return Cport;
    }

    public void setCPort(int port) {
        this.Cport = port;
    }

    public static String getCUserName() {
        return CUserName;
    }

    public void setCUserName(String userName) {
        CUserName = userName;
    }

    public int getSPort() {
        return Sport;
    }

    public void setSPort(int port) {
        this.Sport = port;
    }

}