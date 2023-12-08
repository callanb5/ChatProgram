package com.example.chatprogramfx;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer extends Thread {
    int port;
    String msg;
    String user;
    public ChatServer(int port, String msg, String user) {
        this.port = port;
        this.msg = msg;
        this.user = user;
    }
    public void run() {
        System.out.println("Starting Server");

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket s1 = serverSocket.accept();
            OutputStream os = s1.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            // I used "⍼" as a delimiter, because it's very unlikely to ever be put into someone's username.
            // We could limit the usernames to ASCII to make sure that nobody breaks the program -CB
            String userAndMsg = user + "⍼" + msg;
            dos.writeUTF(userAndMsg);

            System.out.println("Server sent message: " + msg + " from user: "  + user);
            serverSocket.close();
            s1.close();

        } catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }
}
