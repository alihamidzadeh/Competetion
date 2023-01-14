package Client_G.Pages;
//
//import javafx.stage.Stage;
//
//public class ScoreBoard {
//
//    public void start(Stage stage) throws Exception{
//
//    }
//}

import Client_G.C_Graphic;
import Client_G.Client;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class ScoreBoard  {
    private static boolean run = false;
    private TableView table = new TableView();
    private ObservableList<Record> records;

    public ScoreBoard(Record[] recordlist) {
        records =
                FXCollections.observableArrayList(
                        recordlist
                );
    }
    public ScoreBoard(){

    }

    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Score Board");
        stage.setWidth(300);
        stage.setHeight(550);

        final Label label = new Label("Users Score");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn usernameCol = new TableColumn("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<String, Integer>("Username"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<String, Integer>("Score"));

        usernameCol.setMinWidth(110);
        scoreCol.setMinWidth(140);

        table.setItems(records);

        table.getColumns().addAll(usernameCol, scoreCol);
        Button backBtn = new Button("Back");

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, backBtn);
        vbox.setAlignment(Pos.CENTER);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);

        if (!run) {
            run = true;
            stage.show();
        }
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                run = false;
                stage.close();
            }
        });

        backBtn.setOnAction(actionEvent -> {
            run = false;
            stage.close();
        });
    }

    public static class Record {
        private String username;
        private int score;

        public Record(String username, int score) {
            this.username = username;
            this.score = score;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}

