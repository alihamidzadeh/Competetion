package Client_G.Pages;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChatEntry {
    private final StringProperty text = new SimpleStringProperty("");
    private final ObjectProperty<Chat.MessageType> type = new SimpleObjectProperty<>();

    public ChatEntry(String text, Chat.MessageType type) {
        this.text.set(text);
        this.type.set(type);
    }

    public StringProperty textProperty() {
        return text;
    }

    public ObjectProperty<Chat.MessageType> typeProperty() {
        return type;
    }
}
