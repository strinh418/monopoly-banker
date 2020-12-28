import java.util.Scanner;

public class Game {
    // TODO: Determine if the right access modifiers are used throughout this class
    /** Name of help text resource. */
    static final String HELP_FILE = "src/HelpText.txt";

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

    /** The starting money in this game. */
    private double startingMoney;

    /** Begins a default game with normal settings:
     *  Starting money: $15 Million
     *  Pass go: $2 Million
     *  Buildings: buildings must be evenly distributed, bought 1 at a time
     *  Jail Penalty: $500 Thousand
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


    /** Returns a command from the standard input after prompting if PROMPT. */
    public String readLine(boolean prompt) {
        if (prompt) {
            prompt();
        }
        if (input.hasNextLine()) {
            return input.nextLine().trim();
        }
        return null;
    }

    /** Print a prompt. */
    private void prompt() {
        if (playing) {
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
            response = readLine(true);
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
            names[i] = readLine(true);
        }
        for (int i = 0; i < names.length; i += 1) {
            players[i] = new Player(startingMoney, names[i]);
        }
        System.out.println("Players successfully set up. Ready to begin.");
    }

    /** Play this game. */
    public void play() {
        playing = true;
        while (true) {
            if (playing) {
                readLine(true);
            }
        }
    }
}
