package com.jme.mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.material.Material;
import com.jme3.texture.Texture.WrapMode;


public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }
    Spatial terrain;
    @Override
    public void simpleInitApp() {
        Board board = new Board(this);
        Player player = new Player(assetManager);
        player.attachBuilder(player.female, rootNode).attachBuilder(player.male, rootNode);
        Scene scene = new Scene(assetManager,rootNode,viewPort);
        new CameraControl(cam, scene.terrain, inputManager);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,-1,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
}
}