package mygame;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

public class Builder {
    private Spatial builderModel;
    private int tileColumn, tileRow;
    private Floor floorLVL;
    private float offsets[];
    private boolean set;
    private boolean moved;
    private boolean building;
    Builder(AssetManager assetManager, String modelName)
    {
        floorLVL = Floor.ZERO;
        offsets = new float[]{3.0f, 13.0f, 20.2f, 26.0f};
        moved = building = false;
        builderModel = assetManager.loadModel("Models/Floors/" + modelName + ".j3o");
        builderModel.scale(3.0f);
        set = false;
    }

    Builder setCoordinates(int column, int row)
    {
        tileRow = row;
        tileColumn =  column;
        return this;
    }
    Spatial getBuilderModel(){
        return builderModel;
    }
    public void setEnabled(boolean b) { set = b; }
    public boolean isSet() { return set; }
}
