/*************************************************************
 *     file: Main.java
 *     authors: OpenGG (Shun Lu, Roenyl Tisoy, Tuan Pham, Evan Gunell)
 *     class: CS 445 - Computer Graphics
 * 
 *     assignment: program 3
 *     last modified: 5/9/2017
 * 
 *     purpose: This class displays an OpenGL window with size
 *     640 x 480, creates a Keyboard object, and initializes the
 *     projection matrix.
 * 
 *************************************************************/
package opengg;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author Shun Lu
 */
public class Main {
    private FPCameraController fp;
    private DisplayMode displayMode;
    /**
     * METHOD: start
     * PURPOSE: method to start GL window and render
     */
    public void start() {
        try {
            createWindow();
            createKeyboard();
            initGL();
            //render();
            fp = new FPCameraController(0f, 0f, 0f);
            fp.gameLoop();
        } catch (Exception e) {
            // what is the error
            e.printStackTrace();
        }
    }
    
    /**
     * METHOD: createWindow
     * PURPOSE: method to create display object 
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 &&
                d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("Simple 3D Rendering");
        Display.create();
    }
    
    /**
     * METHOD: createKeyboard
     * PURPOSE: method to create keyboard object 
     */
    private void createKeyboard() throws Exception {
        Keyboard.create();
    }

    /**
     * METHOD: initGL
     * PURPOSE: method that initialize projection matrix on window 
     */
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth() /
                (float)displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnableClientState (GL_TEXTURE_COORD_ARRAY);
    }
    
    /**
     * METHOD: main
     * PURPOSE: run the OpenGL program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main main = new Main();
        main.start();
    }
}
