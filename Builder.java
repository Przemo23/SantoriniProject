package mygame;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import java.util.ArrayList;

public class Builder {
    static final float[] offsets = new float[]{3.0f, 13.0f, 20.2f, 26.0f};
    private Geometry builderModel;
    private Floor floorLVL;
    private Material material;
    private Node builderNode;
    private int tileColumn, tileRow;
    private ArrayList<Vector2f> adjacentTiles;
    private boolean set;
    private boolean moved;
    private boolean built;
    Builder(AssetManager assetManager, String color)
    {
        adjacentTiles = new ArrayList<>();
        builderNode = new Node("BuilderNode");
        Box box = new Box(1f, 3f, 1f);
        floorLVL = Floor.ZERO;
        moved = built = false;
        builderModel = new Geometry("Builder", box);
        builderNode.attachChild(builderModel);
        material = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        setTeamColor(color);
        builderModel.setMaterial(material);
        builderModel.scale(3.0f);
        set = false;
        moved = false;
    }

    Builder setCoordinates(int column, int row) {
        tileRow = row;
        tileColumn =  column;
        return this;
    }
    Geometry getBuilderModel(){
        return builderModel;
    }
    private void setTeamColor(String color)  {
        switch (color) {
            case "Blue":
                material.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
                break;
            case "Red":
                material.setColor("Color", ColorRGBA.Red);   // set color of material to blue
                break;
            case "Green":
                material.setColor("Color", ColorRGBA.Green);   // set color of material to blue
                break;
        }

    }
    public void setEnabled(boolean b) { set = b; }
    public void addAdjacentTile(int column, int row){
        adjacentTiles.add(new Vector2f(column, row));
    }
    public void removeAdjacentTiles() {
        adjacentTiles.clear();
    }
    public boolean isSet() { return set; }
    public Node getBuilderNode() { return builderNode;  }
    public int getColumn() { return tileColumn; }
    public int getRow() { return tileRow; }
    public ArrayList<Vector2f> getAdjacentTiles(){
        return adjacentTiles;
    }
    public void setMoved(boolean b){
        moved = b;
    }
    public boolean hasMoved() { return moved;
    }
    public Floor getFloorLvl() { return floorLVL;
    }
    public void setFloorLvl(Floor update){
        floorLVL = update;
    }

    public void setBuilt(boolean b) {
        built = b;
    }
    public boolean hasBuilt() {
        return built;
    }
}
