package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static server.TCPServer.RockScissorsPaperResult.*;

public class TCPServer extends Thread{
    private static final int SERVER_PORT = 12345;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private String localHostAddress;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket();

            localHostAddress = "127.0.0.1"; //InetAddress.getLocalHost().getHostAddress();
            serverSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));

            System.out.println("[server] binding! \naddress:" + localHostAddress + ", port:" + SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        waitClientConnection();

        BufferedReader tmpBuf = null;

        try {
            tmpBuf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String message;

        while (true) {
            try {
                message = tmpBuf.readLine();

                if (message == null) {
                    System.out.println("[서버] 상대방과 연결이 끊어졌습니다.");
                    break;
                }
                else {
                    System.out.println("[서버] 클라이언트 : " + message);

                    sendToClientGameResult(showGameResult(message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        closeServer();
    }

    public void waitClientConnection() {
        try {
            clientSocket = serverSocket.accept();
            InetSocketAddress remoteSocketAddress = (InetSocketAddress)clientSocket.getRemoteSocketAddress();
            String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
            int remoteHostPort = remoteSocketAddress.getPort();

            System.out.println("[server] connected! \nconnected socket address:" + remoteHostName
                    + ", port:" + remoteHostPort);
            System.out.println("\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int showGameResult(String message) {
        if (!(message.equals("가위") || message.equals("바위") || message.equals("보"))) {
            return -1;
        }

        int value = ThreadLocalRandom.current().nextInt(3);

        switch (value) {
            case 0:
                System.out.println(getAIChoice(value, message));
                System.out.println("[서버] 이겼습니다!");
                return 0;
            case 1:
                System.out.println(getAIChoice(value, message));
                System.out.println("[서버] 졌습니다...");
                return 1;
            case 2:
                System.out.println(getAIChoice(value, message));
                System.out.println("[서버] 비겼습니다?");
                return 2;
        }

        return -1;
    }

    public String getAIChoice(int type, String message) {
        if (type == 0) {
            switch (message) {
                case "가위":
                    return "[서버] AI : 바위";
                case "바위":
                    return "[서버] AI : 보";
                case "보":
                    return "[서버] AI : 가위";
            }
        }
        else if (type == 2) {
            switch (message) {
                case "가위":
                    return "[서버] AI : 가위";
                case "바위":
                    return "[서버] AI : 바위";
                case "보":
                    return "[서버] AI : 보";
            }
        }
        else {
            switch (message) {
                case "가위":
                    return "[서버] AI : 보";
                case "바위":
                    return "[서버] AI : 가위";
                case "보":
                    return "[서버] AI : 바위";
            }
        }

        return "";
    }

    public void sendToClientGameResult(int result) {
        try {
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());

            printWriter.println(result);
            printWriter.flush();
        } catch (IOException e) {
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

    public enum RockScissorsPaperResult {
        Win(),
        Lose(),
        Draw()
    }
}
