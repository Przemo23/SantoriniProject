package com.jme.mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.Random;




class InitializationState extends AbstractAppState{
    private SimpleApplication app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private Camera cam;
    private Board board;


    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (Game) app; // can cast Application to something more specific
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.cam = this.app.getCamera();
        ((Game) app).board = new Board((Game) app);
        this.board = ((Game) app).board;

        ((Game) app).player = new Player[((Game) app).getPlayerNumber()];

        createPlayers();

        new Scene(assetManager, rootNode, viewPort);
        new CameraControl(cam, board.boardCentre(), inputManager);
        stateManager.attach(((Game) app).bSS);
    }
    private void createPlayers()
    {
        String playerColor;
        for(int i=0; i<((Game)app).player.length;i++)
        {
            if(i == 0)
                playerColor = "Blue";
            else if(i==1)
                playerColor = "Red";
            else if(i==2)
                playerColor = "Green";
            else if(i==3)
                playerColor = "Black";
            else
                playerColor = "White";

            Gods randomGod = Gods.getRandomGod();
            switch (randomGod)
            {
                case Pan:
                    System.out.println("Player " + (i+1)+ " is Pan.");
                    ((Game) app).player[i] = new Player(((Game) app), playerColor)
                    {

                        int previousHeight = 0;
                        @Override
                        public void move(Board board, Ray ray, CollisionResults results, Builder selected) {
                             previousHeight = selected.getFloorLvl().height;
                            super.move(board, ray, results, selected);

                        }
                        @Override
                        public boolean isWinAccomplished(Builder builder) {
                            return ((previousHeight - builder.getFloorLvl().height >= 2) || builder.getFloorLvl() == Floor.SECOND);
                        }

                    };
                    break;

                case Chronos:
                        System.out.println("Player " +( i+1)+ " is Chronos.");
                        ((Game) app).player[i] = new Player(((Game) app), playerColor) {


                            @Override
                            public boolean isWinAccomplished(Builder builder)
                            {
                                if(builder.getFloorLvl().equals(Floor.SECOND) || calculateNumberOfTowers()>=5)
                                    return true;

                                else return false;
                            }

                            int calculateNumberOfTowers()
                            {
                                int towersCount = 0;
                                for(int i =0;i<5;i++)
                                {
                                    for(int j =0;j<5;j++)
                                    {
                                        if(board.getTile(i,j).getHeight().height==4)
                                            towersCount++;
                                    }
                                }
                                return  towersCount;

                            }

                        };
                        break;
                case Artemis:
                    System.out.println("Player " + (i+1)+ " is Artemis.");
                    ((Game) app).player[i] = new Player(((Game) app), playerColor)
                    {
                        boolean afterFirstMove = false;
                        @Override
                        public void move(Board board, Ray ray, CollisionResults results, Builder selected) {

                            super.move(board, ray, results, selected);
                            if(afterFirstMove == false) {
                                afterFirstMove =true;
                                selected.setMoved(false);
                            }
                            else
                                afterFirstMove = false;

                        }


                    };
                    break;
              /*  case Hephaestus:
                    System.out.println("Player " + (i+1)+ " is Hephaestus.");
                    ((Game) app).player[i] = new Player(((Game) app), playerColor)
                    {

                        @Override
                        public Vector2f build(Board board, Ray ray, CollisionResults results, Builder selected) {

                            Vector2f previousTarget = super.build(board, ray, results, selected);
                            board.buildTile((int)previousTarget.x,(int)previousTarget.y);
                            return null;
                        }


                    };
                    break;*/
                        default:
                     ((Game) app).player[i] = new Player(((Game) app), playerColor);
                     break;

            }
        }


    }
}