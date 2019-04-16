package appStates;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import model.*;
import view.CameraControl;
import view.Scene;


class InitializationState extends AbstractAppState {
    private SimpleApplication app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private Camera cam;
    private Board board;


    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (Game) app; // can cast Application to something more specific
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.cam = this.app.getCamera();
        ((Game) app).board = new Board((Game) app);
        this.board = ((Game) app).board;

        ((Game) app).player = new Player[((Game) app).getPlayerNumber()];
        for(int i = 0; i < ((Game) app).player.length; i++)
            ((Game)app).player[i] = new Player(((Game) app) , "Blue");
       // createPlayers((Game) app, board);
        new Scene(assetManager, rootNode, viewPort);
        new CameraControl(cam, board.boardCentre(), inputManager);
        stateManager.attach(((Game) app).bSS);
    }

}