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
        male = new Builder(assetManager, "First", 2, 0);
        female = new Builder(assetManager, "Second", 3, 3);
        rules = new BasicRules();
    }

    public Player attachBuilder(Builder builder, Node node)
    {
        node.attachChild(builder.getBuilderModel());
        return this;
    }

}
