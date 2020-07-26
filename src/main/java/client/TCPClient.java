package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private Socket socket;

    public TCPClient() {
        socket = new Socket();
    }

    public void connectServer() {
        try {
            socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectServer() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
