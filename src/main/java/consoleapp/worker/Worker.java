package worker;

import model.Accommodation;
import model.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Worker extends Thread {

    private final int port;
    private final ArrayList<Accommodation> accommodations = new ArrayList<>();

    private Socket masterSocket;

    public Worker(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            this.masterSocket = serverSocket.accept();
            System.out.println("Master is connected to worker with port " + this.port);

            while (true) {
                acceptRequest();
            }
        } catch (IOException io) {
            System.out.println("Failed to initiate server socket for port " + port);
        }
    }

    private void acceptRequest() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(this.masterSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(this.masterSocket.getInputStream());
            Request request = (Request) input.readObject();
            System.out.println(request);
        } catch (IOException exception) {
            System.out.println("acceptRequest io error");
        } catch (ClassNotFoundException exception) {
            System.out.println("acceptRequest class error");
        }
    }
}
