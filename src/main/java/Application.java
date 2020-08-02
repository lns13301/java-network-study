import client.TCPClient;
import server.TCPServer;

public class Application {
    public static void main(String[] args) {
        try {
            (new TCPServer()).start();
            Thread.sleep(1000);
            (new TCPClient()).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
