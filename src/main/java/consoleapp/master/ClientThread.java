package master;

import model.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            while (true) {
                Request request = (Request) input.readObject();
                System.out.println("Got the request");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Please god help us");
        }
    }
}
