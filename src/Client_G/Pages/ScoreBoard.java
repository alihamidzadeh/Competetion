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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ScoreBoard extends Application {
    private static boolean run = false;
    private TableView table = new TableView();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Score Board");
        stage.setWidth(300);
        stage.setHeight(550);

        final Label label = new Label("Users Score");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn usernameCol = new TableColumn("Username");
        TableColumn scoreCol = new TableColumn("Score");
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
}