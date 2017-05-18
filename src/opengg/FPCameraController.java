/*************************************************************
 *     file: FPCameraController.java
 *     authors: OpenGG (Shun Lu, Roenyl Tisoy, Tuan Pham, Evan Gunell)
 *     class: CS 445 - Computer Graphics
 * 
 *     assignment: program 3
 *     last modified: 5/9/2017
 * 
 *     purpose: This class handles the operations and movements
 *     of the 3D Cube.
 * 
 * 
 *************************************************************/

package opengg;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

public class FPCameraController {
    //3d vector to store the camera's position in
    private Vector3f position = null;
    private Vector3f lPosition = null;
    //the rotation around the Y axis of the camera
    private float yaw = 0.0f;
    //the rotation around the X axis of the camera
    private float pitch = 0.0f;
    private Vector3Float me;
    private Chunk chunk;
   
    /**
     * METHOD: constructor
     * PURPOSE: instantiates position of Vector3f to the x, y, and z parameters
     */
    public FPCameraController(float x, float y, float z) {
    //instantiate position Vector3f to the x y z params.
        position = new Vector3f(x, y, z);
        lPosition= new Vector3f(x, y, z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
        chunk = new Chunk((int) x, (int) y, (int) z);
    }
    
    /**
     * METHOD: yaw
     * PURPOSE: sets the yaw Euler angle to the parameter
     */
    public void yaw(float amount) {
        yaw += amount;
    }

    /**
     * METHOD: pitch
     * PURPOSE: sets the pitch Euler angle to the parameter
     */
    public void pitch(float amount) {
        pitch -= amount;
    }

    /**
     * METHOD: walkForward
     * PURPOSE: calculates the distance the 3D Cube moves forward
     */
    public void walkForward(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    /**
     * METHOD: walkBackward
     * PURPOSE: calculates the distance the 3D Cube moves backward
     */
    public void walkBackward(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }

    /**
     * METHOD: stafeLeft
     * PURPOSE: calculates the distance the 3D Cube strafes left
     */
    public void strafeLeft(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw - 90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw - 90));
        position.x -= xOffset;
        position.z += zOffset;
    }

     /**
     * METHOD: strafeRight
     * PURPOSE: calculates the distance the 3D Cube strafes right
     */
    public void strafeRight(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw + 90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw + 90));
        position.x -= xOffset;
        position.z += zOffset;
    }

     /**
     * METHOD: moveUp
     * PURPOSE: calculates the distance the 3D Cube moves up
     */
    public void moveUp(float distance) {
        position.y -= distance;
    }
    
     /**
     * METHOD: moveDown
     * PURPOSE: calculates the distance the 3D Cube moves down
     */
    public void moveDown(float distance) {
        position.y += distance;
    }

    /**
     * METHOD: lookThrough
     * PURPOSE: handles the calculations for the rotation and 
     * translation of the 3D Cube
     */
    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.x, position.y, position.z);
    }

    /**
     * METHOD: gameLoop
     * PURPOSE: listens and performs the functions based on what key
     * is pressed
     */
    public void gameLoop() {
        FPCameraController camera = new FPCameraController(0, 0, 0);

        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f;
        float lastTime = 0.0f;
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = 0.35f;
        // hide the mouse
        Mouse.setGrabbed(true);

        while (!Display.isCloseRequested() &&
               !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            time = Sys.getTime();
            lastTime = time;
            dx = Mouse.getDX();
            dy = Mouse.getDY();
            camera.yaw(dx * mouseSensitivity);
            camera.pitch(dy * mouseSensitivity);

            // Check input
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                camera.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                camera.walkBackward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                camera.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                camera.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                camera.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                camera.moveDown(movementSpeed);
            }

            // draw
            glLoadIdentity();
            glEnable(GL_DEPTH_TEST);
            glDepthFunc(GL_LESS);
            camera.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            chunk.render();
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        Keyboard.destroy();
    }

    /**
     * METHOD: render
     * PURPOSE: initialize and render the 3D cube
     */
    private void render() {
        Cube3D cube = new Cube3D(new Vector3Float(0f, 0f, -5.0f), 1.0f);
        cube.draw();
    }
}
