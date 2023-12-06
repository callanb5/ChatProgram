public class Main {
    public static void main(String[] args) {
            ChatServer server = new ChatServer(7777, "Hello World!");
            server.start();
    }

}
