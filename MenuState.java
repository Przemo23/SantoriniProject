package com.jme.mygame;

import com.jme3.app.Application;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.GuiControl;
import com.simsilica.lemur.core.GuiLayout;
import com.simsilica.lemur.style.BaseStyles;

import java.util.Collection;


public class MenuState extends AbstractAppState {

    private SimpleApplication app;
    private Node guiNode;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Camera cam;
    private float windowHeight, windowWidth;
    private float tabHeight, tabWidth;
    //private AppSettings settings;


    @Override
    public void initialize(AppStateManager stateManager, Application appImp) {

        super.initialize(stateManager, app);
        this.app = (Game) appImp; // can cast Application to something more specific
        this.rootNode = this.app.getRootNode();
        this.guiNode = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.cam = this.app.getCamera();

        windowHeight = cam.getHeight();
        windowWidth = cam.getWidth();

        tabWidth = windowWidth/6;
        tabHeight = windowHeight/3;



        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();










        // Set 'glass' as the default style when not specified
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        QuadBackgroundComponent sth= new QuadBackgroundComponent();
        sth.setTexture(assetManager.loadTexture("Textures/Textures/Sand.jpg"));
        //sth.setColor(ColorRGBA.Red);


        //startScreen.setPreferredSize();

        // Create a simple container for our elements
        Container myWindow = new Container();
        myWindow.setBackground(sth);
        myWindow.setPreferredSize(new Vector3f(windowWidth,windowHeight,0.0f));
        guiNode.attachChild(myWindow);
        myWindow.setLocalTranslation(0f, windowHeight, 0);




        Container buttons = new Container();
        //buttons.setLocalScale(0.2f);
        buttons.setPreferredSize(new Vector3f(tabWidth,tabHeight,0.0f));
        buttons.setLocalTranslation(windowWidth/2-tabWidth/2, windowHeight/2+tabHeight/2, 0);

        guiNode.attachChild(buttons);

        Button twoPlayers = buttons.addChild(new Button("Two Players"));
        twoPlayers.setColor(ColorRGBA.Magenta);

        Button threePlayers = buttons.addChild(new Button("Three Players"));
        threePlayers.setColor(ColorRGBA.Green);

        Button fourPlayers = buttons.addChild(new Button("Four Players"));
        threePlayers.setColor(ColorRGBA.Green);



        twoPlayers.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("The world is yours.");
                stateManager.cleanup();
                ((Game) app).setPlayerNumber( 2);
                stateManager.attach(((Game) app).iS);
                stateManager.detach(((Game) app).mS);
            }
        });


        threePlayers.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("The world is yours.");
                ((Game) app).setPlayerNumber( 3);
                stateManager.cleanup();
                stateManager.attach(((Game) app).iS);
                stateManager.detach(((Game) app).mS);
            }
        });

        fourPlayers.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("The world is yours.");
                ((Game) app).setPlayerNumber( 4);
                stateManager.cleanup();
                stateManager.attach(((Game) app).iS);
                stateManager.detach(((Game) app).mS);

            }
        });





    }

    @Override
    public void cleanup()
    {
        super.cleanup();
        guiNode.detachAllChildren();

    }



}

