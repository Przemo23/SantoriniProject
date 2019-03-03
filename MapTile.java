/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;



import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Spatial;


/**
 *
 * @author Przemek
 */
public class MapTile {
    int Height = -1; // -1 = ground, 0 = parter, 1 = first floor, 2 = second floor, 3 = dome
    boolean IsBuilder = false; // 0 = Builder is not standing here, 1 = Builder is standing on this tile
    byte X,Y;
    Node TileNode;
    Spatial FloorTile,Parter,FirstFloor,SecondFloor,Dome;
    AssetManager Manager;
    //public MapTile(){};
    public MapTile(AssetManager ManagerImport,byte i,byte j)
    {
        
        TileNode = new Node("TileNode");
        X = i;
        Y = j;
        Box FloorTileShape = new Box(10.0f,0.1f,10.0f);
        FloorTile = new Geometry("Tile",FloorTileShape);
        Manager = ManagerImport;        
        Material TileMat = new Material(Manager, "Common/MatDefs/Misc/Unshaded.j3md");
        TileMat.setColor("Color",ColorRGBA.White);
        FloorTile.setMaterial(TileMat);
        FloorTile.setLocalTranslation(-53.0f + 20*i,40.0f,-53.0f +20*j);
        TileNode.attachChild(FloorTile);
        Parter = Manager.loadModel("Models/Parter.j3o");
        FirstFloor = Manager.loadModel("Models/Pierwsze.j3o");
        SecondFloor = Manager.loadModel("Models/Second.j3o");
        Dome = Manager.loadModel("Models/Dome.j3o");
        Parter.setLocalTranslation(-52.0f+X*20.0f,40.0f,-52.0f+Y*20.0f);
        Parter.setLocalScale(3.0f);
        FirstFloor.setLocalTranslation(-52.0f+X*20.0f,46.0f,-52.0f+Y*20.0f);
        FirstFloor.setLocalScale(3.0f);
        SecondFloor.setLocalTranslation(-52.0f+X*20.0f,57.2f,-52.0f+Y*20.0f);
        SecondFloor.setLocalScale(3.0f);
        Dome.setLocalTranslation(-52.0f+X*20.0f,61.0f,-52.0f+Y*20.0f);
        Dome.setLocalScale(6.0f);
        
    }
    
    /**
     *
     * @return
     */
    protected boolean BuildUp()
    {
           if(IsBuilder == true || Height == 3)
               return false;
           Height++;
            
           switch(Height)
           {
               case 0:
                   
                   TileNode.attachChild(Parter);
                   break;
               case 1:
                   TileNode.attachChild(FirstFloor);
                   break;
               case 2:
                   TileNode.attachChild(SecondFloor);
                   break;
               case 3:
                   TileNode.attachChild(Dome);
                   break;
               default:
                   break;
                   
           }
           return true;
               
    }
}
