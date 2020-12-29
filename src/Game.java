import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    // TODO: Determine if the right access modifiers are used throughout this class
    // TODO: Allow the use of M or K when specifying dollar amounts and switch to K when under 1 M.

    // TODO: Add help file with commands
    /** Name of help text resource. */
    static final String HELP_FILE = "src/HelpText.txt";

    /** Describes a command with up to three arguments. */
    private static final Pattern COMMAND_PATN =
            Pattern.compile("(#|\\S+)\\s*(\\S*)\\s*(\\S*)\\s*(\\S*).*");

    /** Successful buy string format. */
    private static final String BOUGHT_PROPERTY = "Successfully bought %s. %s now has $%s M.";

    /** Pay another player string format. */
    private static final String PAY_PLAYER = "%s payed %s $%s M. Players now have $%s M and $%s M respectively.";

    /** Loss in money string format. */
    private static final String LOST_MONEY = "%s paid $%s M. Player now has $%s M.";

    /** Gain in money string format. */
    private static final String GAIN_MONEY = "%s gained $%s M. Player now has $%s M.";

    /** String format for player prompt. */
    private static final String TURN_PROMPT = "%s: Turn %s";

    /** Buy building string format. */
    private static final String BUY_BUILDING = "%s bought a building on %s for $%s M. Player now has $%s M.";

    /** Sell building string format. */
    private static final String SELL_BUILDING = "%s sold a building on %s for $%s M. Player now has $%s M.";

    /** Give property string format. */
    private static final String GIVE_PROPERTY = "%s gave %s %s for $%s M. Players now have $%s M and $%s M respectively.";

    /** Player's money string format. */
    private static final String PLAYER_MONEY = "%s has $%s M.";

    /** Determines whether the current game is in session. */
    private boolean playing;

    /** Input source. */
    private Scanner input;

    /** Players in this game. */
    private Player[] players;

    // TODO: Deal with case where two players may have the same name.
    /** Map of player names to players. */
    private Map<String, Player> playerNames;

    /** Index of the current player. */
    private int playerIndex;

    /** Player who has their current turn. */
    private Player currPlayer;

    /** Properties in this game. Maps name of the property to the Property object. */
    private Map<String, Property> properties;

    /** The starting money in this game. */
    private double startingMoney;

    /** Amount gained at pass go. */
    private double passGo;

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
        passGo = 2;
        input = new Scanner(System.in);
        welcome();
        setUpPlayers();
        createStandardProperties();
        playerIndex = 0;
        currPlayer = players[0];
        currPlayer.incrementTurn();
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
            System.out.println(String.format(TURN_PROMPT, currPlayer.getName(), currPlayer.getTurn()));
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
        playerNames = new HashMap<>();
        for (int i = 0; i < numPlayers; i += 1) {
            System.out.println("Please type a name for player " + (i + 1) + ".");
            names[i] = readLine(false);
        }
        for (int i = 0; i < names.length; i += 1) {
            players[i] = new Player(startingMoney, names[i]);
            playerNames.put(names[i].toLowerCase(), players[i]);
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
                case "?":
                    Main.printResource(HELP_FILE);
                case "end":
                    turn();
                    nextPrompt = true;
                    break;
                case "quit":
                     System.exit(0);
                     break;
                case "buy":
                    try {
                        Property bought = properties.get(command.group(2).toLowerCase());
                        currPlayer.buyProperty(bought);
                        System.out.println(String.format(BOUGHT_PROPERTY, bought.getName(), currPlayer.getName(),
                                currPlayer.getMoney()));
                    } catch (MonopolyException e) {
                        System.out.println("Unable to buy this property.");
                    } catch (NullPointerException e) {
                        System.out.println("Property does not exist.");
                    }
                    break;
                case "rent":
                    // TODO: Better way to deal with utility dice rolls
                    try {
                        Property rented = properties.get(command.group(2).toLowerCase());
                        if (rented.getClass() == Utility.class) {
                            System.out.println("What dice did you roll?");
                            int dice = Integer.parseInt(readLine(false));
                            currPlayer.payRent((Utility)rented, dice);
                            Player owner = rented.getOwner();
                            System.out.println(String.format(PAY_PLAYER, currPlayer.getName(), owner.getName(),
                                    ((Utility)rented).getRent(dice), currPlayer.getMoney(), owner.getMoney()));
                        } else {
                            currPlayer.payRent(rented);
                            Player owner = rented.getOwner();
                            System.out.println(String.format(PAY_PLAYER, currPlayer.getName(), owner.getName(),
                                    rented.getRent(), currPlayer.getMoney(), owner.getMoney()));
                        }

                    }  catch (MonopolyException e) {
                        System.out.println(e.getMessage());
                        // TODO: Need to deal with exceptions related to insufficient funds here.
                    } catch (NullPointerException e) {
                        System.out.println("Property does not exist.");
                    } catch (NumberFormatException e) {
                        System.out.println("Improper dice roll given.");
                    }
                    break;
                case "pay":
                    try {
                        double amount = Double.parseDouble(command.group(2));
                        String name = command.group(3);
                        if (name.equals("")) {
                            currPlayer.updateMoney(-1 * amount);
                            System.out.println(String.format(LOST_MONEY, currPlayer.getName(), amount, currPlayer.getMoney()));
                        } else {
                            Player other = playerNames.get(name.toLowerCase());
                            currPlayer.payPlayer(other, amount);
                            System.out.println(String.format(PAY_PLAYER, currPlayer.getName(), other.getName(), amount,
                                    currPlayer.getMoney(), other.getMoney()));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount provided for command.");
                    } catch (MonopolyException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println("Unable to find other player");
                    }
                    break;
                case "gain":
                    try {
                        double amount = Double.parseDouble(command.group(2));
                        String name = command.group(3);
                        if (name.equals("")) {
                            currPlayer.updateMoney(amount);
                            System.out.println(String.format(GAIN_MONEY, currPlayer.getName(), amount, currPlayer.getMoney()));
                        } else {
                            Player other = playerNames.get(name.toLowerCase());
                            other.payPlayer(currPlayer, amount);
                            System.out.println(String.format(PAY_PLAYER, other.getName(), currPlayer.getName(), amount,
                                    other.getMoney(), currPlayer.getMoney()));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount provided for command.");
                    } catch (MonopolyException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println("Unable to find other player");
                    }
                    break;
                case "buybuilding":
                    try {
                        Property buying = properties.get(command.group(2).toLowerCase());
                        currPlayer.buyBuilding(buying);
                        System.out.println(String.format(BUY_BUILDING, currPlayer.getName(), buying.getName(),
                                buying.getBuildingCost(), currPlayer.getMoney()));
                    } catch (NullPointerException e) {
                        System.out.println("Property does not exist.");
                    } catch (MonopolyException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "sellbuilding":
                    try {
                        Property selling = properties.get(command.group(2).toLowerCase());
                        currPlayer.sellBuilding(selling);
                        System.out.println(String.format(SELL_BUILDING, currPlayer.getName(), selling.getName(),
                                selling.getBuildingCost() * .5, currPlayer.getMoney()));
                    } catch (NullPointerException e) {
                        System.out.println("Property does not exist.");
                    } catch (MonopolyException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "passgo":
                    try {
                        currPlayer.updateMoney(passGo);
                        System.out.println(String.format(PLAYER_MONEY, currPlayer.getName(), currPlayer.getMoney()));
                    } catch (MonopolyException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "give":
                    try {
                        Property giving = properties.get(command.group(2).toLowerCase());
                        Player other = playerNames.get(command.group(3).toLowerCase());
                        double amount = 0;
                        if (!command.group(4).equals("")) {
                            amount = Double.parseDouble(command.group(4).toLowerCase());;
                        }
                        if (amount == 0) {
                            currPlayer.giveProperty(giving, other);
                        } else {
                            currPlayer.giveProperty(giving, other, amount);
                        }
                        System.out.println(String.format(GIVE_PROPERTY, currPlayer.getName(), other.getName(),
                                giving.getName(), amount, currPlayer.getMoney(), other.getMoney()));

                    } catch (NullPointerException e) {
                        System.out.println("Unable to find property or player.");
                    } catch (NumberFormatException e) {
                        System.out.println("Improper format provided for money amount.");
                    } catch (MonopolyException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "endgame":
                    try {
                        for (Player p : players) {
                            System.out.println(String.format(PLAYER_MONEY, p.getName(), p.calculateWorth()));
                        }
                    } catch (MonopolyException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "info":
                    for (Player p : players) {
                        System.out.print(String.format(PLAYER_MONEY, p.getName(), p.getMoney()));
                        System.out.println(" " + p.getProperties());
                    }
                    nextPrompt = true;
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

    /** Switches to the next player's turn. */
    private void turn() {
        playerIndex = (playerIndex + 1) % players.length;
        currPlayer = players[playerIndex];
        currPlayer.incrementTurn();
    }

    /** Creates the standard Monopoly properties. */
    public void createStandardProperties() {
        properties = new HashMap<>();
        List<Property> tempProperties = new ArrayList<>();
        // Create red properties
        Collections.addAll(tempProperties, ColorProperty.createProperties("red", new String[] {"indiana",
                "kentucky", "illinois"}, new double[] {2.2, 2.2, 2.4},1.5, new double[][]
                {{.18, .9, 2.5, 7, 8.75, 10.5}, {.18, .9, 2.5, 7, 8.75, 10.5}, {.2, 1, 3, 7.5, 9.25, 11}}));

        // Create green properties
        Collections.addAll(tempProperties, ColorProperty.createProperties("green", new String[] {"northcarolina",
        "pacific", "pennsylvania"}, new double[] {3, 3, 3.2},2, new double[][]{{.26, 1.3, 3.9, 9, 11, 12.75},
                {.26, 1.3, 3.9, 9, 11, 12.75}, {.28, 1.5, 4.5, 10, 12, 14}}));

        // Create yellow properties
        Collections.addAll(tempProperties, ColorProperty.createProperties("yellow", new String[] {"atlantic",
                "ventnor", "marvingardens"}, new double[] {2.6, 2.6, 2.8},1.5, new double[][]
                {{.22, 1.1, 3.3, 8, 9.75, 11.5}, {.22, 1.1, 3.3, 8, 9.75, 11.5}, {.24, 1.2, 3.6, 8.5, 10.25, 12}}));

        // Create orange properties
        Collections.addAll(tempProperties, ColorProperty.createProperties("orange", new String[] {"stjamesplace",
                "tennessee", "newyork"}, new double[] {1.8, 1.8, 2},1, new double[][]{{.14, .7, 2, 5.5, 7.5, 9.5},
                {.14, .7, 2, 5.5, 7.5, 9.5}, {.16, .8, 2.2, 6, 8, 10}}));

        // Create purple properties
        Collections.addAll(tempProperties, ColorProperty.createProperties("purple", new String[] {"states",
                "stcharlesplace", "virginia"}, new double[] {1.4, 1.4, 1.6}, 1, new double[][]
                {{.1, .5, 1.5, 4.5, 6.25, 7.5}, {.1, .5, 1.5, 4.5, 6.25, 7.5}, {.12, .6, 1.8, 5, 7, 9}}));

        // Create light blue properties
        Collections.addAll(tempProperties, ColorProperty.createProperties("light blue", new String[] {"vermont",
                "oriental", "connecticut"}, new double[] {1, 1, 1.2}, .5, new double[][]
                {{.06, .3, .9, 2.7, 4, 5.5}, {.06, .3, .9, 2.7, 4, 5.5}, {.08, .4, 1, 3, 4.5, 6}}));

        // Create dark blue properties
        Collections.addAll(tempProperties, ColorProperty.createProperties("dark blue", new String[] {"parkplace",
                "boardwalk"}, new double[] {3.5, 4}, 2, new double[][] {{.35, 1.75, 5, 11, 13, 15},
                {.5, 2, 6, 14, 17, 18}}));

        // Create brown properties
        Collections.addAll(tempProperties, ColorProperty.createProperties("brown", new String[] {"mediterranean", "baltic"},
                new double[] {.6, .6}, .5, new double[][] {{.02, .1, .3, .9, 1.6, 2.5}, {.04, .2, .6, 1.8, 3.2, 4.5}}));

        // Create railroads
        Collections.addAll(tempProperties, Railroad.createProperties(new String[] {"pennsylvaniarailroad", "shortline",
                "reading", "b&o"}, 2, 1, new double[] {0, .25, .5, 1, 2}));

        // Create utilities
        Collections.addAll(tempProperties, Utility.createProperties(new String[] {"electric", "water"}, 1.5,
                .45, new double[] {0, 4, 10}));

        for (Property p: tempProperties) {
            properties.put(p.getName(), p);
        }
    }
}
