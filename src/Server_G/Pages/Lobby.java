package Server_G.Pages;

import Server_G.*;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Lobby {
    public static TextArea clientsLogTxtAr = new TextArea();
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


        Label label2 = new Label("Clients:");
        label2.setTextFill(Color.web("#595556"));
        label2.setStyle("-fx-font-size:20px");
        //clientsLogTxtAr.setText("client 1 has coneccted!\nclient 1 has coneccted!\nclient 1 has coneccted!\n");   //TODO
        clientsLogTxtAr.setStyle("-fx-font-size:20px; -fx-text-fill: #c746ff; -fx-font-weight: bold;");
        clientsLogTxtAr.setDisable(true);
        clientsLogTxtAr.setEditable(false);
        clientsLogTxtAr.setMaxSize(400, 400);
        clientsLogTxtAr.setMinSize(400, 40);


        Button backBtn = new Button("back");
        backBtn.setOnAction(actionEvent -> {
            Graphic graphics = new Graphic();
            try {
                graphics.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });


        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-size: 100% 100%");

//        root.setStyle("-fx-background-image: url('https://i.pinimg.com/originals/cf/4e/7e/cf4e7ef82f683fcc564d78e786511559.gif'); -fx-background-size: 100% 100%");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        vbox.setLayoutX((primaryScreenBounds.getMaxX() / 2) - (primaryScreenBounds.getMaxX() / 3));
        vbox.setLayoutY(60);
        vbox.getChildren().addAll(label1, label2, clientsLogTxtAr, backBtn);
        root.getChildren().add(vbox);
        Scene scene1 = new Scene(root, 500, 300);
        stage.setScene(scene1);
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("");
        stage.alwaysOnTopProperty();
        stage.setAlwaysOnTop(true);
        stage.show();
        Server server = new Server(8081, "host");

    }
}
