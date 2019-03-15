package com.jme.mygame;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.light.AmbientLight;

public final class Board {
    private float TILE_LENGTH = 10.0f;
    private float TILE_WIDTH = 10.0f;
    private float TILE_HEIGHT = 0.1f;
    private AssetManager assetManager;
    private Spatial boardFrame;
    private Node tilesNode;
    private Node boardNode;
    private BoardTile tiles[][];

    Board(Game game)
    {
        tilesNode = new Node("Tiles");
        boardNode = new Node("Board");
        tiles = new BoardTile[5][5];
        assetManager = game.getAssetManager();
        boardFrame = assetManager.loadModel("Models/Board/Board.j3o");
        boardFrame.setLocalTranslation(-23.0f, 0.1f, -3.0f);
        boardFrame.setLocalScale(20.0f);
        boardNode.attachChild(boardFrame);

        for(byte column = 0; column<5; column++)
            for(byte row = 0; row<5; row++)
            {
                tiles[column][row] = new BoardTile(column, row);
                tilesNode.attachChild(tiles[column][row].tileNode);
            }
            boardNode.attachChild(tilesNode);
            attachBoard(game.getRootNode());
    }


    private void attachBoard(Node node)
    {
        node.attachChild(boardNode);
    }
    public Node getBoardNode() { return tilesNode; }
    public void buildTile(int column, int row) { tiles[column][row].buildUp(); }
    public boolean tileCollides(int column, int row, CollisionResult collisionResult)
    {
        Node n = collisionResult.getGeometry().getParent();
        while(n.getParent() != null)
        {
            if(n.equals(tiles[column][row].tileNode))
                return true;
            n = n.getParent();
        }
        return false;
    }
    public BoardTile getTile(int column, int row) { return tiles[column][row]; }
    public Spatial boardCentre() {return tiles[2][2].tile;}
    public void switchOffLights()
    {
        for(int i=0; i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                tiles[i][j].switchOff();
            }
        }

    }
    public void highLightBoard(int coordX,int coordY)
    {
        if((coordX>4 || coordX < 0 || coordY >4 || coordY <0)==false) {
            for (int i = coordX - 1; i <= coordX + 1; i++) {
                for (int j = coordY - 1; j <= coordY + 1; j++) {
                    if (tiles[i][j].isOccupied() == false || tiles[i][j].getHeight() != Floor.DOME)
                        tiles[i][j].highlight();
                }
            }
        }

    }


 /* This is an inner class describing each board tile from 25 tiles */
     class BoardTile {

        Node tileNode;
         Spatial tile;
        private Material tileMat;
        private Spatial ground, first, second, dome;
        private Texture tileTexture;
        private Floor height;
        private boolean occupied;
        private int rowCoord;
        private int columnCoord;
        AmbientLight light;

        public BoardTile(int column, int row)
        {
            light = new AmbientLight();
            light.setColor(ColorRGBA.Red.mult(1.3f));
            height = Floor.ZERO;
            occupied = false;
            columnCoord = column;
            rowCoord = row;
            tileNode = new Node("TileNode");
            Box tileShape = new Box(TILE_LENGTH, TILE_HEIGHT, TILE_WIDTH);
            tile = new Geometry("Tile", tileShape);
            tileMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            //tileMat.setColor("Color",ColorRGBA.White);
            tileTexture = assetManager.loadTexture("Textures/Terrain/Grass.jpg");
            tileMat.setTexture("ColorMap", tileTexture);
            tile.setMaterial(tileMat);
            tile.setLocalTranslation(-53.0f + 20*column, 0.0f, -53.0f + 20*row);
            tileNode.attachChild(tile);
            ground = assetManager.loadModel("Models/Floors/Ground.j3o");
            first = assetManager.loadModel("Models/Floors/First.j3o");
            second = assetManager.loadModel("Models/Floors/Second.j3o");
            dome = assetManager.loadModel("Models/Floors/Dome.j3o");
            ground.setLocalTranslation(-52.0f+column*20.0f,0.0f,-52.0f+row*20.0f);
            ground.setLocalScale(3.0f);
            first.setLocalTranslation(-52.0f+column*20.0f,6.0f,-52.0f+row*20.0f);
            first.setLocalScale(3.0f);
            second.setLocalTranslation(-52.0f+column*20.0f,17.2f,-52.0f+row*20.0f);
            second.setLocalScale(3.0f);
            dome.setLocalTranslation(-52.0f+column*20.0f,21.0f,-52.0f+row*20.0f);
            dome.setLocalScale(6.0f);

        }

        public boolean isOccupied(){
            return occupied;
        }
        public Floor getHeight(){
            return height;
        }
        public void buildUp()
        {
            if(height == Floor.ZERO)
            {
                tileNode.attachChild(ground);
                height = Floor.GROUND;
            }
            else if(height == Floor.GROUND)
            {
                height = Floor.FIRST;
                tileNode.attachChild(first);
            }
            else if(height == Floor.FIRST)
            {
                height = Floor.SECOND;
                tileNode.attachChild(second);
            }
            else if(height == Floor.SECOND)
            {
                height = Floor.DOME;
                tileNode.attachChild(dome);
                occupied = true;
            }
        }
     public void highlight() { tileNode.addLight(light); }
     public void switchOff() {tileNode.removeLight(light);}
    }


}
