package ait.chat.server.task;

import ait.mediation.BlkQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatServerReceiver implements Runnable {
//todo Этот класс отвечает за прием сообщений от клиентов.
//     Каждый экземпляр этого класса работает в своем потоке
    private BlkQueue<String> messageBox;
    private Socket socket;

    public ChatServerReceiver(BlkQueue<String> messageBox,Socket socket) {
        this.messageBox = messageBox;
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String message = socketReader.readLine();
                if (message == null) {
                    break;
                }
                messageBox.push(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
