package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import static mygame.InGameState.BUILDING_PHASE;
import static mygame.InGameState.MOVEMENT_PHASE;
import static mygame.InGameState.roundPhase;

public final class Board {
    private AssetManager assetManager;
    private Node tilesNode;             // every single board tile is attached to it
    private Node boardNode;             // includes tilesNode and board frame
    private BoardTile tiles[][];

    Board(Game game) {
        // 1. preliminary initialization
        this.tilesNode = new Node("Tiles");
        this.boardNode = new Node("Board");
        this.tiles = new BoardTile[5][5];
        this.assetManager = game.getAssetManager();
        // 2. loading board frame model
        Spatial boardFrame = assetManager.loadModel("Models/Board/Board.j3o");
        boardFrame.setLocalTranslation(-21.5f, 0.1f, -1.5f);
        boardFrame.setLocalScale(20.0f);
        boardNode.attachChild(boardFrame);
        // 3. loading board tiles
        for(byte column = 0; column<5; column++)
            for(byte row = 0; row<5; row++)
            {
                tiles[column][row] = new BoardTile(column, row);
                tilesNode.attachChild(tiles[column][row].tileNode);
            }
        boardNode.attachChild(tilesNode);
        // 4. final attachment of the clear board to the world
        highlightBoard();
        attachBoard(game.getRootNode());
    }

    private void highlightBoard() {
        for(int i = 0; i<5; i++)
            for(int j = 0; j<5; j++)
                tiles[i][j].makeColored();
    }

    private void attachBoard(Node node){
        node.attachChild(boardNode);
    }

    /** Returns a board tile that a mouse cursor collides with OR returns null if the click missed the board */
    public BoardTile collidingTile(int column, int row, CollisionResult collisionResult) {
        Node n = collisionResult.getGeometry().getParent();
        while(n.getParent() != null)
        {
            if(n.equals(tiles[column][row].tileNode))
                return tiles[column][row];
            n = n.getParent();
        }
        return null;
    }


    /** Builds up a tile that was selected by a player during the building phase */
    public void buildTile(int column, int row) { tiles[column][row].buildUp(); }

    /** Shows tiles available to enter by the builder during the movement phase */
    public void showAvailableTiles(Builder selected, boolean bool){
        int tempColumn ;
        int tempRow ;
        Texture tileHighlighted = assetManager.loadTexture("Textures/Terrain/Selected.jpg");
        Texture tileSwitchedOff = assetManager.loadTexture("Textures/Terrain/Grass.jpg");
        if(!bool)
            selected.removeAdjacentTiles();
       for(tempColumn = selected.getColumn() - 1; tempColumn <= selected.getColumn() + 1; tempColumn++)
       {
           if(tempColumn<0 || tempColumn > 4)
               continue;
           for(tempRow = selected.getRow() - 1; tempRow <= selected.getRow() + 1; tempRow++)
           {
               if(tempRow<0 || tempRow > 4)
                   continue;
               if(bool && roundPhase == MOVEMENT_PHASE && !tiles[tempColumn][tempRow].isCompleted() && tiles[tempColumn][tempRow].isMovable()
                       && tiles[tempColumn][tempRow].getHeight().height - selected.getFloorLvl().height < 2 )
               {
                   tiles[tempColumn][tempRow].tileMat.setTexture("ColorMap", tileHighlighted);
                   selected.addAdjacentTile(tempColumn, tempRow);
               }
               else if(bool &&  !tiles[tempColumn][tempRow].isCompleted() && tiles[tempColumn][tempRow].isBuildable() && roundPhase == BUILDING_PHASE)
               {
                   tiles[tempColumn][tempRow].tileMat.setTexture("ColorMap", tileHighlighted);
                   selected.addAdjacentTile(tempColumn, tempRow);
               }
               else if(!bool)
                   tiles[tempColumn][tempRow].tileMat.setTexture("ColorMap", tileSwitchedOff);
           }
       }
    }

    /** Actually it returns a tilesNode */
    public Node getBoardNode() {
        return tilesNode; }

    /** Returns a tile which location is defined by "column" and "row" indexes in a 5x5 matrix */
    public BoardTile getTile(int column, int row) {
        return tiles[column][row]; }

    /** Returns a tile in the exact middle of the board */
    public Spatial boardCentre() {return tiles[2][2].tile;}


 /* This is an inner class describing each board tile from 25 tiles */
     class BoardTile {
        private AmbientLight floorsLight;
        private AmbientLight domeLight;
        private Node tileNode;
        private Node domeNode;
        private Node floorsNode;
        private Spatial tile;
        private Spatial ground, first, second, dome;
        private Material tileMat;
        private Texture tileTexture;
        private Floor height;
        private boolean completed;
        private boolean movable;
        private boolean buildable;
        private int rowCoord;
        private int columnCoord;

        public BoardTile(int column, int row) {
            floorsLight = new AmbientLight();
            floorsLight.setColor(ColorRGBA.White.mult(0.6f));
            domeLight = new AmbientLight();
            domeLight.setColor(ColorRGBA.Blue.mult(0.7f));
            height = Floor.ZERO;
            completed = false;
            buildable = true;
            movable = true;
            columnCoord = column;
            rowCoord = row;
            tileNode = new Node("TileNode");
            domeNode = new Node("DomeNode");
            floorsNode = new Node("FloorsNode");
            float TILE_LENGTH = 10.0f;
            float TILE_WIDTH = 10.0f;
            float TILE_HEIGHT = 0.1f;
            Box tileShape = new Box(TILE_LENGTH, TILE_HEIGHT, TILE_WIDTH);
            tile = new Geometry("Tile", tileShape);
            tileMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            tileTexture = assetManager.loadTexture("Textures/Terrain/Grass.jpg");
            tileMat.setTexture("ColorMap", tileTexture);
            tile.setMaterial(tileMat);
            tile.setLocalTranslation(-51.5f + 20*column, 0.0f, -51.5f + 20*row);
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
            tileNode.attachChild(floorsNode);
            tileNode.attachChild(domeNode);


        }
        public boolean isCompleted(){
            return completed;
        }

        public boolean isBuildable() {
             return buildable;
         }

        public boolean isMovable() {
             return movable;
         }

        public void setCompleted(boolean b) { completed = b; }
        public void setBuildable(boolean b) {
                buildable = b;
         }
        public void setMovable(boolean b){
                movable = b;
         }
        public Floor getHeight(){
            return height;
        }
        public void makeColored() { floorsNode.addLight(floorsLight); domeNode.addLight(domeLight); }
        public void buildUp() {
            if(height == Floor.ZERO)
            {
                floorsNode.attachChild(ground);
                height = Floor.GROUND;
            }
            else if(height == Floor.GROUND)
            {
                height = Floor.FIRST;
                floorsNode.attachChild(first);
            }
            else if(height == Floor.FIRST)
            {
                height = Floor.SECOND;
                floorsNode.attachChild(second);
            }
            else if(height == Floor.SECOND)
            {
                height = Floor.DOME;
                domeNode.attachChild(dome);
                completed = true;
                movable = false;
                buildable = false;
            }
        }
        public Vector2f getCoordinates() {
            return new Vector2f(columnCoord, rowCoord);
     }
 }
}
