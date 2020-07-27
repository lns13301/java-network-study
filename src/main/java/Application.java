import client.TCPClient;
import server.TCPServer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        TCPServer tcpServer = new TCPServer();

        tcpServer.waitClientConnection();
    }
}
