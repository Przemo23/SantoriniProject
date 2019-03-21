package mygame;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
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
        builder.getBuilderModel().setLocalTranslation(-52.0f + column*20.0f, Builder.offsets[builder.getFloorLvl().height],-52.0f+row*20.f);
        buildersNode.attachChild(builder.getBuilderNode());
        builder.setCoordinates(column, row);
        builder.setEnabled(true);
        game.board.getTile(column, row).setBuildable(false);
        game.board.getTile(column, row).setMovable(false);
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

    public void  moveBuilder(Board board, Ray ray, CollisionResults collisionResults, Builder builder){

        if(builder.getBuilderNode().equals(male.getBuilderNode()))
            rules.move(board, ray, collisionResults, male);

        else if(builder.getBuilderNode().equals(female.getBuilderNode()))
            rules.move(board, ray, collisionResults, female);
    }

    public void build(Board board, Ray ray, CollisionResults results, Builder builder) {
        if(builder.getBuilderNode().equals(male.getBuilderNode()))
            rules.build(board, ray, results, male);
        else if(builder.getBuilderNode().equals(female.getBuilderNode()))
            rules.build(board, ray, results, female);
    }

    public void resetBuilderPhaseFlags(Builder builder) {
        if(builder.getBuilderNode().equals(male.getBuilderNode()))
        {
            male.setMoved(false);
            male.setBuilt(false);
        }

        else if(builder.getBuilderNode().equals(female.getBuilderNode()))
        {
            female.setBuilt(false);
            female.setMoved(false);
        }
    }
}
