package com.example.chatprogramfx;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

public class ChatClient extends Thread {
    private MainGUI cls;

    public ChatClient(MainGUI cls) {
        this.cls = cls;
    }

    @Override
    public void run() {
        System.out.println("Starting Client");
        while (true) {
            try {
                boolean wait = true;
                while (wait) {
                    try {
                        Socket socket = new Socket("127.0.0.1", 7777);
                        wait = false;

                        InputStream is = socket.getInputStream();
                        DataInputStream dis = new DataInputStream(is);

                        String recieve = dis.readUTF();

                        String[] delim = recieve.split("⍼", 2);
                        String username = delim[0];
                        String msg = delim[1];

                        //Must be inside Platform.runLater since to modify JavaFX GUIs it just be run in a JavaFX thread.
                        Platform.runLater(() -> {
                            cls.receiveMessage(msg, username);
                        });

                        System.out.println("Received: " + msg);
                        socket.close();

                    } catch (ConnectException e) {
                        Thread.sleep(1000);
                        System.out.println("Client waiting for connection");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}