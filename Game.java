package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Spatial;


public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }
    Spatial terrain;
    @Override
    public void simpleInitApp() {
        Board board = new Board(assetManager, rootNode);
        terrain = assetManager.loadModel("Scenes/Map1.j3o");
        terrain.setLocalTranslation(-10, -40, 0);
        rootNode.attachChild(terrain);
        new CameraControl(cam, terrain, inputManager);

}
}