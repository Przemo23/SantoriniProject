package mygame;

import com.jme3.collision.CollisionResult;
import com.jme3.scene.Node;

public class Player  {
    private Node buildersNode;
    Builder male, female;
    BasicRules rules;
    Game game;

    public Player(Game game, String color) {
        this.game = game;
        this.buildersNode = new Node("BuildersNode");
        this.male = new Builder(game.getAssetManager(), color);
        this.female = new Builder(game.getAssetManager(), color);
        this.rules = new BasicRules();
        game.getRootNode().attachChild(buildersNode);
    }
    public void attachBuilder(Builder builder, int column, int row) {
        builder.getBuilderModel().setLocalTranslation(-52.0f + column*20.0f, -0.0f,-52.0f+row*20.f);
        buildersNode.attachChild(builder.getBuilderNode());
        builder.setCoordinates(column, row);
        builder.setEnabled(true);
        game.board.getTile(column, row).setOccupied(true);
    }
    public Builder collidingBuilder(CollisionResult collisionResult) {
        Node n = collisionResult.getGeometry().getParent();
        while(n.getParent() != null)
        {
            if(n.equals(male.getBuilderNode()))
                return male;
            else if(n.equals(female.getBuilderNode()))
                return female;
        }
        return null;
    }
    public boolean isBuilderSet(Builder builder) {
        return builder.isSet();
    }
    public Node getBuildersNode() {
        return buildersNode;
    }
}
