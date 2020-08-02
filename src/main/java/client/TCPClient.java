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
    private int clientChoice;

    public TCPClient() {
        socket = new Socket();
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
            showGameRule();
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
            BufferedReader serverBuf = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;

            while(true) {
                message = tmpBuf.readLine();

                if (message.equals("exit") || message.equals("종료")) {
                    break;
                }

                printWriter.println(message);
                printWriter.flush();

                String receiveMessage = serverBuf.readLine();

                if (message.equals("가위")) {
                    clientChoice += 10;
                }
                else if (message.equals("바위")) {
                    clientChoice += 20;
                }
                else if (message.equals("보")) {
                    clientChoice += 30;
                }

                if (clientChoice > 0) {
                    clientChoice += Integer.parseInt(receiveMessage) + 1;
                    getResultMessage(clientChoice);

                    clientChoice = 0;
                }
            }

            printWriter.close();
            tmpBuf.close();
            disconnectServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getResultMessage(int value) {
        if (value == 11) {
            System.out.println("[클라이언트] 클라이언트 : 가위");
            System.out.println("[클라이언트] AI : 바위");
            System.out.println("[클라이언트] 졌습니다...");
        }
        if (value == 12) {
            System.out.println("[클라이언트] 클라이언트 : 가위");
            System.out.println("[클라이언트] AI : 보");
            System.out.println("[클라이언트] 이겼습니다!");
        }
        if (value == 13) {
            System.out.println("[클라이언트] 클라이언트 : 가위");
            System.out.println("[클라이언트] AI : 가위");
            System.out.println("[클라이언트] 비겼습니다?");
        }
        if (value == 21) {
            System.out.println("[클라이언트] 클라이언트 : 바위");
            System.out.println("[클라이언트] AI : 보");
            System.out.println("[클라이언트] 졌습니다...");
        }
        if (value == 22) {
            System.out.println("[클라이언트] 클라이언트 : 바위");
            System.out.println("[클라이언트] AI : 가위");
            System.out.println("[클라이언트] 이겼습니다!");
        }
        if (value == 23) {
            System.out.println("[클라이언트] 클라이언트 : 바위");
            System.out.println("[클라이언트] AI : 바위");
            System.out.println("[클라이언트] 비겼습니다?");
        }
        if (value == 31) {
            System.out.println("[클라이언트] 클라이언트 : 보");
            System.out.println("[클라이언트] AI : 가위");
            System.out.println("[클라이언트] 졌습니다...");
        }
        if (value == 32) {
            System.out.println("[클라이언트] 클라이언트 : 보");
            System.out.println("[클라이언트] AI : 바위");
            System.out.println("[클라이언트] 이겼습니다!");
        }
        if (value == 32) {
            System.out.println("[클라이언트] 클라이언트 : 보");
            System.out.println("[클라이언트] AI : 보");
            System.out.println("[클라이언트] 비겼습니다?");
        }
    }

    public void showGameRule() {
        System.out.println("[클라이언트] 성공적으로 서버와 연결되었습니다.");
        System.out.println("[클라이언트] 서버를 종료하려면 '종료' 혹은 'exit'를 입력하세요.\n");
        System.out.println("[클라이언트] [가위 바위 보] 게임");
        System.out.println("[클라이언트] 채팅으로 '가위' '바위' '보' 중 하나를 입력하세요.\n");
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
