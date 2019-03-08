package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

public class Builder {
    private Spatial builderModel;
    private int tileColumn, tileRow;
    private Floor floorLVL;
    private float offsets[];
    private boolean moved;
    private boolean building;
    public Builder(AssetManager assetManager, String modelName, int column, int row)
    {
        floorLVL = Floor.ZERO;
        offsets = new float[]{3.0f, 13.0f, 20.2f, 26.0f};
        moved = building = false;
        tileColumn = column;
        tileRow = row;
        builderModel = assetManager.loadModel("Models/Floors/" + modelName + ".j3o");
        builderModel.setLocalTranslation(-52.0f + column*20.0f, offsets[0],-52.0f+row*20.f);
        builderModel.scale(3.0f);
    }

    Spatial getBuilderModel(){
        return builderModel;
    }

}
