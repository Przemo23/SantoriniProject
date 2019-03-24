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

class InitializationState extends AbstractAppState{
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
        ((Game) app).player[0] = new Player(((Game) app), "Blue");
        ((Game) app).player[1] = new Player(((Game) app), "Red");
        if(((Game) app).getPlayerNumber()>=3)
            ((Game) app).player[2] = new Player(((Game) app), "Green");
        if(((Game) app).getPlayerNumber()==4)
            ((Game) app).player[3] = new Player( ((Game) app), "Magenta");

        new Scene(assetManager, rootNode, viewPort);
        new CameraControl(cam, board.boardCentre(), inputManager);
        stateManager.attach(((Game) app).bSS);
    }
}