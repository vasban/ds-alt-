package manager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ManagerApp {

    private Scanner input;

    private final String host;
    private final int port;
    private ManagerAppService managerAppService;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public static void main(String[] args) {
        ManagerApp managerApp = new ManagerApp(args[0], Integer.parseInt(args[1]));
        managerApp.start();
    }

    public ManagerApp(String host, int port){
        this.host = host;
        this.port = port;
    }

    private void start() {
        try (Socket requestSocket = new Socket(host,port)) {
            objectOutputStream = new ObjectOutputStream(requestSocket.getOutputStream());
            objectInputStream = null;
            managerAppService = new ManagerAppService(objectInputStream, objectOutputStream);
            mainMenu();
        } catch(Exception exception) {
            System.out.println("Unexpected exception");
        } finally {
            try {
                objectInputStream.close();
                objectOutputStream.close();
            } catch (Exception exception) {
                System.out.println("Soy milk");
            }
        }
    }

    private void mainMenu() throws IOException {
        input = new Scanner(System.in);
        int selection;
        boolean isLoop = true;
        while (isLoop) {
            isLoop = false;

            System.out.println("Choose from the following options");
            System.out.println("-------------------------\n");
            System.out.println("1 - Register new accommodation");
            System.out.println("2 - Register available dates");
            System.out.println("3 - View registered reservations for your accommodations");
            System.out.println("4 - Quit");

            selection = Integer.parseInt(input.nextLine());
            switch (selection) {
                case 1:
                    addAccommodation();
                    break;
                case 2:
                    addAvailableDates();
                    break;
                case 3:
                    getReservations();
                    break;
                case 4:
                    System.out.println("Closing application.");
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    isLoop = true;
                    break;
            }
        }
    }

    private void addAccommodation() throws IOException {
        System.out.println("Insert path to json file:\n");
        String jsonPath = input.nextLine();
        managerAppService.addAccommodation(jsonPath);
        mainMenu();
    }

    private void addAvailableDates() throws IOException {
        int selection;
        boolean isLoop = true;
        while (isLoop) {
            isLoop = false;

            System.out.println("Choose from the following options");
            System.out.println("-------------------------\n");
            System.out.println("1 - Enter a single date");
            System.out.println("2 - Enter a range of dates");
            System.out.println("3 - Enter dates seperated by commas(\",\")");
            System.out.println("4 - Return");
            selection = Integer.parseInt(input.nextLine());

            switch (selection) {
                case 1:
                    System.out.println("Insert date (DD-MM-YYYY):\n");
                    String date = input.nextLine();
                    managerAppService.addAvailableDate(date);
                    break;
                case 2:
                    String startDate, endDate;
                    System.out.println("Insert start date (DD-MM-YYYY):\n");
                    startDate = input.nextLine();
                    System.out.println("Insert end date (DD-MM-YYYY):\n");
                    endDate = input.nextLine();
                    managerAppService.addAvailableDates(startDate, endDate);
                    break;
                case 3:
                    System.out.println("Insert dates (DD-MM-YYYY) seperated by commas (\",\"):\n");
                    String dates = input.nextLine();
                    managerAppService.addAvailableDate(dates);
                    break;
                case 4:
                    System.out.println("Go back.");
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    isLoop = true;
            }
        }
        mainMenu();
    }

    private void getReservations() throws IOException {
        managerAppService.getReservations();
        System.out.println();
        mainMenu();
    }
}
