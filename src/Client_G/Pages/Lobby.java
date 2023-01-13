package Client_G.Pages;

import Client_G.Client;
import Client_G.C_Graphic;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static java.lang.System.exit;

public class Lobby {
    public static TextArea LogTxtAr = new TextArea();
    public static Label label2;
    private static int choice;
    private static Button btn1;
    private static Button btn2;
    private static Button btn3;
    private static Button btn4;
    private static boolean clicked;
    private static Text clientAns;


    public void start(Stage stage) throws Exception {
        Label label1 = new Label("Quiz Room");
        label1.setTextFill(Color.web("#c22d0c"));
        label1.setStyle("-fx-font-family: 'Arial Narrow';\n" +
                "-fx-font-size: 40px;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-text-fill: rgba(0,0,0,.9);\n" +
                "-fx-background-color: white;\n" +
                "-fx-border-color: #000000;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-alignment: center;\n" +
                "-fx-text-alignment: center;\n" +
                "-fx-effect: dropshadow(gaussian, #b4b4b4, 6,0,0,2);");

        label2 = new Label("Can NOT Connect To Server ...");
        label2.setTextFill(Color.web("#595556"));
        label2.setStyle("-fx-font-size:20px");
        LogTxtAr.setText("Waiting for others...\n");
        LogTxtAr.setStyle("-fx-font-size:17px; -fx-text-fill: #c746ff; -fx-font-weight: bold;");
        LogTxtAr.setDisable(false);
        LogTxtAr.setEditable(false);
//        LogTxtAr.setFont(Font.loadFont("Resources/Font/B-NAZANIN.TTF", 120));
        LogTxtAr.setMaxSize(700, 150);
        LogTxtAr.setMinSize(500, 100);

        btn1 = new Button("1");
        btn2 = new Button("2");
        btn3 = new Button("3");
        btn4 = new Button("4");
        btn1.setStyle("-fx-font-size:15px;-fx-text-fill: #000000;-fx-background-color: #7ad331");
        btn2.setStyle("-fx-font-size:15px;-fx-text-fill: #000000;-fx-background-color: #7ad331");
        btn3.setStyle("-fx-font-size:15px;-fx-text-fill: #000000;-fx-background-color: #7ad331");
        btn4.setStyle("-fx-font-size:15px;-fx-text-fill: #000000;-fx-background-color: #7ad331");
        showBtns(false);

        HBox hBox1 = new HBox(10);
        HBox hBox2 = new HBox(10);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);

        hBox1.getChildren().addAll(btn1, btn2);
        hBox2.getChildren().addAll(btn3, btn4);

        clientAns = new Text();
        clientAns.setVisible(false);
        clientAns.setStyle("-fx-font-size:18px; -fx-font-weight: bold;");

        Button backBtn = new Button("back");
        Pane root = new Pane();
        root.setStyle("-fx-background-color: linear-gradient(#f6fa00, #f6fa00); -fx-background-size: 100% 100%");

//        root.setStyle("-fx-background-image: url('https://i.pinimg.com/originals/cf/4e/7e/cf4e7ef82f683fcc564d78e786511559.gif'); -fx-background-size: 100% 100%");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        vbox.setLayoutX(45);
        vbox.setLayoutY(60);
        vbox.getChildren().addAll(label1, label2, LogTxtAr, hBox1, hBox2, clientAns, backBtn);
        root.getChildren().add(vbox);
        Scene scene1 = new Scene(root, 700, 300);
        stage.setScene(scene1);
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("");
        stage.alwaysOnTopProperty();
        stage.setAlwaysOnTop(true);
        stage.show();

        stage.setOnCloseRequest(we -> {
            System.out.println("Lobby window is closing");
            stage.close();
            exit(0);
        });

        class serverThread extends Thread {
            @Override
            public void run() {
                try {
                    Client socket = new Client(5230, "client1");
                } catch (Exception e) {
                    System.out.println("Can NOT Connect To Server!");
                    e.printStackTrace();
                }
            }
        }
        serverThread t = new serverThread();
        t.start();

        btn1.setOnAction(actionEvent -> {
            clientAns.setText("Your answer is number: 1");
            clientAns.setVisible(true);
            setChoice(1);
        });

        btn2.setOnAction(actionEvent -> {
            clientAns.setText("Your answer is number: 2");
            clientAns.setVisible(true);
            setChoice(2);
        });

        btn3.setOnAction(actionEvent -> {
            clientAns.setText("Your answer is number: 3");
            clientAns.setVisible(true);
            setChoice(3);
        });

        btn4.setOnAction(actionEvent -> {
            clientAns.setText("Your answer is number: 4");
            clientAns.setVisible(true);
            setChoice(4);
        });

        backBtn.setOnAction(actionEvent -> {
            C_Graphic CGraphic = new C_Graphic();
            try {
                t.stop();
                if (Client.socket != null) {
                    Client.socket.close();
                }

                CGraphic.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }


    public void setChoice(int choice) {
        setClicked(true);
        this.choice = choice;
    }

    public static void showBtns(boolean vis) {
        btn1.setVisible(vis);
        btn2.setVisible(vis);
        btn3.setVisible(vis);
        btn4.setVisible(vis);

    }

    public static boolean isClicked() {
        return clicked;
    }

    public static void setClicked(boolean clicked) {
        Lobby.clicked = clicked;
    }

    public static int getChoice() {
        clientAns.setVisible(false);
        return choice;
    }
}
