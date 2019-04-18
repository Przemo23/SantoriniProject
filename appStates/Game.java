package appStates;
import com.jme3.app.SimpleApplication;
import model.Board;
import model.Player;


public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }

    //States
    MenuState menuState = new MenuState();
    InitializationState initializationState = new InitializationState();
    BuilderSetState builderSetState = new BuilderSetState();
    InGameState inGameState;
    //Others
    Player[] player;
    public Board board;
    private int playerNumber;


    @Override
    public void simpleInitApp() {
        stateManager.attach(menuState);
    }

    int getPlayerNumber()
    {
        return playerNumber;
    }

    void setPlayerNumber(int n)
    {
        playerNumber = n;
    }
}