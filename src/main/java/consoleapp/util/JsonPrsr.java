package util;

import model.Accommodation;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonPrsr {

  private static JSONObject parser(String jsonFileName) {
    try {
      String content = new String(Files.readAllBytes(Paths.get(jsonFileName)));
      JSONObject room = new JSONObject(content);

      return room;

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Accommodation getAccomodationObject(String jsonFileName) {
    JSONObject room = parser(jsonFileName);
    return new Accommodation(
        room.getString("roomName"),
        room.getInt("noOfPersons"),
        room.getString("area"),
        room.getInt("stars"),
        room.getInt("noOfReviews"));
  }

  public static void main(String args[]) {
    System.out.println(
        getAccomodationObject("src\\main\\java\\consoleapp\\rooms.json"));

  }
}
