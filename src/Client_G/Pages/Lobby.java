package Client_G.Pages;

import Client_G.Client;
import Client_G.Graphic;
import Server_G.Server;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static java.lang.System.exit;

public class Lobby {
    public static TextArea LogTxtAr = new TextArea();
    public static Label label2;

    public void start(Stage stage) throws Exception {
        Label label1 = new Label("Lobby");
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

        label2 = new Label("Can NOT Connect To Server ...");
        label2.setTextFill(Color.web("#595556"));
        label2.setStyle("-fx-font-size:20px");
        LogTxtAr.setStyle("-fx-font-size:17px; -fx-text-fill: #c746ff; -fx-font-weight: bold;");
        LogTxtAr.setDisable(false);
        LogTxtAr.setEditable(false);
        LogTxtAr.setMaxSize(600, 150);
        LogTxtAr.setMinSize(400, 100);

        Button btn1 = new Button("1");
        Button btn2 = new Button("2");
        Button btn3 = new Button("3");
        Button btn4 = new Button("4");
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        hBox1.getChildren().addAll(btn1,btn2);
        hBox2.getChildren().addAll(btn3,btn4);



        Button backBtn = new Button("back");
        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(#f6fa00, #f6fa00); -fx-background-size: 100% 100%");

//        root.setStyle("-fx-background-image: url('https://i.pinimg.com/originals/cf/4e/7e/cf4e7ef82f683fcc564d78e786511559.gif'); -fx-background-size: 100% 100%");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        vbox.setLayoutX(100);
        vbox.setLayoutY(60);
        vbox.getChildren().addAll(label1, label2, LogTxtAr,hBox1,hBox2, backBtn);
        root.getChildren().add(vbox);
        Scene scene1 = new Scene(root, 500, 300);
        stage.setScene(scene1);
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("");
        stage.alwaysOnTopProperty();
        stage.setAlwaysOnTop(true);
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
                    Client socket = new Client(5230, "client1");
                } catch (Exception e) {
                    System.out.println("Can NOT Connected!");
                    e.printStackTrace();
                }

            }
        }
        serverThread t = new serverThread();
        t.start();

        backBtn.setOnAction(actionEvent -> {
            Client_G.Graphic graphic = new Graphic();
            try {
                t.stop();
                if (Client.socket != null) {
                    Client.socket.close();
                }

                graphic.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
