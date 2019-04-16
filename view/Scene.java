package view;
import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
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


public final class Scene
{
    private Node sceneNode;
    private Spatial terrain;
    private AssetManager assetManager;
    private SimpleWaterProcessor waterProcessor;
    private Node skyNode;


    public Scene(AssetManager importedManager, Node nodeToAttach, ViewPort viewPort)
    {
        assetManager = importedManager;
        sceneNode = new Node("SceneNode");



        // Terrain initialization
        terrain = assetManager.loadModel("Scenes/Terrain.j3o");

       // terrain.setMaterial(matTerrain);
        terrain.setLocalTranslation(-60, -50, -85);
        sceneNode.attachChild(terrain);

        // Water
        waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(sceneNode);
        //waterProcessor.setWaveSpeed(2.0f);
        //waterProcessor.setDistortionScale(10.0f);

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
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,-1,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        nodeToAttach.addLight(sun);


        nodeToAttach.attachChild(sceneNode);

    }

}
