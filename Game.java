package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Spatial;



/** Sample 8 - how to let the user pick (select) objects in the scene
 * using the mouse or key presses. Can be used for shooting, opening doors, etc. */
public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();
        app.start();
    }
    @Override
    public void simpleInitApp() {
        Board board = new Board(assetManager, rootNode);
        Spatial Terrain;
        Terrain = assetManager.loadModel("Scenes/Map1.j3o");
        FloorNode.attachChild(Terrain);
        FloorNode.scale(0.07f,0.07f,0.07f);

}
}