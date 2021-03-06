/*************************************************************
 *     file: Chunk.java
 *     authors: OpenGG (Shun Lu, Roenyl Tisoy, Tuan Pham, Evan Gunell)
 *     class: CS 445 - Computer Graphics
 * 
 *     assignment: program 3
 *     last modified: 5/9/2017
 * 
 *     purpose: This class bundles up a number of blocks together and then
*      makes a single call to the renderer for each cube.
 * 
 * 
 *************************************************************/

package opengg;

/**
 *
 * @author Tuan Pham
 */

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Chunk {
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int StartX, StartY, StartZ;
    private Random r;
    private int VBOTextureHandle;
    private Texture texture;

    /**
     * METHOD: render
     * PURPOSE: draws our cubes together in a chunk
     */
    public void render(){
        glPushMatrix();
        glPushMatrix();
        glBindBuffer(GL_ARRAY_BUFFER,
        VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER,
        VBOColorHandle);
        glColorPointer(3,GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBindTexture(GL_TEXTURE_2D, 1);
        glTexCoordPointer(2,GL_FLOAT,0,0L);
        glDrawArrays(GL_QUADS, 0,
        CHUNK_SIZE *CHUNK_SIZE*
        CHUNK_SIZE * 24);
        glPopMatrix();
    }
    
    /**
     * METHOD: rebuildMesh
     * PURPOSE: Creates all of the cubes and textures them
     */
    public void rebuildMesh(float startX, float startY, float startZ) {
        Random seed = new Random();
        SimplexNoise noise = new SimplexNoise(70, .07f, seed.nextInt());
        
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers(); 
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexTextureData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE *CHUNK_SIZE)* 6 * 12);
       
        for (float x = 0; x < CHUNK_SIZE; x += 1) {
            for (float z = 0; z < CHUNK_SIZE; z += 1) {
                for (float y = 0; y < CHUNK_SIZE; y++) {
                    float i = startX + x *((10 - startX) / 12);
                    float j = startY + z *((10 - startY) / 12);
                    float k = startZ + z *((10 - startZ) / 12);
                    int height = Math.abs((int)(startY + (int)(100 * noise.getNoise((int)i, (int)j, (int)k)) * CUBE_LENGTH));
                    if(y <= height) {
                        // Set ID here
                        if (y == 0) {
                            Blocks[(int)(x)][(int)(y)][(int)(z)] = new 
                            Block(Block.BlockType.BlockType_Bedrock);
                        }
                        else if (y == height) {
                            if (r.nextFloat() > 0.66f) {
                                Blocks[(int)(x)][(int)(y)][(int)(z)] = new
                                Block(Block.BlockType.BlockType_Water);
                            }
                            else if (r.nextFloat() > 0.33f) {
                                Blocks[(int)(x)][(int)(y)][(int)(z)] = new
                                Block(Block.BlockType.BlockType_Sand);                
                            }
                            else {
                                Blocks[(int)(x)][(int)(y)][(int)(z)] = new
                                Block(Block.BlockType.BlockType_Grass);
                            }
                        }
                        else {
                            if (r.nextFloat() > 0.5f) {
                                Blocks[(int)(x)][(int)(y)][(int)(z)] = new
                                Block(Block.BlockType.BlockType_Dirt);
                            }
                            else {
                                Blocks[(int)(x)][(int)(y)][(int)(z)] = new
                                Block(Block.BlockType.BlockType_Stone);
                            }
                        }
                        VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH), (float) (y * CUBE_LENGTH + (int)(CHUNK_SIZE * .8)), (float) (startZ + z * CUBE_LENGTH)));
                        VertexColorData.put(createCubeVertexCol(getCubeColor(Blocks[(int) x][(int) y][(int) z])));
                        VertexTextureData.put(createTexCube((float) 0, (float) 0, Blocks[(int)(x)][(int)(y)][(int)(z)]));
                    }
                }
            }
        }
       
        VertexColorData.flip();
        VertexPositionData.flip();
        VertexTextureData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData,
        GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    /**
     * METHOD: createCubeVertexCol
     * PURPOSE: Stores cubes in a column
     */
    private float[] createCubeVertexCol(float[] CubeColorArray) {
        float[] cubeColors = new float[CubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = CubeColorArray[i %
            CubeColorArray.length];
        }
        return cubeColors;
    }
    
    /**
     * METHOD: createCube
     * PURPOSE: Creates individual cubes
     */
    public static float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[] {
        // TOP QUAD
        x + offset, y + offset, z,
        x - offset, y + offset, z,
        x - offset, y + offset, z - CUBE_LENGTH,
        x + offset, y + offset, z - CUBE_LENGTH,
        // BOTTOM QUAD
        x + offset, y - offset, z - CUBE_LENGTH,
        x - offset, y - offset, z - CUBE_LENGTH,
        x - offset, y - offset, z,
        x + offset, y - offset, z,
        // FRONT QUAD
        x + offset, y + offset, z - CUBE_LENGTH,
        x - offset, y + offset, z - CUBE_LENGTH,
        x - offset, y - offset, z - CUBE_LENGTH,
        x + offset, y - offset, z - CUBE_LENGTH,
        // BACK QUAD
        x + offset, y - offset, z,
        x - offset, y - offset, z,
        x - offset, y + offset, z,
        x + offset, y + offset, z,
        // LEFT QUAD
        x - offset, y + offset, z - CUBE_LENGTH,
        x - offset, y + offset, z,
        x - offset, y - offset, z,
        x - offset, y - offset, z - CUBE_LENGTH,
        // RIGHT QUAD
        x + offset, y + offset, z,
        x + offset, y + offset, z - CUBE_LENGTH,
        x + offset, y - offset, z - CUBE_LENGTH,
        x + offset, y - offset, z };
    }
    
    /**
     * METHOD: getCubeColor
     * PURPOSE: Returns cube color
     */
    private float[] getCubeColor(Block block) {
          return new float[] { 1, 1, 1 };
    }
    
    /**
     * METHOD: Chunk
     * PURPOSE: Constructs our Chunk
     */
    public Chunk(int startX, int startY, int startZ) {
        try {
            texture = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("terrain.png"));
        }
        catch(Exception e) {
            System.out.print("ER-ROAR!");
        }
        r = new Random();
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    if (r.nextFloat()>0.9f){
                        Blocks[x][y][z] = new
                        Block(Block.BlockType.BlockType_Bedrock);
                    } else if (r.nextFloat()>0.8f){
                        Blocks[x][y][z] = new
                        Block(Block.BlockType.BlockType_Dirt);
                    } else if (r.nextFloat()>0.6f){
                        Blocks[x][y][z] = new
                        Block(Block.BlockType.BlockType_Water);
                    } else if (r.nextFloat()>0.4f){
                        Blocks[x][y][z] = new
                        Block(Block.BlockType.BlockType_Stone);
                    } else if (r.nextFloat()>0.2f){
                        Blocks[x][y][z] = new
                        Block(Block.BlockType.BlockType_Sand);
                    } else{
                        Blocks[x][y][z] = new
                        Block(Block.BlockType.BlockType_Grass);
                    }
                }
            }   
        }
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers(); 
        StartX = startX;
        StartY = startY;
        StartZ = startZ;
        rebuildMesh(startX, startY, startZ);
    }
    
    /**
     * METHOD: createTexCube
     * PURPOSE: Textures each cube
     */
    public static float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f/16)/1024f;
        switch (block.GetID()) {
            case 0:
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*3, y + offset*10,
                x + offset*2, y + offset*10,
                x + offset*2, y + offset*9,
                x + offset*3, y + offset*9,
                // TOP!
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // FRONT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1,
                // BACK QUAD
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1,
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                // LEFT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1,
                // RIGHT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1};
            
            case 1:
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*3, y + offset*2,
                x + offset*2, y + offset*2,
                x + offset*2, y + offset*1,
                x + offset*3, y + offset*1,
                // TOP!
                x + offset*3, y + offset*2,
                x + offset*2, y + offset*2,
                x + offset*2, y + offset*1,
                x + offset*3, y + offset*1,
                // FRONT QUAD
                x + offset*2, y + offset*1,
                x + offset*3, y + offset*1,
                x + offset*3, y + offset*2,
                x + offset*2, y + offset*2,
                // BACK QUAD
                x + offset*3, y + offset*2,
                x + offset*2, y + offset*2,
                x + offset*2, y + offset*1,
                x + offset*3, y + offset*1,
                // LEFT QUAD
                x + offset*2, y + offset*1,
                x + offset*3, y + offset*1,
                x + offset*3, y + offset*2,
                x + offset*2, y + offset*2,
                // RIGHT QUAD
                x + offset*2, y + offset*1,
                x + offset*3, y + offset*1,
                x + offset*3, y + offset*2,
                x + offset*2, y + offset*2};
            case 2:
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*14, y + offset*13,
                x + offset*13, y + offset*13,
                x + offset*13, y + offset*12,
                x + offset*14, y + offset*12,
                // TOP!
                x + offset*14, y + offset*13,
                x + offset*13, y + offset*13,
                x + offset*13, y + offset*12,
                x + offset*14, y + offset*12,
                // FRONT QUAD
                x + offset*13, y + offset*12,
                x + offset*14, y + offset*12,
                x + offset*14, y + offset*13,
                x + offset*13, y + offset*13,
                // BACK QUAD
                x + offset*14, y + offset*13,
                x + offset*13, y + offset*13,
                x + offset*13, y + offset*12,
                x + offset*14, y + offset*12,
                // LEFT QUAD
                x + offset*13, y + offset*12,
                x + offset*14, y + offset*12,
                x + offset*14, y + offset*13,
                x + offset*13, y + offset*13,
                // RIGHT QUAD
                x + offset*13, y + offset*12,
                x + offset*14, y + offset*12,
                x + offset*14, y + offset*13,
                x + offset*13, y + offset*13};
            case 3:
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // TOP!
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // FRONT QUAD
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                // BACK QUAD
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // LEFT QUAD
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                // RIGHT QUAD
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1};
            case 4:
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*2, y + offset*1,
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*0,
                x + offset*2, y + offset*0,
                // TOP!
                x + offset*2, y + offset*1,
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*0,
                x + offset*2, y + offset*0,
                // FRONT QUAD
                x + offset*1, y + offset*0,
                x + offset*2, y + offset*0,
                x + offset*2, y + offset*1,
                x + offset*1, y + offset*1,
                // BACK QUAD
                x + offset*2, y + offset*1,
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*0,
                x + offset*2, y + offset*0,
                // LEFT QUAD
                x + offset*1, y + offset*0,
                x + offset*2, y + offset*0,
                x + offset*2, y + offset*1,
                x + offset*1, y + offset*1,
                // RIGHT QUAD
                x + offset*1, y + offset*0,
                x + offset*2, y + offset*0,
                x + offset*2, y + offset*1,
                x + offset*1, y + offset*1};
            case 5:
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                // TOP!
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                // FRONT QUAD
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                // BACK QUAD
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                // LEFT QUAD
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                // RIGHT QUAD
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2};
            default:
                return null;
        }
    }     
}
