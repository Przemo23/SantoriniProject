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

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    protected void setPlayerNumber(int n)
    {
        playerNumber = n;
    }
}