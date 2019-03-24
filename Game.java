package com.jme.mygame;
import com.jme3.app.SimpleApplication;


public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }

    protected MenuState mS = new MenuState();
    InitializationState iS = new InitializationState();
     BuilderSetState bSS = new BuilderSetState();
     InGameState iGS;
     Player[] player;
     Board board;
     private int playerNumber;


    @Override
    public void simpleInitApp() {


        stateManager.attach(mS);
    }

    public void initBoardPlayers()
    {
        board = new Board(this);
        player = new Player[playerNumber];
        player[0] = new Player(this, "Blue");
        player[1] = new Player(this, "Red");
        if(playerNumber>=3)
            player[2] = new Player(this, "Green");
        if(playerNumber==4)
            player[3] = new Player( this, "Magenta");
    }

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    protected void setPlayerNumber(int n)
    {
        playerNumber = n;
    }
}