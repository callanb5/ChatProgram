package com.example.chatprogramfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainGUI extends Application {
    int port = 7777;
    private String appName = "Chat Program";
    private Stage newStage = new Stage();
    private Button sendMessage;
    private TextField messageBox;
    private TextArea chatBox;
    private TextField usernameChooser;
    private Stage preStage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        preStage = new Stage();
        preStage.setTitle(appName);

        usernameChooser = new TextField();
        Label chooseUsernameLabel = new Label("Pick a username:");
        Button enterServer = new Button("Enter Chat Server");
        enterServer.setOnAction(e -> enterServerButtonAction());

        GridPane prePanel = new GridPane();
        prePanel.setHgap(10);
        prePanel.setVgap(10);
        prePanel.setPadding(new Insets(10));

        prePanel.add(chooseUsernameLabel, 0, 0);
        prePanel.add(usernameChooser, 1, 0);
        prePanel.add(enterServer, 1, 1);

        Scene preScene = new Scene(prePanel, 300, 100);
        preStage.setScene(preScene);
        preStage.show();
    }

    //Added a bit to this where it lets ClientChat know the current instance. It needs this to know where to run methods. -CB
    private void enterServerButtonAction() {
        String username = usernameChooser.getText();
        if (username.length() < 1) {
            System.out.println("No!");
        } else {
            preStage.close();
            MainGUI currentGUI = this;
            ChatClient chatclient = new ChatClient(currentGUI);
            chatclient.start();
            display();
        }
    }
    private void display() {
        BorderPane mainPane = new BorderPane();

        HBox bottomPane = new HBox(10);
        bottomPane.setPadding(new Insets(10));
        bottomPane.setStyle("-fx-background-color: blue;");

        messageBox = new TextField();
        messageBox.setPromptText("Type your message");
        messageBox.requestFocus();

        sendMessage = new Button("Send Message");
        sendMessage.setOnAction(e -> sendMessageButtonAction());

        chatBox = new TextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", 15));

        mainPane.setCenter(new ScrollPane(chatBox));

        bottomPane.getChildren().addAll(messageBox, sendMessage);
        mainPane.setBottom(bottomPane);

        Scene scene = new Scene(mainPane, 470, 300);
        newStage.setTitle(appName);
        newStage.setScene(scene);
        newStage.setOnCloseRequest(event -> Platform.exit());
        newStage.show();
    }

    //Made it start a ChatServer thread when sendMessageButtonAction is run -CB
    private void sendMessageButtonAction() {
        if (messageBox.getText().length() < 1) {

        } else if (messageBox.getText().equals(".clear")) {
            chatBox.setText("Cleared all messages\n");
            messageBox.setText("");
        } else {
            //The message will already be displayed through ChatClient, which is why I commented the below out. -CB
            //chatBox.appendText("<" + usernameChooser.getText() + ">:  " + messageBox.getText() + "\n");
            ChatServer cs = new ChatServer(port,messageBox.getText(),usernameChooser.getText());
            cs.start();
            messageBox.setText("");
        }
        messageBox.requestFocus();
    }

    //Added this method for ChatClient to use -CB
    public void receiveMessage(String msg, String user) {
     chatBox.appendText("<" + user + ">:  " + msg + "\n");   
    }
}