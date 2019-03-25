package com.jme.mygame;

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
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.component.QuadBackgroundComponent;

public class InGameState extends AbstractAppState {
    private SimpleApplication app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera cam;
    private Board board;
    private Player[] player;
    private Builder selectedBuilder; //a builder, who was chosen by a player to make his turn
    private int active; // player[active] is the one whose turn is currently considered
    static int roundPhase;
    static final int SELECTION_PHASE = 0;
    static final int MOVEMENT_PHASE = 1;
    static final int BUILDING_PHASE = 2;
    private TextField turnPanel;

    @Override
    public void initialize(AppStateManager stateManager, Application app) { super.initialize(stateManager, app);
        this.app = (Game) app; // can cast Application to something more specific
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.cam = this.app.getCamera();
        this.board = ((Game) app).board;
        this.player = ((Game) app).player;
        this.active = 0;
        this.roundPhase = SELECTION_PHASE;

        QuadBackgroundComponent sth= new QuadBackgroundComponent();
        sth.setTexture(assetManager.loadTexture("Textures/Textures/CobbleRoad.jpg"));
        Container textContainer = new Container();
        textContainer.setLocalTranslation(0.0f,100.0f,0.0f);
        textContainer.setPreferredSize(new Vector3f(cam.getWidth()/9,cam.getHeight()/15,0.0f));
        textContainer.setBackground(sth);
        turnPanel = textContainer.addChild(new TextField("Turn indicator"));
        turnPanel.setColor(ColorRGBA.Orange);
        turnPanel.setText("Player " + (active +1)+ "'s turn.");
        turnPanel.setSingleLine(false);


        ((Game) app).getGuiNode().attachChild(textContainer);
        initKeys();
    }
    private void addFunctionality(String mappingName){
        inputManager.addMapping(mappingName, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, mappingName);
    }
    private void initKeys() {
        inputManager.addMapping("selectBuilder", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("cancelBuilder", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(actionListener, "selectBuilder");
        inputManager.addListener(actionListener, "cancelBuilder");
    }
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            CollisionResults results = new CollisionResults();
            Vector2f click2d = inputManager.getCursorPosition().clone();
            Vector3f click3d = cam.getWorldCoordinates(click2d, 0f).clone();
            Vector3f dir = cam.getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();
            Ray ray = new Ray(click3d, dir);
/////////////////// If we want to select one of two available builders

            if (name.equals("selectBuilder") && !keyPressed) {
                player[active].getBuildersNode().collideWith(ray, results);

            /// If a cursor collides with a builder, we pick the closest one

                if (results.size() > 0)
                {
                    CollisionResult closest = results.getClosestCollision();
                    if(player[active].collidingBuilder(closest) != null)
                    {
                        if(selectedBuilder != null)
                            board.showAvailableTiles(selectedBuilder, false);
                        roundPhase = MOVEMENT_PHASE;
                        selectedBuilder = player[active].collidingBuilder(closest);
                        board.showAvailableTiles(selectedBuilder, true);
                        if(!inputManager.hasMapping("moveBuilder"))
                                addFunctionality("moveBuilder");
                    }
                }
            }
////////////////// We decided to choose the other builder
            else if(name.equals("cancelBuilder") && !keyPressed && selectedBuilder != null)
            {
                    board.showAvailableTiles(selectedBuilder, false);
                    selectedBuilder = null;
                    inputManager.deleteMapping("moveBuilder");
                    roundPhase = SELECTION_PHASE;
            }
///////////////// We decided to move the selected builder
            else if(name.equals("moveBuilder") && !keyPressed && selectedBuilder != null)
            {
                player[active].moveBuilder(board, ray, results, selectedBuilder);
                if(selectedBuilder.hasMoved())
                {
                    if(player[active].rules.isWinAccomplished(selectedBuilder))
                    {
                        System.out.println("Player " + (active+1) + " WINS!!!!!");
                        System.exit(1);
                    }
                    inputManager.deleteMapping("selectBuilder");
                    inputManager.deleteMapping("cancelBuilder");
                    inputManager.deleteMapping("moveBuilder");
                    addFunctionality("buildPhase");
                    roundPhase = BUILDING_PHASE;
                    board.showAvailableTiles(selectedBuilder, true);
                }
            }
///////////////// Builder has moved - it is time to build (if possible)
            else if(name.equals("buildPhase") && !keyPressed){
                player[active].build(board, ray, results, selectedBuilder);
                if(selectedBuilder.hasBuilt())
                {
                    inputManager.addMapping("cancelBuilder", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
                    inputManager.addListener(actionListener,"cancelBuilder");
                    inputManager.deleteMapping("buildPhase");
                    player[active].resetBuilderPhaseFlags(selectedBuilder);
                    selectedBuilder = null;
                    active = (active+1)%(player.length);
                    addFunctionality("selectBuilder");
                    roundPhase = SELECTION_PHASE;
                }
            }
            }

    };
    @Override
    public void update(float fpt)
    {
        switch (roundPhase)
        {
            case 0:
                turnPanel.setText("Player " + (active + 1) + "'s turn." + '\n' + "Select one of your builders");
                break;

            case 1:
                turnPanel.setText("Player " + (active + 1) + "'s turn." + '\n' + "Choose where you want to move");
                break;
            case 2:
                turnPanel.setText("Player " + (active + 1) + "'s turn." + '\n' + " Choose the tile on which you want to build.");
                break;

                default:
                    break;
        }
    }
}
