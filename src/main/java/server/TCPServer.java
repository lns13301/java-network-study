package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static final int SERVER_PORT = 12345;
    private ServerSocket serverSocket;
    private String localHostAddress;

    public TCPServer() throws IOException {
        serverSocket = new ServerSocket();

        localHostAddress = InetAddress.getLocalHost().getHostAddress();
        serverSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));

        System.out.println("[server] binding! \naddress:" + localHostAddress + ", port:" + SERVER_PORT);
    }

    public void waitClientConnection() {
        try {
            Socket socket = serverSocket.accept();
            InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
            String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
            int remoteHostPort = remoteSocketAddress.getPort();

            System.out.println("[server] connected! \nconnected socket address:" + remoteHostName
                    + ", port:" + remoteHostPort);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}