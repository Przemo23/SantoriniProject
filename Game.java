package mygame;
import com.jme3.app.SimpleApplication;


public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }

     InitializationState iS = new InitializationState();
     BuilderSetState bSS = new BuilderSetState();
    Player[] player;
    Board board;

    @Override
    public void simpleInitApp() {
        board = new Board(this);
        player = new Player[1];
        for(int i = 0; i<player.length; i++)
            player[i] = new Player(assetManager);
        stateManager.attach(iS);
    }
}