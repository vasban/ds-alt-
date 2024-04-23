package manager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ManagerApp {

    private static final String DEFAULT_MASTER_HOST = "localhost";
    private static final int DEFAULT_MASTER_PORT = 50000;

    private ManagerAppService managerAppService;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private Scanner scanner;

    public static void main(String[] args) {
        ManagerApp managerApp = new ManagerApp();
        managerApp.init();
    }

    private void init() {
        try (Socket requestSocket = new Socket(DEFAULT_MASTER_HOST, DEFAULT_MASTER_PORT)) {
            output = new ObjectOutputStream(requestSocket.getOutputStream());
            input = new ObjectInputStream(requestSocket.getInputStream());
            managerAppService = new ManagerAppService(input, output);
            mainMenu();
        } catch (Exception exception) {
            System.out.println("Unexpected exception");
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (Exception exception) {
                System.out.println("Failure while closing streams");
            }
        }
    }

    private void mainMenu() throws IOException {
        scanner = new Scanner(System.in);
        boolean isLoop = true;
        while (isLoop) {
            System.out.println("Choose from the following options");
            System.out.println("-------------------------\n");
            System.out.println("1 - Register new accommodation");
            System.out.println("2 - Register available dates");
            System.out.println("3 - View registered reservations for your accommodations");
            System.out.println("4 - Quit");

            int selection = Integer.parseInt(scanner.nextLine());
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
                    isLoop = false;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    private void addAccommodation() throws IOException {
        System.out.println("Insert path to json file:\n");
        String jsonPath = scanner.nextLine();
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
            selection = Integer.parseInt(scanner.nextLine());

            switch (selection) {
                case 1:
                    System.out.println("Insert date (DD-MM-YYYY):\n");
                    String date = scanner.nextLine();
                    managerAppService.addAvailableDate(date);
                    break;
                case 2:
                    String startDate, endDate;
                    System.out.println("Insert start date (DD-MM-YYYY):\n");
                    startDate = scanner.nextLine();
                    System.out.println("Insert end date (DD-MM-YYYY):\n");
                    endDate = scanner.nextLine();
                    managerAppService.addAvailableDates(startDate, endDate);
                    break;
                case 3:
                    System.out.println("Insert dates (DD-MM-YYYY) seperated by commas (\",\"):\n");
                    String dates = scanner.nextLine();
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
