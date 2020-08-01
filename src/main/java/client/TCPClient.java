package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient extends Thread {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private Socket socket;

    public TCPClient() {
        socket = new Socket();
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
            sendToServer();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer() {
        try {
            BufferedReader tmpBuf = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            String message;

            while(true) {
                message = tmpBuf.readLine();
                if (message.equals("exit")) {
                    break;
                }

                printWriter.println(message);
                printWriter.flush();
            }

            printWriter.close();
            tmpBuf.close();
            disconnectServer();
        } catch (IOException e) {
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
