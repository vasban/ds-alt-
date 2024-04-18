package worker;

import model.Accommodation;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Worker {

    private final String host;
    private final int port;
    private final ArrayList<Accommodation> accommodations = new ArrayList<>();

    public static void main(String [] args) {
        Worker worker = new Worker(args[0], Integer.parseInt(args[1]));
        worker.run();
    }

    public Worker(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() {
        try (Socket requestSocket = new Socket(host,port);
             ObjectOutputStream output = new ObjectOutputStream(requestSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(requestSocket.getInputStream())
        ) {
            Accommodation accommodation = (Accommodation) input.readObject();
            accommodations.add(accommodation);
        } catch(Exception exception) {
            System.out.println("Unexpected exception");
        }
    }
}
