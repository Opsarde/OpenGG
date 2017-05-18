/*************************************************************
 *     file: Block.java
 *     authors: OpenGG (Shun Lu, Roenyl Tisoy, Tuan Pham, Evan Gunell)
 *     class: CS 445 - Computer Graphics
 * 
 *     assignment: program 3
 *     last modified: 5/9/2017
 * 
 *     purpose: This class represents our individual blocks and the different types
 *     of textures it can have.
 * 
 * 
 *************************************************************/

package opengg;

/**
 *
 * @author Tuan Pham
 */
public class Block {
    private boolean IsActive;
    private BlockType Type;
    private float x,y,z;
    
    /**
     * METHOD: BlockType
     * PURPOSE: Specifies each type of block
     */
    public enum BlockType
    {
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5);
        private int BlockID;
        BlockType(int i) {
            BlockID =i;
        }
        
        /**
        * METHOD: GetID
        * PURPOSE: returns ID of block
        */
        public int GetID(){
            return BlockID;
        }
        
        /**
        * METHOD: SetID
        * PURPOSE: Sets ID of block
        */
        public void SetID(int i){
            BlockID = i;
        }
    }
    /**
    * METHOD: Block
    * PURPOSE: Initializes block type
    */
    public Block(BlockType type){
        Type= type;
    }
    

    /**
    * METHOD: setCoords
    * PURPOSE: Sets the coordinates of the blocks
    */
    public void setCoords(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
    * METHOD: isActive
    * PURPOSE: Checks if block is active
    */
    public boolean IsActive() {
        return IsActive;
    }
    
    /**
    * METHOD: setActive
    * PURPOSE: Toggles block 
    */
    public void SetActive(boolean active){
        IsActive=active;
    }
    
    /**
    * METHOD: GetID
    * PURPOSE: Gets ID of block
    */
    public int GetID(){
        return Type.GetID();
    }
}

