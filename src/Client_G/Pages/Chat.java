package Client_G.Pages;

import Client_G.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Chat {
    public static ObservableList<ChatEntry> messages = FXCollections.observableArrayList();
    private TextField textField;
    public static Button sendBtn;
    public static String messageStr;
    public static boolean pw = false;

    public void start(Stage primaryStage) throws InterruptedException {

        textField = new TextField();
        sendBtn = new Button("Send");
        Button logoutBtn = new Button("Logout");
        ListView<ChatEntry> listView = new ListView<>();
        listView.setCellFactory(list -> new ChatCell());
        listView.setItems(messages);
        listView.setStyle("-fx-background-color: #f7e5f7;");

        sendBtn.setOnAction(evt -> {
            if (!textField.getText().trim().equals("")) {
                messages.add(new ChatEntry(textField.getText(), MessageType.LOCAL));
                messageStr = textField.getText();
                pw = true;
                textField.clear();
            } else {
                Stage stage = new Stage();
                Button button = new Button("OK");
                button.setStyle("-fx-background-color: #e22828;");
                VBox vBox = new VBox(10, new Label(" Invalid Text "), button);
                vBox.setAlignment(Pos.CENTER);
                Pane root = new Pane(vBox);
                root.setStyle("-fx-background-color: #a6f0f0; -fx-background-size: 100% 100%");
                button.setOnAction(actionEvent1 -> {
                    stage.close();
                });
                vBox.setLayoutX(60);
                vBox.setLayoutY(5);
                stage.setScene(new Scene(root));
                stage.setWidth(200);
                stage.setHeight(100);
                stage.setAlwaysOnTop(true);
                stage.show();
            }
        });

        logoutBtn.setOnAction(evt -> {
            textField.setText("logout");
            messageStr = textField.getText();
            pw = true;
            primaryStage.close();
        });

        HBox hBox1 = new HBox(2);

        for (int i = 0; i < Lobby.recordsLobbyBackup.length; i++) {
            if (Client.getUserName().equals(Lobby.recordsLobbyBackup[i].getUsername()))
                continue;
            String usrS = Lobby.recordsLobbyBackup[i].getUsername();
            Button usrBtn = new Button(usrS);

            usrBtn.setOnAction(evt -> {
                textField.setText("@"+usrS+" ");
            });

            hBox1.getChildren().add(usrBtn);
        }
        HBox hBox = new HBox(logoutBtn, textField, sendBtn);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        VBox vbox = new VBox(10, hBox1, listView, hBox);
        Scene scene = new Scene(vbox);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle(Lobby.usrTitle);
        primaryStage.show();
    }


    enum MessageType {
        LOCAL, REMOTE
    }


    static class ChatCell extends ListCell<ChatEntry> {

        HBox graphic = new HBox();
        Text message = new Text();

        public ChatCell() {
            graphic.getChildren().add(message);
            getStyleClass().add("custom-list-cell");
        }

        @Override
        public void updateItem(ChatEntry item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty && (item != null)) {
                message.setText(item.textProperty().get());
                if (item.typeProperty().get().equals(MessageType.REMOTE))
                    graphic.setAlignment(Pos.CENTER_LEFT);
                else
                    graphic.setAlignment(Pos.CENTER_RIGHT);
                Platform.runLater(() -> {
                    setGraphic(graphic);
                    graphic.setStyle("-fx-background-color: #f7e5f7;");
                });
            } else {
                message.setText("");
                Platform.runLater(() -> {
                    setGraphic(null);
                });
            }
        }
    }


    public static void receiveMessage(String string) {
        Chat.messages.add(new ChatEntry(string, Chat.MessageType.REMOTE));
    }
}
