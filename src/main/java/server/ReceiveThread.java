package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread{
    private Socket socket;

    @Override
    public void run() {
        super.run();

        try {
            BufferedReader tmpBuf = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;

            while (true) {
                message = tmpBuf.readLine();

                if (message == null) {
                    System.out.println("상대방과 연결이 끊어졌습니다.");
                    break;
                }
                else {
                    System.out.println("상대방 : " + message);
                }
            }

            tmpBuf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSocket (Socket socket) {
        this.socket = socket;
    }
}
