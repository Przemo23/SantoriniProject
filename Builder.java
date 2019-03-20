package mygame;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class Builder {
    private Geometry builderModel;
    private Floor floorLVL;
    private Material material;
    private Node builderNode;
    private int tileColumn, tileRow;
    private float offsets[];

    private boolean set;
    private boolean moved;
    private boolean building;
    Builder(AssetManager assetManager, String color)
    {
        builderNode = new Node("BuilderNode");
        Box box = new Box(1f, 3f, 1f);
        floorLVL = Floor.ZERO;
        offsets = new float[]{3.0f, 13.0f, 20.2f, 26.0f};
        moved = building = false;
        builderModel = new Geometry("Builder", box);
        builderNode.attachChild(builderModel);
        material = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        setTeamColor(color);
        builderModel.setMaterial(material);
        builderModel.scale(3.0f);
        set = false;
    }

    Builder setCoordinates(int column, int row)
    {
        tileRow = row;
        tileColumn =  column;
        return this;
    }
    Geometry getBuilderModel(){
        return builderModel;
    }
    private void setTeamColor(String color)
    {
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
    public boolean isSet() { return set; }
    public Node getBuilderNode() { return builderNode;  }
}
