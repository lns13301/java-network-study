import client.ReceiveThread;
import client.SendThread;
import client.TCPClient;
import server.TCPServer;

import java.io.IOException;
import java.net.Socket;

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
