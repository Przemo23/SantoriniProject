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
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

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
    float Timer =0.0f;
    
    MapTile MapTiles[][]=new MapTile[5][5];
    
    
            @Override
    public void simpleInitApp() {
           
       
        Node FloorNode = new Node("Floor");
        rootNode.attachChild(FloorNode);
        //Node WaterNode = new Node("Water");
       // WaterNode.attachChild(WaterNode);
                
        /*
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
        Dome.setLocalScale(6.0f);*/
        
        
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
        for(byte i=0;i<5;i++) // Initializing 
        {
            for(byte j=0;j<5;j++)
            {
                MapTiles[i][j] = new MapTile(assetManager,i,j);
                FloorNode.attachChild(MapTiles[i][j].TileNode);
               
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
        
        
        
        
        //Testing segment
        MapTiles[0][1].BuildUp();
        MapTiles[0][1].BuildUp();
        MapTiles[0][1].BuildUp();
        MapTiles[1][2].BuildUp();
        MapTiles[1][2].BuildUp();
        MapTiles[1][2].BuildUp();
        MapTiles[1][2].BuildUp();
        MapTiles[1][2].BuildUp();
        MapTiles[3][0].BuildUp();
        MapTiles[3][0].BuildUp();
        MapTiles[2][4].BuildUp();
        
        
        //End of tests
        DirectionalLight sun = new DirectionalLight();
          sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
          sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        initKeys();
        
        
    }
    private void initKeys() {
        // You can map one or several inputs to one named action
        inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Up",     new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Down",   new KeyTrigger(KeyInput.KEY_M));
        // Add the names to the action listener.
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(analogListener, "Left", "Right", "Up","Down");

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
            
            
            if(Timer > 0.5f)
            {
                if (isRunning) {
                    if (name.equals("Up")) {
                        Vector3f v = Player1.Player.getLocalTranslation();
                        Player1.Player.setLocalTranslation(v.x , v.y , v.z + 20.0f);           
                    }
                    if (name.equals("Right")) {
                        Vector3f v = Player1.Player.getLocalTranslation();
                        Player1.Player.setLocalTranslation(v.x + 20.0f, v.y, v.z);
                    }
                    if (name.equals("Left")) {
                        Vector3f v = Player1.Player.getLocalTranslation();
                        Player1.Player.setLocalTranslation(v.x - 20.0f, v.y, v.z);
                    }
                    if (name.equals("Down")) {
                        Vector3f v = Player1.Player.getLocalTranslation();
                        Player1.Player.setLocalTranslation(v.x , v.y, v.z-20.0f);
                    }
                    Timer = 0.0f;
               
                    //isRunning = false;
                } else {
                    System.out.println("Press P to unpause.");
                }
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
        Timer += tpf;//*settings.getFrameRate();
    }

  
}
