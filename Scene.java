package com.jme.mygame;



import com.jme3.math.Plane;
import com.jme3.scene.Spatial;
import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;
import com.jme3.material.Material;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.scene.Node;
import com.jme3.water.SimpleWaterProcessor;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.Geometry;
import com.jme3.math.Quaternion;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.math.FastMath;
import com.jme3.math.ColorRGBA;
import com.jme3.util.SkyFactory;


import static com.jme3.math.Vector3f.UNIT_Y;


public class Scene
{
    Node sceneNode;
    Spatial terrain;
    Material matTerrain;
    AssetManager assetManager;
    SimpleWaterProcessor waterProcessor;
    Node skyNode;


    public Scene(AssetManager importedManager, Node nodeToAttach, ViewPort viewPort)
    {
        assetManager = importedManager;
        sceneNode = new Node("SceneNode");



        // Terrain initialization
        terrain = assetManager.loadModel("Scenes/Map1.j3o");
        matTerrain = new Material(assetManager,
                "Common/MatDefs/Terrain/Terrain.j3md");

        matTerrain.setTexture("Alpha", assetManager.loadTexture(
                "Textures/terrain-alpha/Map1-terrain-Map1-alphablend0.png"));

        /** 1.2) Add GRASS texture into the red layer (Tex1). */
        Texture hill2 = assetManager.loadTexture(
                "Textures/Terrain/Hill.jpg");
        hill2.setWrap(WrapMode.Repeat);
        matTerrain.setTexture("Tex1", hill2);
        matTerrain.setFloat("Tex1Scale", 8f);

        Texture hill = assetManager.loadTexture(
                "Textures/Terrain/Hill.jpg");
        hill.setWrap(WrapMode.Repeat);
        matTerrain.setTexture("Tex2", hill);
        matTerrain.setFloat("Tex2Scale", 8f);

        Texture grass = assetManager.loadTexture(
                "Textures/Terrain/Grass2.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        matTerrain.setTexture("Tex3", grass);
        matTerrain.setFloat("Tex3Scale", 8f);


        terrain.setMaterial(matTerrain);
        terrain.setLocalTranslation(-0, -40, 0);
        sceneNode.attachChild(terrain);

        // Water
        waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(sceneNode);

        //Adding Sky
        skyNode = new Node("SkyNode");
        skyNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Sky/blue_clear/4/SKY01.DDS", true));
        //skyNode.scale(0.1f);
        sceneNode.attachChild(skyNode);
        Vector3f waterLocation=new Vector3f(0,-6,0);
        waterProcessor.setPlane(new Plane(UNIT_Y, waterLocation.dot(UNIT_Y)));
        viewPort.addProcessor(waterProcessor);
        waterProcessor.setWaterColor(ColorRGBA.Blue);


        Quad quad = new Quad(400,400);
        quad.scaleTextureCoordinates(new Vector2f(6f,6f));

        Geometry water=new Geometry("water", quad);
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        water.setLocalTranslation(-200, -6, 250);
        water.setShadowMode(ShadowMode.Receive);
        water.setMaterial(waterProcessor.getMaterial());
        nodeToAttach.attachChild(water);



        nodeToAttach.attachChild(sceneNode);

    }

}
