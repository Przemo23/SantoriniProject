package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Spatial;
import com.jme3.math.Quaternion;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.util.SkyFactory;
import com.jme3.water.SimpleWaterProcessor;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Santorini extends SimpleApplication {

    public static void main(String[] args) {
        Santorini app = new Santorini();
        app.start();
    }
    private boolean isRunning = true;
    Spatial Terrain;
    

    protected class PlayerClass
    {
        public PlayerClass(){};
        Spatial Player;
        byte XTile,YTile;
       
    }
    PlayerClass Player1 = new PlayerClass();
    
            @Override
    public void simpleInitApp() {
           
        Node FloorNode = new Node("Floor");
        rootNode.attachChild(FloorNode);
        Node WaterNode = new Node("Water");
        WaterNode.attachChild(WaterNode);
                
        
        Spatial TestBlock = assetManager.loadModel("Models/Parter.j3o");
  
        FloorNode.attachChild(TestBlock);
        TestBlock.setLocalTranslation(-52.0f,40.0f,-52.0f);
        TestBlock.setLocalScale(3.0f);
        Spatial TestBlock2 = assetManager.loadModel("Models/Second.j3o");

        FloorNode.attachChild(TestBlock2);
        TestBlock2.setLocalTranslation(-52.0f,57.2f,-52.0f);
        TestBlock2.setLocalScale(3.0f);
        
        Spatial TestBlock3 = assetManager.loadModel("Models/Pierwsze.j3o");
        FloorNode.attachChild(TestBlock3);
        TestBlock3.setLocalTranslation(-52.0f,46.0f,-52.0f);
        TestBlock3.setLocalScale(3.0f);
        
        Spatial Dome = assetManager.loadModel("Models/Dome.j3o");
        FloorNode.attachChild(Dome);
        Dome.setLocalTranslation(-52.0f,61.0f,-52.0f);
        Dome.setLocalScale(6.0f);
        
        
        Spatial Board = assetManager.loadModel("Models/Board.j3o");
        Board.setLocalTranslation(-23.0f, 40.1f, -3.0f);
        Board.setLocalScale(20.0f);
        FloorNode.attachChild(Board);
        
        
        // Prototype player
        
        Box PlayerShape = new Box(1.2f,3.6f,1.2f);
        Player1.Player = new Geometry("Player",PlayerShape);
        Material PlayerMat = new Material(
                         assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        PlayerMat.setColor("Color", ColorRGBA.Blue);
        Player1.Player.setMaterial(PlayerMat);
        Player1.Player.setLocalTranslation(-13.0f,43.6f,-13.0f);
        FloorNode.attachChild(Player1.Player);
        //Tiles
        Box FloorTileShape = new Box(10.0f,0.1f,10.0f);
        Spatial[][] FloorTiles = new Spatial[5][5];
        
        for(byte i=0;i<5;i++)
        {
            for(byte j=0;j<5;j++)
            {
                //Material and shape
                Spatial FloorTile = new Geometry("Tile",FloorTileShape);
                Material TileMat = new Material(
                         assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                //TileMat.setColor("Color", ColorRGBA.randomColor());
                if((j-i)%2==0)
                    TileMat.setColor("Color",ColorRGBA.White);
                else
                    TileMat.setColor("Color",ColorRGBA.Black);
                FloorTile.setMaterial(TileMat);
               
       
                //Location
                FloorTile.setLocalTranslation(-53.0f + 20*i,40.0f,-53.0f +20*j);
                FloorTiles[i][j] = FloorTile;
                FloorNode.attachChild(FloorTiles[i][j]);
            }
        }
        
        // Terrain
        Terrain = assetManager.loadModel("Scenes/Map1.j3o");
        FloorNode.attachChild(Terrain);
        FloorNode.scale(0.07f,0.07f,0.07f);
        
        //Rotation
               Quaternion Rot = new Quaternion();
               Rot.fromAngleAxis(FastMath.PI/8, new Vector3f(1f,-1f,0f));
                 FloorNode.setLocalRotation(Rot);
        
        
        
        
        
        
        DirectionalLight sun = new DirectionalLight();
          sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
          sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        
    }
    private void initKeys() {
        // You can map one or several inputs to one named action
        inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                                          new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        // Add the names to the action listener.
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(analogListener, "Left", "Right", "Rotate");

    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                isRunning = !isRunning;
            }
        }
    };

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (isRunning) {
                if (name.equals("Up")) {
                    Player1.Player.rotate(0, value * speed, 0);
                }
                if (name.equals("Right")) {
                    Vector3f v = Player1.Player.getLocalTranslation();
                    Player1.Player.setLocalTranslation(v.x + value * speed, v.y, v.z);
                }
                if (name.equals("Left")) {
                    Vector3f v = Player1.Player.getLocalTranslation();
                    Player1.Player.setLocalTranslation(v.x - value * speed, v.y, v.z);
                }
                if (name.equals("Left")) {
                    Vector3f v = Player1.Player.getLocalTranslation();
                    Player1.Player.setLocalTranslation(v.x - value * speed, v.y, v.z);
                }
            } else {
                System.out.println("Press P to unpause.");
            }
        }
    };
    /*
    // we create a water processor
SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(assetManager);
waterProcessor.setReflectionScene(WaterNode);

// we set the water plane
Vector3f waterLocation=new Vector3f(0,-6,0);
waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y)));
viewPort.addProcessor(waterProcessor);

// we set wave properties
waterProcessor.setWaterDepth(40);         // transparency of water
waterProcessor.setDistortionScale(0.05f); // strength of waves
waterProcessor.setWaveSpeed(0.05f);       // speed of waves

// we define the wave size by setting the size of the texture coordinates
Quad quad = new Quad(400,400);
quad.scaleTextureCoordinates(new Vector2f(6f,6f));

// we create the water geometry from the quad
Geometry water=new Geometry("water", quad);
water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
water.setLocalTranslation(-200, -6, 250);
water.setShadowMode(ShadowMode.Receive);
water.setMaterial(waterProcessor.getMaterial());
rootNode.attachChild(water);
    */
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

  
}
