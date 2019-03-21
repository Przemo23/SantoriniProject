package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class BuilderSetState extends AbstractAppState {
    private SimpleApplication app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera cam;
    private Geometry mark;
    private Builder phantomBuilder;
    private Board board;
    private Player[] player;
    private int buildersCount;


    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (Game) app; // can cast Application to something more specific
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.cam = this.app.getCamera();
        this.board = ((Game) app).board;
        this.player = ((Game) app).player;
        phantomBuilder = new Builder(assetManager, "White");
        rootNode.attachChild(phantomBuilder.getBuilderModel());
        buildersCount = 0;
        initMark();
        initKeys();
    }
    @Override
    public void setEnabled(boolean value) {
        if(!value)
            actionListener = null;
    }
    private void initKeys() {
        inputManager.addMapping("selectTile", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "selectTile");
    }
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("selectTile") && !keyPressed) {
                // 1. Reset results list.
                boolean builderWasSet = false;
                CollisionResults results = new CollisionResults();
                Vector2f click2d = inputManager.getCursorPosition().clone();
                Vector3f click3d = cam.getWorldCoordinates(click2d, 0f).clone();
                Vector3f dir = cam.getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();
                Ray ray = new Ray(click3d, dir);
                board.getBoardNode().collideWith(ray, results);
                if (results.size() > 0) {
                    CollisionResult closest = results.getClosestCollision();
                    for(int i = 0; i<player.length; i++){
                        for(int column = 0; column<5; column++){
                            for(int row = 0; row<5; row++)
                                if (board.collidingTile(column, row, closest) != null && !board.getTile(column, row).isCompleted()) {
                                    if (!player[i].isBuilderSet(player[i].male)) {
                                        player[i].attachBuilder(player[i].male, column, row);
                                        System.out.println("Player " + i + " male was set");
                                        builderWasSet = true;
                                        buildersCount++;
                                        break;
                                    } else if (!player[i].isBuilderSet(player[i].female)) {
                                        player[i].attachBuilder(player[i].female, column, row);
                                        System.out.println("Player " + i + " female was set");
                                        builderWasSet = true;
                                        buildersCount++;
                                        break;
                                    }
                            }
                                if(builderWasSet) break;
                        }
                        if(builderWasSet) break;
                    }                      // Let's interact - we mark the hit with a red dot.
                        mark.setLocalTranslation(closest.getContactPoint());
                        rootNode.attachChild(mark);
                        if(buildersCount == 2*player.length) {
                            System.out.println("Detaching BuilderSetState");
                            stateManager.detach(BuilderSetState.this);
                        }
                }
                else {
                    // No hits? Then remove the red mark.
                    rootNode.detachChild(mark);
                }
            }
        }
    };

    @Override
    public void cleanup() {
        super.cleanup();
        actionListener = null;
        inputManager.deleteMapping("selectTile");
        rootNode.detachChild(phantomBuilder.getBuilderModel());
        ((Game) app).iGS = new InGameState();
        stateManager.attach(((Game) app).iGS);
    }

    private void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }

    @Override
    public void update(float tpf) {
        CollisionResults results = new CollisionResults();
        Vector2f click2d = inputManager.getCursorPosition().clone();
        Vector3f click3d = cam.getWorldCoordinates(click2d, 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);
        board.getBoardNode().collideWith(ray, results);
        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            for(int column = 0; column<5; column++)
                for(int row = 0; row<5; row++)
                    if (board.collidingTile(column, row, closest) != null && !board.getTile(column, row).isCompleted())
                        phantomBuilder.getBuilderModel().setLocalTranslation(-52.0f + column*20.0f, 3.0f,-52.0f+row*20.f);
        }

    }
}
