package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;


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
        terrain = assetManager.loadModel("Scenes/Map1.j3o");
        terrain.setLocalTranslation(-10, -40, 0);
        rootNode.attachChild(terrain);
        new CameraControl(cam, terrain, inputManager);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
}
}