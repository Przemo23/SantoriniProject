package mygame;

import com.jme3.scene.Node;

public class Player  {
    Builder male, female;
    BasicRules rules;
    Game game;

    public Player(Game game, String color) {
        this.game = game;
        male = new Builder(game.getAssetManager(), color);
        female = new Builder(game.getAssetManager(), color);
        rules = new BasicRules();
    }
    public void attachBuilder(Builder builder, Node node, int column, int row) {
        builder.getBuilderModel().setLocalTranslation(-52.0f + column*20.0f, -0.0f,-52.0f+row*20.f);
        node.attachChild(builder.getBuilderModel());
        builder.setCoordinates(column, row);
        builder.setEnabled(true);
        game.board.getTile(column, row).setOccupied(true);
    }
    public boolean isBuilderSet(Builder builder) {
        return builder.isSet();
    }
}
