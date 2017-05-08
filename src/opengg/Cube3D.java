/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opengg;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author shun7817
 */
public class Cube3D {
    private float edgeLength;
    private Vector3Float position;

    public Cube3D(Vector3Float position, float edgeLength) {
        this.edgeLength = edgeLength;
        this.position = position;
    }

    public void draw() {
        try {
            glTranslatef(position.x, position.y, position.z);
            glBegin(GL_QUADS);
            //Top
            glColor3f(255.0f, 0.0f, 0.0f);
            glVertex3f(edgeLength, edgeLength, -edgeLength);
            glVertex3f(-edgeLength, edgeLength, -edgeLength);
            glVertex3f(-edgeLength, edgeLength, edgeLength);
            glVertex3f(edgeLength, edgeLength, edgeLength);
            //Bottom         
            glColor3f(255.0f, 255.0f, 0.0f);
            glVertex3f(edgeLength, -edgeLength, edgeLength);
            glVertex3f(-edgeLength, -edgeLength, edgeLength);
            glVertex3f(-edgeLength, -edgeLength, -edgeLength);
            glVertex3f(edgeLength, -edgeLength, -edgeLength);
            //Front
            glColor3f(0.0f, 255.0f, 255.0f);
            glVertex3f(edgeLength, edgeLength, edgeLength);
            glVertex3f(-edgeLength, edgeLength, edgeLength);
            glVertex3f(-edgeLength, -edgeLength, edgeLength);
            glVertex3f(edgeLength, -edgeLength, edgeLength);
            //Back
            glColor3f(0.0f, 255.0f, 0.0f);
            glVertex3f(edgeLength, -edgeLength, -edgeLength);
            glVertex3f(-edgeLength, -edgeLength, -edgeLength);
            glVertex3f(-edgeLength, edgeLength, -edgeLength);
            glVertex3f(edgeLength, edgeLength, -edgeLength);
            //Left
            glColor3f(0.0f, 0.0f, 255.0f);
            glVertex3f(-edgeLength, edgeLength, edgeLength);
            glVertex3f(-edgeLength, edgeLength, -edgeLength);
            glVertex3f(-edgeLength, -edgeLength, -edgeLength);
            glVertex3f(-edgeLength, -edgeLength, edgeLength);
            //Right
            glColor3f(255.0f, 0.0f, 255.0f);
            glVertex3f(edgeLength, edgeLength, -edgeLength);
            glVertex3f(edgeLength, edgeLength, edgeLength);
            glVertex3f(edgeLength, -edgeLength, edgeLength);
            glVertex3f(edgeLength, -edgeLength, -edgeLength);
            glEnd();
            //Top
            glColor3f(255.0f, 0.0f, 0.0f);
            glBegin(GL_LINE_LOOP);
            glVertex3f(edgeLength, edgeLength, -edgeLength);
            glVertex3f(-edgeLength, edgeLength, -edgeLength);
            glVertex3f(-edgeLength, edgeLength, edgeLength);
            glVertex3f(edgeLength, edgeLength, edgeLength);
            glEnd();
            //Bottom         
            glColor3f(255.0f, 255.0f, 0.0f);
            glBegin(GL_LINE_LOOP);
            glVertex3f(edgeLength, -edgeLength, edgeLength);
            glVertex3f(-edgeLength, -edgeLength, edgeLength);
            glVertex3f(-edgeLength, -edgeLength, -edgeLength);
            glVertex3f(edgeLength, -edgeLength, -edgeLength);
            glEnd();
            //Front
            glColor3f(0.0f, 255.0f, 255.0f);
            glBegin(GL_LINE_LOOP);
            glVertex3f(edgeLength, edgeLength, edgeLength);
            glVertex3f(-edgeLength, edgeLength, edgeLength);
            glVertex3f(-edgeLength, -edgeLength, edgeLength);
            glVertex3f(edgeLength, -edgeLength, edgeLength);
            glEnd();
            //Back
            glColor3f(0.0f, 255.0f, 0.0f);
            glBegin(GL_LINE_LOOP);
            glVertex3f(edgeLength, -edgeLength, -edgeLength);
            glVertex3f(-edgeLength, -edgeLength, -edgeLength);
            glVertex3f(-edgeLength, edgeLength, -edgeLength);
            glVertex3f(edgeLength, edgeLength, -edgeLength);
            glEnd();
            //Left
            glColor3f(0.0f, 0.0f, 255.0f);
            glBegin(GL_LINE_LOOP);
            glVertex3f(-edgeLength, edgeLength, edgeLength);
            glVertex3f(-edgeLength, edgeLength, -edgeLength);
            glVertex3f(-edgeLength, -edgeLength, -edgeLength);
            glVertex3f(-edgeLength, -edgeLength, edgeLength);
            glEnd();
            //Right
            glColor3f(255.0f, 0.0f, 255.0f);
            glBegin(GL_LINE_LOOP);
            glVertex3f(edgeLength, edgeLength, -edgeLength);
            glVertex3f(edgeLength, edgeLength, edgeLength);
            glVertex3f(edgeLength, -edgeLength, edgeLength);
            glVertex3f(edgeLength, -edgeLength, -edgeLength);
            glEnd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void edge() {

    }

    private void fill() {

    }
}
