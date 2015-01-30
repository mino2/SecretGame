/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diamondgame;

/**
 *
 * @author Noubuk
 */
final class CPos {

    public void setXY(int x, int y){
        this.y = y;
        this.x = x;
    }
        
    public int x;
    public int y;
    
    public CPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
}
