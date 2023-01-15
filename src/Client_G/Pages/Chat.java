package Client_G.Pages;


import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Chat {
    public static ObservableList<ChatEntry> messages = FXCollections.observableArrayList();
    public static TextField textField;
    public static Button sendBtn;

    public void start(Stage primaryStage) throws InterruptedException {
        textField = new TextField();
        sendBtn = new Button("Send");
        ListView<ChatEntry> listView = new ListView<>();
        listView.setCellFactory(list -> new ChatCell());
        listView.setItems(messages);
        sendBtn.setOnAction(evt -> {
            messages.add(new ChatEntry(textField.getText(), MessageType.LOCAL));
            textField.clear();

            setTOClient(textField.getText());
        });

        HBox hBox = new HBox(textField, sendBtn);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        VBox vbox = new VBox(10, listView, hBox);
        Scene scene = new Scene(vbox);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();


//        if (primaryStage.isShowing()){
//            messages.add(new ChatEntry("Hello", MessageType.REMOTE));
//            Thread.sleep(1000);
//            messages.add(new ChatEntry("A", MessageType.REMOTE));
//            Thread.sleep(1000);
//            messages.add(new ChatEntry("B", MessageType.REMOTE));
//            Thread.sleep(1000);
//            messages.add(new ChatEntry("C", MessageType.REMOTE));
//            Thread.sleep(1000);
//        }
    }

    public static String message;
    public static boolean pw = false;

    private void setTOClient(String text) {
        System.out.println("Message received!");
        message = text;
        pw = true;
    }

//    public static String getMessage() {
//        while (pw) {
//            pw = false;
//            return message;
//        }
//        return null;
//    }

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
                setGraphic(graphic);
            } else {
                message.setText("");
                setGraphic(null);
            }
        }
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

    public static void receiveMessage(String string) {
        Chat.messages.add(new ChatEntry(string, Chat.MessageType.REMOTE));
    }
}
