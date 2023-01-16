package Client_G;

import Client_G.Pages.Lobby;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class C_Graphic extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Label titleLB = new Label("User");
        titleLB.setStyle("-fx-font-family: 'Impact';\n" +
                "-fx-font-size: 40px;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-text-fill: rgba(0,0,0,.9);\n" +
                "-fx-background-color: white;\n" +
                "-fx-border-color: black;\n" +
                "-fx-border-width: 7;\n" +
                "-fx-alignment: center;\n" +
                "-fx-text-alignment: center;\n" +
                "-fx-effect: dropshadow(gaussian, #b4b4b4, 6,0,0,2);");

        Button startBtn = new Button("Start");
        startBtn.setTextFill(Color.web("#222224"));
        startBtn.setStyle(" -fx-background-color: #000000,\n" +
                "        linear-gradient(#7ebcea, #2f4b8f),\n" +
                "        linear-gradient(#426ab7, #263e75),\n" +
                "        linear-gradient(#395cab, #223768);\n" +
                "    -fx-background-insets: 0,1,2,3;\n" +
                "    -fx-background-radius: 3,2,2,2;\n" +
                "    -fx-padding: 12 30 12 30;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-font-size: 15px;");

        Button settingBtn = new Button("Setting");
        settingBtn.setTextFill(Color.web("#222224"));
        settingBtn.setStyle(" -fx-background-color: #000000,\n" +
                "        linear-gradient(#7ebcea, #2f4b8f),\n" +
                "        linear-gradient(#426ab7, #263e75),\n" +
                "        linear-gradient(#395cab, #223768);\n" +
                "    -fx-background-insets: 0,1,2,3;\n" +
                "    -fx-background-radius: 3,2,2,2;\n" +
                "    -fx-padding: 12 30 12 30;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-font-size: 15px;");

        Button exitBtn = new Button("Exit");
        exitBtn.setTextFill(Color.web("#222224"));
        exitBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\n" +
                "    -fx-background-radius: 20;\n" +
                "    -fx-background-insets: 0;\n" +
                "    -fx-text-fill: white;\n" +
                "     -fx-font-size: 20px;");


        //set Scene, Pane, ...
        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(#f6fa00, #f6fa00); -fx-background-size: 100% 100%");

//        try {
//            root.setStyle("-fx-background-image: url('https://i.pinimg.com/originals/cf/4e/7e/cf4e7ef82f683fcc564d78e786511559.gif'); -fx-background-size: 100% 100%");
//        }catch (Exception exception){
//            exception.printStackTrace();
//        }


        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        int stageWidth = 400;
        int stageHeight = 400;
        vbox.setLayoutX((stageWidth / 2) - (stageWidth / 6));
        vbox.setLayoutY(20);
        vbox.getChildren().addAll(titleLB, startBtn,settingBtn, exitBtn);
        root.getChildren().add(vbox);
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.setTitle("Quiz Program");
        stage.setResizable(true);
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.setX(400);
        stage.setY(50);
        stage.setAlwaysOnTop(true);
        stage.show();


        //set Action for botton
        startBtn.setOnAction(actionEvent -> {
            Lobby lobby = new Lobby();
            try {
                lobby.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        exitBtn.setOnAction(actionEvent -> {
            stage.close();
            return;
        });
    }
}
