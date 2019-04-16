package appStates;
import com.jme3.app.SimpleApplication;
import model.Board;
import model.Player;


public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }

    MenuState mS = new MenuState();
    InitializationState iS = new InitializationState();
    BuilderSetState bSS = new BuilderSetState();
    InGameState iGS;
    Player[] player;
    public Board board;
    private int playerNumber;


    @Override
    public void simpleInitApp() {

        stateManager.attach(mS);

    }

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    public void setPlayerNumber(int n)
    {
        playerNumber = n;
    }
}