package Server_G.Pages;

import Server_G.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static java.lang.System.exit;


public class Lobby {
    public static TextArea clientsLogTxtAr = new TextArea();

    public void start(Stage stage) throws Exception {
        Label label1 = new Label("Logs Room");
        label1.setTextFill(Color.web("#c22d0c"));
        label1.setStyle("-fx-font-family: 'Arial Narrow';\n" +
                "-fx-font-size: 40px;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-text-fill: rgba(0,0,0,.9);\n" +
                "-fx-background-color: white;\n" +
                "-fx-border-color: black;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-alignment: center;\n" +
                "-fx-text-alignment: center;\n" +
                "-fx-effect: dropshadow(gaussian, #b4b4b4, 6,0,0,2);");


        Label label2 = new Label("Live Logs:");
        label2.setTextFill(Color.web("#595556"));
        label2.setStyle("-fx-font-size:20px");
        clientsLogTxtAr.setStyle("-fx-font-size:20px; -fx-text-fill: #c746ff; -fx-font-weight: bold;");
        clientsLogTxtAr.setDisable(false);
        clientsLogTxtAr.setEditable(false);
        clientsLogTxtAr.setMaxSize(600, 700);
        clientsLogTxtAr.setMinSize(400, 100);
//        clientsLogTxtAr.setLayoutX(0);
//        clientsLogTxtAr.setLayoutY(0);

        Button backBtn = new Button("back");

        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-size: 100% 100%");

//        root.setStyle("-fx-background-image: url('https://i.pinimg.com/originals/cf/4e/7e/cf4e7ef82f683fcc564d78e786511559.gif'); -fx-background-size: 100% 100%");

        VBox vbox = new VBox(12);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label1, label2, clientsLogTxtAr, backBtn);
        int stageWidth = 800;
        int stageHeight = 550;
        vbox.setLayoutX((stageWidth / 2) - (stageWidth / 2.7));
        vbox.setLayoutY(20);
        root.getChildren().add(vbox);
        Scene scene1 = new Scene(root, stageWidth, stageHeight);
        stage.setScene(scene1);
        stage.setAlwaysOnTop(false);
        stage.setTitle("Server Logs");
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.setX(200);
        stage.setY(300);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Lobby window is closing");
                stage.close();
                exit(0);
            }
        });

        class serverThread extends Thread {
            @Override
            public void run() {
                try {
                    Server server = new Server(8081, "host");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        serverThread t = new serverThread();
        t.start();
        backBtn.setOnAction(actionEvent -> {
            S_Graphic graphics = new S_Graphic();
            try {
                t.stop();
                if (Server.socket != null) {
                    Server.socket.close();
                }
//                String log = "------------------------------------------------------------\n";
//                clientsLogTxtAr.appendText(log);
                for (int i = 0; i < Server.clientList.size(); i++) {
                    Server.threadList.get(i).stop();
//                    log = String.format("client %d has disconnected!\n", Server.clientList.get(i).getPort());
//                    clientsLogTxtAr.appendText(log);
                }
                graphics.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
