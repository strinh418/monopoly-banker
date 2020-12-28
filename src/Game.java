import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    // TODO: Determine if the right access modifiers are used throughout this class

    // TODO: Add help file with commands
    /** Name of help text resource. */
    static final String HELP_FILE = "src/HelpText.txt";

    /** Describes a command with up to three arguments. */
    private static final Pattern COMMAND_PATN =
            Pattern.compile("(#|\\S+)\\s*(\\S*)\\s*(\\S*)\\s*(\\S*).*");

    /** Determines whether the current game is in session. */
    private boolean playing;

    /** Input source. */
    private Scanner input;

    /** Players in this game. */
    private Player[] players;

    /** Index of the current player. */
    private int playerIndex;

    /** Player who has their current turn. */
    private Player currPlayer;

    /** Properties in this game. Maps name of the property to the Property object. */
    private Map<String, Property> properties;

    /** The starting money in this game. */
    private double startingMoney;

    /** Specifies whether the next line should prompt with player's name. */
    private boolean nextPrompt;

    // TODO: Want the last setting to not be the default
    /** Begins a default game with normal settings:
     *  Starting money: $15 Million
     *  Pass go: $2 Million
     *  Buildings: buildings must be evenly distributed, bought 1 at a time
     *  Jail Penalty: $500 Thousand
     *  Standard Monopoly property cards.
     *  Allow other players to play a command when it's not their turn.
     */
    public Game() {
        startingMoney = 15;
        input = new Scanner(System.in);
        welcome();
        setUpPlayers();
        playerIndex = 0;
        currPlayer = players[0];
    }

    // TODO: Have this welcome also specify what the current settings are and give a chance to update them
    /** Prints a welcome statement to the command line. */
    public void welcome() {
        System.out.println("Welcome to the electronic Monopoly banker! Press ? for a list of valid commands.");
    }


    /** Returns a command from the standard input after prompting the player's name if PROMPT. */
    public String readLine(boolean prompt) {
        prompt(prompt);
        if (input.hasNextLine()) {
            return input.nextLine().trim();
        }
        return null;
    }

    /** Print a prompt. Prints the player's name if PLAYER */
    private void prompt(boolean player) {
        if (playing && player) {
            System.out.println(currPlayer.getName() + "'s turn.");
        }
        System.out.print("> ");
    }

    /** Set up the players for this Game. */
    public void setUpPlayers() {
        String response;
        int numPlayers = 0;
        while (numPlayers <= 0) {
            System.out.println("Please specify the number of players in this game.");
            response = readLine(false);
            try {
                numPlayers = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
        String[] names = new String[numPlayers];
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i += 1) {
            System.out.println("Please type a name for player " + (i + 1) + ".");
            names[i] = readLine(false);
        }
        for (int i = 0; i < names.length; i += 1) {
            players[i] = new Player(startingMoney, names[i]);
        }
        System.out.println("Players successfully set up. Ready to begin.");
    }

    /** Process the command. */
    private void processCommand(String line) {
        line = line.trim();
        if (line.length() == 0) {
            return;
        }
        Matcher command = COMMAND_PATN.matcher(line);
        if (command.matches()) {
            switch (command.group(1).toLowerCase()) {
            case "end":
                turn();
                nextPrompt = true;
                break;
            case "quit":
                 System.exit(0);
                 break;
            default:
                 System.out.println("Unknown command. Press ? For a list of commands.");
                 break;
            }
        }
    }

    /** Play this game. */
    public void play() {
        playing = true;
        nextPrompt = true;
        while (true) {
            String next;
            if (playing) {
                next = readLine(nextPrompt);
                nextPrompt = false;
                processCommand(next);
            }
        }
    }

    // TODO: Increment the player's turn variable.
    /** Switches to the next player's turn. */
    private void turn() {
        playerIndex = (playerIndex + 1) % players.length;
        currPlayer = players[playerIndex];
    }

    /** Creates the standard Monopoly properties. */
    public void createStandardProperties() {
        properties = new HashMap<>();
        List<Property> tempProperties = new ArrayList<>();
        // Create red properties
        //Collections.addAll(tempProperties, ColorProperty.createProperties("red", new String[] {"indiana",
        //        "kentucky", "illinois"}, ))
    }
}
