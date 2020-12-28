import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    /** Current game being played. */
    private static Game GAME;
    public static void main(String[] args) {
        GAME = getGame();
        GAME.play();
    }
    // TODO: Allow for command options for customization in the future.
    /** Return an appropriate Game. */
    private static Game getGame() {
        return new Game();
    }

    /** Print the contents of the resource named NAME on the standard error. */
    public static void printResource(String name) {
        try {
            InputStream resourceStream =
                    Main.class.getClassLoader().getResourceAsStream(name);
            BufferedReader str =
                    new BufferedReader(new InputStreamReader(resourceStream));
            for (String s = str.readLine(); s != null; s = str.readLine())  {
                System.err.println(s);
            }
            str.close();
        } catch (IOException excp) {
            System.out.println("No help found.");
        } catch (NullPointerException e) {
            name = name.replace("src/", "");
            printResource(name);
        }
    }
}
