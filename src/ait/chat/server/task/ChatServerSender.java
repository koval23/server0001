package ait.chat.server.task;

import ait.mediation.BlkQueue;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChatServerSender implements Runnable {
//todo Этот класс отвечает за отправку сообщений клиентам.
//     Он также работает в своем потоке
    private BlkQueue<String> messageBox;
    private Set<PrintWriter> clients;

    public ChatServerSender(BlkQueue<String> messageBox) {
        this.messageBox = messageBox;
        clients = new HashSet<>();
    }

    public synchronized boolean addClient(Socket socket) throws IOException {
        return clients.add(new PrintWriter(socket.getOutputStream(), true));
    }

    @Override
    public void run() {
        while (true) {
            String message = messageBox.pop();
            synchronized (this) {
                Iterator<PrintWriter> iterator = clients.iterator();
                while (iterator.hasNext()) {
                    PrintWriter clientWriter = iterator.next();
                    if (clientWriter.checkError()) {
                        iterator.remove();
                    } else {
                        clientWriter.println(message);
                    }
                }
            }

        }
    }
}
