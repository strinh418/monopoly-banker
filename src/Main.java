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
}
