package com.jme.mygame;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Node;

public class Player implements BasicRules {
    Game game;                 // reference to the main file
   // BasicRules rules;          // rules that dictate way of moving, building, checking win condition etc.
    Builder male, female;      // each player has two Builder references
    private Node buildersNode; // keeps both builder models (necessary for checking cursor collisions

    public Player(Game game, String color) {
    // 1. preliminary initialization
        this.game = game;
        this.buildersNode = new Node("BuildersNode");
        this.male = new Builder(game.getAssetManager(), color);
        this.female = new Builder(game.getAssetManager(), color);
        //this.rules = new BasicRules();
        game.getRootNode().attachChild(buildersNode);
    }

/** Returns true if male/female builder of a player was set OR false if not */
    public boolean isBuilderSet(Builder builder) {
    return builder.isSet();
}

/** Attaches a builder to the board during the initialization phase */
    public void attachBuilder(Builder builder, int column, int row) {
    // 1. move and show the builder
        builder.getBuilderModel().setLocalTranslation(-52.0f + column*20.0f, Builder.OFFSETS[builder.getFloorLvl().height],-52.0f+row*20.f);
        buildersNode.attachChild(builder.getBuilderNode());
    // 2. update builder's coordinates and movable/buildable flags
        builder.setCoordinates(column, row);
        builder.setEnabled(true);
        game.board.getTile(column, row).setBuildable(false);
        game.board.getTile(column, row).setMovable(false);
    }

/** Returns a builder that the cursor collided with - basically enables a selection of chosen builder */
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

/** Returns a node that contains both builder models (necessary for checking collisions) */
    public Node getBuildersNode() {
        return buildersNode;
    }

/** Moves a picked builder on the selected tile (redirects arguments to the rules.move(....) */
    public void  moveBuilder(Board board, Ray ray, CollisionResults collisionResults, Builder builder){

        if(builder.getBuilderNode().equals(male.getBuilderNode()))
            this.move(board, ray, collisionResults, male);

        else if(builder.getBuilderNode().equals(female.getBuilderNode()))
            this.move(board, ray, collisionResults, female);
    }

/** Builds a floor on the selected tile (redirects arguments to the rules.build(....) function) */
    public void orderBuild(Board board, Ray ray, CollisionResults results, Builder builder) {
        if(builder.getBuilderNode().equals(male.getBuilderNode()))
            this.build(board, ray, results, male);
        else if(builder.getBuilderNode().equals(female.getBuilderNode()))
            this.build(board, ray, results, female);
    }

/** After the end of a turn, we reset flags that tell us whether builder has moved/built in this turn (preparation for the next turn) */
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
