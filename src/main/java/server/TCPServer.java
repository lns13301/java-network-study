package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TCPServer extends Thread{
    private static final int SERVER_PORT = 12345;
    private ServerSocket serverSocket;
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

        Socket clientSocket = waitClientConnection();

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
                    System.out.println("상대방과 연결이 끊어졌습니다.");
                    break;
                }
                else {
                    System.out.println("[채팅 서버] 클라이언트 : " + message);

                    if (message.equals("가위") || message.equals("바위") || message.equals("보")) {
                        showGameResult();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket waitClientConnection() {
        try {
            Socket socket = serverSocket.accept();
            InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
            String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
            int remoteHostPort = remoteSocketAddress.getPort();

            System.out.println("[server] connected! \nconnected socket address:" + remoteHostName
                    + ", port:" + remoteHostPort);
            System.out.println("서버를 종료하려면 '종료' 혹은 'exit'를 입력하세요.\n");
            System.out.println("[가위 바위 보] 게임");
            System.out.println("채팅으로 '가위' '바위' '보' 중 하나를 입력하세요.\n");

            return socket;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public RockScissorsPaperResult showGameResult() {
        int value = ThreadLocalRandom.current().nextInt(3);

        switch (value) {
            case 0:
                System.out.println("이겼습니다!");
                return RockScissorsPaperResult.Win;
            case 1:
                System.out.println("졌습니다!");
                return RockScissorsPaperResult.Lose;
            case 2:
                System.out.println("비겼습니다!");
                return RockScissorsPaperResult.Draw;
            default:
                return null;
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
