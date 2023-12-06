import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer extends Thread {
    int port;
    String msg;
    public ChatServer(int port, String msg) {
        this.port = port;
        this.msg = msg;
    }
    public void run() {
        System.out.println("Starting Server");

        try {
                ServerSocket serverSocket = new ServerSocket(port);
                Socket s1 = serverSocket.accept();
                OutputStream os = s1.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                //This can be changed to take input from the GUI once it's complete
                dos.writeUTF(msg);
                serverSocket.close();
                s1.close();

        } catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }
}
