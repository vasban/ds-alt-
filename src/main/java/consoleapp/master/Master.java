package master;

import model.Accommodation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Master  {

    private static final int PORT = 6969;

    private final int workerNum;
    private final List<Socket> workerConnections = new ArrayList<>();

    public static void main(String[] args) {
        Master master = new Master(Integer.parseInt(args[0]));
        master.openServer();
    }

    public Master(int workerNum) {
        this.workerNum = workerNum;
    }

    public void openServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            for (int i = 0; i < workerNum; i++) {
                Socket socket = serverSocket.accept();
                workerConnections.add(socket);
                System.out.println("A new worker has connected!");
            }

            for (Socket socket: workerConnections) {
                Accommodation accommodation = new Accommodation();
                accommodation.setRoomName("Room");
                new ObjectOutputStream(socket.getOutputStream()).writeObject(accommodation);
            }

            while(true) {
                try  {
                    Socket socket = serverSocket.accept();
                    ClientThread thread = new ClientThread(socket);
                    thread.start();
                } catch (Exception exception) {
                    System.out.println("socket issue");
                }
            }
        } catch (IOException io) {
            System.out.println("Unexpected exception");
        }
    }
}
