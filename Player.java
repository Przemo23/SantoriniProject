/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
/**
 *
 * @author Przemek
 */
public class Player 
{
    final Spatial PlayerModel;
    byte XTile,YTile;
    byte Floor;
    final AssetManager Manager;
    boolean isActive;
    boolean isBuilding; // true = It's time to build, false = It's time to move
    final float[] Offsets;
    
    
    
  
    public Player(AssetManager ManagerImport,byte X, byte Y)
    {
        
       
        Floor = -1;
        Offsets = new float[]{3.0f,13.0f,20.2f,26.0f};
        isBuilding = false;
        XTile = X;
        YTile = Y;
        Manager = ManagerImport;
        Box PlayerShape = new Box(1.0f,3.0f,1.0f);
        PlayerModel = new Geometry("Player", PlayerShape);
        Material PlayerMat = new Material(Manager, "Common/MatDefs/Misc/Unshaded.j3md");
        PlayerMat.setColor("Color",ColorRGBA.Green);
        PlayerModel.setMaterial(PlayerMat);
        PlayerModel.setLocalTranslation(-52.0f + X*20.0f,40.0f+Offsets[0],-52.0f+Y*20.f);
    }
    
    protected boolean MoveUp(byte Height)
    {
        if(Height<=Floor+1)
        {
            Vector3f Loc = PlayerModel.getLocalTranslation();
            Loc.z+= 20.0f;
            Loc.y = Loc.y + Offsets[Height+1]-Offsets[Floor+1];
            PlayerModel.setLocalTranslation(Loc);
            Floor = Height;
            YTile++;
            return true;
        }    
        return false;
    }
    protected boolean MoveDown(byte Height)
    {
        if(Height<=Floor+1 )
        {
            Vector3f Loc = PlayerModel.getLocalTranslation();
            Loc.z-= 20.0f;
            Loc.y = Loc.y + Offsets[Height+1]-Offsets[Floor+1];
            PlayerModel.setLocalTranslation(Loc);
             Floor = Height;
            YTile--;
            return true;
        }    
        return false;
    }
    protected boolean MoveRight(byte Height)
    {
        if(Height<=Floor+1)
        {
            Vector3f Loc = PlayerModel.getLocalTranslation();
            Loc.x+= 20.0f;
            Loc.y = Loc.y + Offsets[Height+1]-Offsets[Floor+1];
            PlayerModel.setLocalTranslation(Loc);
            Floor = Height;
            XTile++;
            return true;
        }    
        return false;
    }
    protected boolean MoveLeft(byte Height)
    {
        if(Height<=Floor+1)
        { 
            Vector3f Loc = PlayerModel.getLocalTranslation();
            Loc.x-= 20.0f;
            Loc.y = Loc.y + Offsets[Height+1]-Offsets[Floor+1];
            PlayerModel.setLocalTranslation(Loc);
            Floor = Height;
            XTile--;
            return true;
        }    
        return false;
    }
    
}
