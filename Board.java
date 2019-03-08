package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public final class Board {
    private float TILE_LENGTH = 10.0f;
    private float TILE_WIDTH = 10.0f;
    private float TILE_HEIGHT = 0.1f;
    private AssetManager assetManager;
    private Spatial boardFrame;
    private Node boardNode;
    BoardTile tiles[][];

    public Board(Game game)
    {
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
                boardNode.attachChild(tiles[column][row].tileNode);
            }
            attachBoard(game.getRootNode());
    }

    private void attachBoard(Node node)
    {
        node.attachChild(boardNode);
    }


 /* This is an inner class describing each board tile from 25 tiles */
    private class BoardTile {

        Node tileNode;
        private Spatial tile;
        private Material tileMat;
        private Spatial ground, first, second, dome;
        private Texture tileTexture;
        private Floor height;
        private boolean occupied;
        private int rowCoord;
        private int columnCoord;

        public BoardTile(int column, int row)
        {
            height = Floor.ZERO;
            occupied = false;
            columnCoord = column;
            rowCoord = row;
            tileNode = new Node("TileNode");
            Box tileShape = new Box(TILE_LENGTH, TILE_HEIGHT, TILE_WIDTH);
            tile = new Geometry("Tile", tileShape);
            tileMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            tileTexture = assetManager.loadTexture("Textures/Terrain/Grass.jpg");
            tileMat.setTexture("ColorMap", tileTexture);
            tile.setMaterial(tileMat);
            tile.setLocalTranslation(-53.0f + 20*column, 0.0f, -53.0f + 20*row);
            tileNode.attachChild(tile);
            ground = assetManager.loadModel("Models/Floors/Ground.j3o");
            first = assetManager.loadModel("Models/Floors/First.j3o");
            second = assetManager.loadModel("Models/Floors/Second.j3o");
            dome = assetManager.loadModel("Models/Floors/Dome.j3o");
            ground.setLocalTranslation(-52.0f+column*20.0f,40.0f,-52.0f+row*20.0f);
            ground.setLocalScale(3.0f);
            first.setLocalTranslation(-52.0f+column*20.0f,46.0f,-52.0f+row*20.0f);
            first.setLocalScale(3.0f);
            second.setLocalTranslation(-52.0f+column*20.0f,57.2f,-52.0f+row*20.0f);
            second.setLocalScale(3.0f);
            dome.setLocalTranslation(-52.0f+column*20.0f,61.0f,-52.0f+row*20.0f);
            dome.setLocalScale(6.0f);
        }

        public boolean isOccupied(){
            return occupied;
        }
        public Floor getHeight(){
            return height;
        }
    }
}
