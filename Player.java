package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.scene.Node;

public class Player  {
    Builder male, female;
    BasicRules rules;

    public Player(AssetManager assetManager)
    {
        male = new Builder(assetManager, "First");
        female = new Builder(assetManager, "Second");
        rules = new BasicRules();
    }

    public Player attachBuilder(Builder builder, Node node, int column, int row)
    {
        builder.getBuilderModel().setLocalTranslation(-52.0f + column*20.0f, 3.0f,-52.0f+row*20.f);
        node.attachChild(builder.getBuilderModel());
        builder.setCoordinates(column, row);
        builder.setEnabled(true);
        return this;
    }

    public boolean isBuilderSet(Builder builder) {
        return builder.isSet();
    }
}
