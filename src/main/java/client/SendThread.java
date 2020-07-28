package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendThread extends Thread{
    private Socket socket;

    @Override
    public void run() {
        super.run();

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
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
