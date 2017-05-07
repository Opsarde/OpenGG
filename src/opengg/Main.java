/*************************************************************
 *     file: Main.java
 *     author: 
 *     class: CS 445 - Computer Graphics
 * 
 *     assignment: program 3
 *     last modified: 5/9/2017
 * 
 * 
 *************************************************************/
package opengg;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 *
 * @author Shun Lu
 */
public class Main {
    /**
     * METHOD: start
     * PURPOSE: method to start GL window and render
     */
    public void start() {
        try {
            createWindow();
            createKeyboard();
            initGL();
            render();
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
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Simple Graphic Rendering");
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
        glOrtho(-640 / 2, 640 / 2, -480 / 2, 480 / 2, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    /**
     * METHOD: render
     * PURPOSE: render every frame until window is closed or press escape key
     */
    private void render() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            try {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                // TODO:
                // Render cube here
                Display.update();
                Display.sync(60);
            } catch (Exception e) {
            }
        }
        Display.destroy();
        Keyboard.destroy();
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
