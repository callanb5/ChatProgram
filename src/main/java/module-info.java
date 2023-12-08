module com.example.chatprogramfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatprogramfx to javafx.fxml;
    exports com.example.chatprogramfx;
}