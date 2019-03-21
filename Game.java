package mygame;
import com.jme3.app.SimpleApplication;


public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }

     private InitializationState iS = new InitializationState();
     BuilderSetState bSS = new BuilderSetState();
     InGameState iGS;
     Player[] player;
     Board board;

    @Override
    public void simpleInitApp() {
        board = new Board(this);
        player = new Player[2];
            player[0] = new Player(this, "Blue");
            player[1] = new Player(this, "Red");
           // player[2] = new Player(assetManager, "Green");
        board.buildTile(1,1);
        board.buildTile(1,1);
        board.buildTile(1,1);
        board.buildTile(1,3);
        board.buildTile(0,3);
        board.buildTile(0,3);
        board.buildTile(0,3);
        board.buildTile(0,3);
        board.buildTile(2,2);
        board.buildTile(2,2);
        stateManager.attach(iS);
    }
}