package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;



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
    

    
    Player Player1,Player2;
   
    float Timer =0.0f;
    
    MapTile MapTiles[][]=new MapTile[5][5];
    
    
            @Override
    public void simpleInitApp() {
           
       
        Node FloorNode = new Node("Floor");
        rootNode.attachChild(FloorNode);
        //Node WaterNode = new Node("Water");
       // WaterNode.attachChild(WaterNode);
               
        
        
        Spatial Board = assetManager.loadModel("Models/Board.j3o");
        Board.setLocalTranslation(-23.0f, 40.1f, -3.0f);
        Board.setLocalScale(20.0f);
        FloorNode.attachChild(Board);

        //Tiles
        for(byte i=0;i<5;i++) // Initializing 
        {
            for(byte j=0;j<5;j++)
            {
                MapTiles[i][j] = new MapTile(assetManager,i,j);
                FloorNode.attachChild(MapTiles[i][j].TileNode);
               
            }
        }
        
        //Players
        Player1 = new Player(assetManager,(byte)2,(byte)2);
        Player2 = new Player(assetManager,(byte)3,(byte)1);
        MapTiles[2][2].IsBuilder = MapTiles[3][1].IsBuilder =  true;
        Player1.isActive = true;
        Player2.isActive = false;
        
        
        FloorNode.attachChild(Player1.PlayerModel);
        FloorNode.attachChild(Player2.PlayerModel);
        
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
        MapTiles[3][4].BuildUp();
        MapTiles[3][4].BuildUp();
        MapTiles[3][3].BuildUp();
        MapTiles[3][3].BuildUp();
        MapTiles[3][3].BuildUp();
        
        
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

    private final AnalogListener analogListener = new AnalogListener()
    {
            @Override
        public void onAnalog(String name, float value, float tpf) 
        {

            if(Player1.isActive)
            { 
                if(Timer > 0.5f)
                {
                        if (isRunning && !Player1.isBuilding)
                        {
                            if (name.equals("Up")) 
                            {
                               // Vector3f v = Player2.PlayerModel.getLocalTranslation();
                               // Player2.PlayerModel.setLocalTranslation(v.x , v.y , v.z + 20.0f);   
                                if(Player1.YTile<4)
                                {
                                    if(Player1.MoveUp(MapTiles[Player1.XTile][Player1.YTile+1].Height)==true)
                                    {   
                                        MapTiles[Player1.XTile][Player1.YTile].IsBuilder =true;           
                                        MapTiles[Player1.XTile][Player1.YTile-1].IsBuilder =false;
                                    }
                                    else
                                        return;
                                }
                            }
                            if (name.equals("Right"))
                            {
                                if(Player1.XTile<4)
                                {
                                    if(Player1.MoveRight(MapTiles[Player1.XTile+1][Player1.YTile].Height)==true)
                                    {
                                        MapTiles[Player1.XTile][Player1.YTile].IsBuilder =true;        
                                        MapTiles[Player1.XTile-1][Player1.YTile].IsBuilder =false;
                                    }
                                    else
                                        return;
                                }
                           }
                            if (name.equals("Left")) 
                            {
                                if(Player1.XTile>0)
                                {
                                    if(Player1.MoveLeft(MapTiles[Player1.XTile-1][Player1.YTile].Height)==true)
                                    {
                                        MapTiles[Player1.XTile][Player1.YTile].IsBuilder =true;
                                        MapTiles[Player1.XTile+1][Player1.YTile].IsBuilder =false;
                                    }
                                    else
                                        return;
                                }
                            }
                            if (name.equals("Down"))
                            {
                                if(Player1.YTile>0)
                                {
                                    if(Player1.MoveDown(MapTiles[Player1.XTile][Player1.YTile-1].Height)==true)
                                    {
                                        MapTiles[Player1.XTile][Player1.YTile].IsBuilder =true;
                                        MapTiles[Player1.XTile][Player1.YTile+1].IsBuilder =false;
                                    }
                                    else
                                         return;
                                }
                            }
                            Timer = 0.0f;
                            Player1.isBuilding = true;
                            //isRunning = false;
                        }
                        else if(isRunning && Player1.isBuilding)
                        {
                            //TODO Building
                            Player2.isActive = true;
                            Player1.isActive = false;
                            Player1.isBuilding = false;
                            Timer = 0.0f;
                        }
                        else 
                           System.out.println("Press P to unpause.");

                    }
                }
                else
                {
                    if(Timer>0.5f)
                    {
                    if (isRunning && !Player2.isBuilding)
                    {
                        if (name.equals("Up")) 
                        {
                           // Vector3f v = Player2.PlayerModel.getLocalTranslation();
                           // Player2.PlayerModel.setLocalTranslation(v.x , v.y , v.z + 20.0f);   
                            if(Player2.YTile<4)
                            {
                                if(Player2.MoveUp(MapTiles[Player2.XTile][Player2.YTile+1].Height)==true)
                                {
                                    MapTiles[Player2.XTile][Player2.YTile].IsBuilder =true;
                                    MapTiles[Player2.XTile][Player2.YTile-1].IsBuilder =false;
                                }
                                else
                                    return;
                            }
                        }
                        if (name.equals("Right"))
                        {
                            if(Player2.XTile<4)
                            {
                                if(Player2.MoveRight(MapTiles[Player2.XTile+1][Player2.YTile].Height)==true)
                                {
                                    MapTiles[Player2.XTile][Player2.YTile].IsBuilder =true;    
                                    MapTiles[Player2.XTile-1][Player2.YTile].IsBuilder =false;
                                }
                                else
                                    return;
                            }
                       }
                        if (name.equals("Left")) 
                        {
                            if(Player2.XTile>0)
                            {
                                if(Player2.MoveLeft(MapTiles[Player2.XTile-1][Player2.YTile].Height)==true)
                                {
                                    MapTiles[Player2.XTile][Player2.YTile].IsBuilder =true;
                                    MapTiles[Player2.XTile+1][Player2.YTile].IsBuilder =false;
                                }
                                else
                                    return;
                            }
                        }
                        if (name.equals("Down"))
                        {
                            if(Player2.YTile>0)
                            {
                                if(Player2.MoveDown(MapTiles[Player2.XTile][Player2.YTile-1].Height)==true)
                                {
                                    MapTiles[Player2.XTile][Player2.YTile].IsBuilder =true;
                                    MapTiles[Player2.XTile][Player2.YTile+1].IsBuilder =false;
                                }
                                else
                                    return;
                            }
                        }
                        Timer = 0.0f;
                        Player2.isBuilding = true;
                                //isRunning = false;
                    } 
                    else if(isRunning && Player2.isBuilding)
                    {
                        //TODO Building
                        Player1.isActive = true;
                        Player2.isActive = false;
                        Player2.isBuilding = false;
                        Timer = 0.0f;
                    }
                    else 
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
    public void simpleUpdate(float tpf) 
    {
        Timer += tpf;//*settings.getFrameRate();
    }

  
}
            
