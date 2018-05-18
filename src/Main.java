import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> data = new ArrayList<>();

        System.out.println("LOL");

        try (Scanner s = new Scanner(
                new File(System.getProperty("user.dir") +
                        "/data/junctions.csv"), "UTF-8" )) {
            // Benutzen Sie das Scanner-Objekt s hier
        } catch (FileNotFoundException e) {
            // junctions.csv wurde nicht gefunden
            System.exit(1);
        }
    }
}
