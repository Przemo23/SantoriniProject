package com.jme.mygame;



import com.jme3.asset.AssetManager;
import com.jme3.math.*;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.util.SkyFactory;
import com.jme3.water.SimpleWaterProcessor;

import static com.jme3.math.Vector3f.UNIT_Y;
import static com.jme3.util.SkyFactory.EnvMapType.SphereMap;


public class Scene
{
    Node sceneNode;
    Spatial terrain;
    AssetManager assetManager;
    SimpleWaterProcessor waterProcessor;
    Node skyNode;


    public Scene(AssetManager importedManager, Node nodeToAttach, ViewPort viewPort)
    {
        assetManager = importedManager;
        sceneNode = new Node("SceneNode");



        // Terrain initialization
        terrain = assetManager.loadModel("Scenes/Terrain.j3o");
        /**matTerrain = new Material(assetManager,
                "Common/MatDefs/Terrain/TerrainLighting.j3md");
        matTerrain.setBoolean("useTriPlanarMapping", false);
        matTerrain.setFloat("Shininess", 0.0f);

        matTerrain.setTexture("AlphaMap", assetManager.loadTexture(
                "Textures/terrain-alpha/Terrain-terrain-Terrain-alphablend0.png"));
        matTerrain.setTexture("AlphaMap_1", assetManager.loadTexture(
                "Textures/terrain-alpha/Terrain-terrain-Terrain-alphablend1.png"));
        matTerrain.setTexture("AlphaMap_2", assetManager.loadTexture(
                "Textures/terrain-alpha/Terrain-terrain-Terrain-alphablend2.png"));
        */
        /** 1.2) Add GRASS texture into the red layer (Tex1). */
       /** Texture sand = assetManager.loadTexture(
                "Textures/Textures/Sand.jpg");
        sand.setWrap(WrapMode.Repeat);
        matTerrain.setTexture("DiffuseMap_1", sand);
        matTerrain.setFloat("DiffuseMap_1_scale", 8f);

        Texture cliff = assetManager.loadTexture(
                "Textures/Textures/Cliff.jpg");
        cliff.setWrap(WrapMode.Repeat);
        matTerrain.setTexture("DiffuseMap_2", cliff);
        matTerrain.setFloat("DiffuseMap_2_scale", 8f);



        Texture grass = assetManager.loadTexture(
                "Textures/Textures/Grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        matTerrain.setTexture("DiffuseMap_3", grass);
        matTerrain.setFloat("DiffuseMap_3_scale", 8f);

        Texture grassBlock = assetManager.loadTexture(
                "Textures/Textures/GrassBlock.jpg");
        grassBlock.setWrap(Texture.WrapMode.Repeat);
        matTerrain.setTexture("DiffuseMap_4", grassBlock);
        matTerrain.setFloat("DiffuseMap_4_scale", 8f);

        Texture grassBlock2 = assetManager.loadTexture(
                "Textures/Textures/GrassRock2.jpg");
        grassBlock2.setWrap(Texture.WrapMode.Repeat);
        matTerrain.setTexture("DiffuseMap_5", grassBlock2);
        matTerrain.setFloat("DiffuseMap_5_scale", 8f);

        Texture cobble = assetManager.loadTexture(
                "Textures/Textures/CobbleRoad.jpg");
        cobble.setWrap(Texture.WrapMode.Repeat);
        matTerrain.setTexture("DiffuseMap_6", cobble);
        matTerrain.setFloat("DiffuseMap_6_scale", 8f);

        Texture hill = assetManager.loadTexture(
                "Textures/Terrain/Hill.jpg");
        hill.setWrap(WrapMode.Repeat);
        matTerrain.setTexture("DiffuseMap_7", hill);
        matTerrain.setFloat("DiffuseMap_7_scale", 8f);*/

       // terrain.setMaterial(matTerrain);
        terrain.setLocalTranslation(-60, -50, -85);
        sceneNode.attachChild(terrain);

        // Water
        waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(sceneNode);

        //Adding Sky
        skyNode = new Node("SkyNode");
        skyNode.attachChild(SkyFactory.createSky(assetManager, "Textures/TestSky2.jpg", SphereMap));
        skyNode.rotate(FastMath.PI/2,0.0f,0.0f);
        sceneNode.attachChild(skyNode);


        Vector3f waterLocation=new Vector3f(0,-6,0);
        waterProcessor.setPlane(new Plane(UNIT_Y, waterLocation.dot(UNIT_Y)));
        viewPort.addProcessor(waterProcessor);
        waterProcessor.setWaterColor(ColorRGBA.Blue);


        Quad quad = new Quad(2048,2048);
        quad.scaleTextureCoordinates(new Vector2f(6f,6f));

        Geometry water=new Geometry("water", quad);
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        water.setLocalTranslation(-350, -6, 550);
        water.setShadowMode(ShadowMode.Receive);
        water.setMaterial(waterProcessor.getMaterial());
        nodeToAttach.attachChild(water);



        nodeToAttach.attachChild(sceneNode);

    }

}
