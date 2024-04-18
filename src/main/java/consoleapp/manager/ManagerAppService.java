package manager;

import model.Accommodation;
import model.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ManagerAppService {

    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ManagerAppService(ObjectInputStream input, ObjectOutputStream output) {
        this.input = input;
        this.output = output;
    }

    public  void addAccommodation(String jsonPath) throws IOException {
        Accommodation accommodation = new Accommodation();
        accommodation.setRoomName("Room");
        Request request = new Request("ADD", accommodation);
        output.writeObject(request);
    }

    public  void addAvailableDate(String date) {
        System.out.println(String.format("DEBUG: Added available date for %s.", date.toString()));
    }

    public  void addAvailableDates(String startDate, String endDate) {
        System.out.println(
                String.format("DEBUG: Added available dates for %s - %s.", startDate.toString(), endDate.toString()));
    }

    public  void addAvailableDates(String dates) {
        System.out.println(String.format("DEBUG: Added available dates for %s.", dates.toString()));
    }

    public  void getReservations() {
        System.out.println("DEBUG: Reservations here.");
    }

}
